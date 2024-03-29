package com.example.CAPSTONE.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SeatingAreaDTO {
    @NotNull
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private int availableSeats;
    @NotNull
    private int totSeats;
    @NotBlank
    private BigDecimal price;
    @NotNull
    private Long idEvent;
}
