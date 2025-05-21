package org.com.controller;

import jakarta.validation.Valid;
import org.com.dto.PpmDTO;
import org.com.entity.Nh3PPM;
import org.com.service.PpmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/nh3alert")
public class PpmController {

    @Autowired
    private PpmService ppmService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate; // Send WebSocket

    @PostMapping("/ppm")
    public ResponseEntity<?> receiveNh3Data(@Valid @RequestBody PpmDTO ppmDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, List<String>> errorResponse = new HashMap<>();
            errorResponse.put("errors", bindingResult.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList()));
            return ResponseEntity.badRequest().body(errorResponse);
        }


        LocalDateTime time = ppmService.getByTimeWithoutZone(ppmDTO.getTimestamp());


        Nh3PPM nh3PPM = ppmService.addNh3PPM(Nh3PPM.builder()
                .ppm(ppmDTO.getPpm())
                .timestamp(time)
                .build());


        messagingTemplate.convertAndSend("/topic/nh3", ppmDTO);

        return ResponseEntity.ok("Data saved and broadcasted successfully.");
    }
}
