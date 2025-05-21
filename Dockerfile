# --- Giai đoạn build ---
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Đặt thư mục làm việc
WORKDIR /app

# Copy toàn bộ code vào container
COPY . .

# Build project, bỏ qua test để giảm thời gian build
RUN mvn -B -DskipTests clean install


# --- Giai đoạn chạy ---
FROM eclipse-temurin:21-jdk

# Đặt thư mục làm việc
WORKDIR /app

# Copy file .jar từ giai đoạn build sang
COPY --from=build /app/target/*.jar app.jar

# Chạy app
CMD ["java", "-jar", "app.jar"]
