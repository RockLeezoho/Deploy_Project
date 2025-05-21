function updateStatus(ppm) {
  const circle = document.getElementById("circle");
  const ppmElement = document.getElementById("ppm");

  ppmElement.textContent = `Nồng độ NH3: ${Math.round(ppm * 10) / 10} ppm`;

  circle.classList.remove("good", "warning", "danger");

  if (ppm >= 100) {
    circle.classList.add("danger");
  } else if (ppm >= 50) {
    circle.classList.add("warning");
  } else {
    circle.classList.add("good");
  }
}

const connectionStatusElem = document.getElementById("connection-status");

function setConnectionStatus(text, color = "gray") {
  if (connectionStatusElem) {
    connectionStatusElem.textContent = text;
    connectionStatusElem.style.color = color;
  }
}

// Biến global để quản lý stompClient
let stompClient = null;
let reconnectDelay = 1000; // 1 giây ban đầu
const maxReconnectDelay = 30000; // tối đa 30 giây

function connect() {
  setConnectionStatus("Đang kết nối...", "orange");

  const socket = new SockJS('/ws');
  stompClient = Stomp.over(socket);

  // Tắt debug log của stomp nếu muốn
  stompClient.debug = null;

  stompClient.connect({}, frame => {
    console.log('Connected: ' + frame);
    reconnectDelay = 1000; // reset lại delay khi kết nối thành công
    setConnectionStatus("Đã kết nối", "green");

    stompClient.subscribe('/topic/nh3', message => {
      try {
        const data = JSON.parse(message.body);
        console.log("Received data via WebSocket:", data);

        if (data.ppm !== undefined) {
          updateStatus(data.ppm);
        }
      } catch (e) {
        console.error("Lỗi parse JSON từ WebSocket:", e);
      }
    });
  }, error => {
    console.error('Lỗi kết nối WebSocket:', error);
    setConnectionStatus("Mất kết nối, đang thử lại...", "blue");
    attemptReconnect();
  });

  // Xử lý lỗi socket thấp hơn (sockjs)
  socket.onclose = () => {
    console.warn('Socket bị đóng');
    setConnectionStatus("Mất kết nối, đang thử lại...", "blue");
    attemptReconnect();
  };
}

function attemptReconnect() {
  if (reconnectDelay > maxReconnectDelay) reconnectDelay = maxReconnectDelay;
  console.log(`Thử kết nối lại sau ${reconnectDelay / 1000} giây...`);
  setTimeout(() => {
    setConnectionStatus("Đang kết nối lại...", "orange");
    connect();
    reconnectDelay *= 2;
  }, reconnectDelay);
}

connect();
