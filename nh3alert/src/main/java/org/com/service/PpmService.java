package org.com.service;

import org.com.entity.Nh3PPM;
import org.com.repository.PpmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PpmService {

    @Autowired
    private PpmRepository ppmRepository;

    public LocalDateTime getByTimeWithoutZone(String timestamp) {
        if(timestamp == null || timestamp.isEmpty()) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(timestamp, formatter);
    }

    public Nh3PPM addNh3PPM(Nh3PPM nh3PPM) {
        return ppmRepository.save(nh3PPM);
    }
}
