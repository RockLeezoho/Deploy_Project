package org.com.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class PpmDTO {

    @NotNull(message= "Missing ppm data.")
    private Double ppm;

    @NotNull(message= "Missing timestamp data.")
    private String timestamp;

}
