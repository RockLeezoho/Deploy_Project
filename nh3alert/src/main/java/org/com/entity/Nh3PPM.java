package org.com.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name= "nh3ppm")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Nh3PPM {

    @Id
    @Getter
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(nullable= false)
    private Double ppm;

    @Getter
    @Column(name= "timestamp", nullable = false)
    private LocalDateTime timestamp;

}
