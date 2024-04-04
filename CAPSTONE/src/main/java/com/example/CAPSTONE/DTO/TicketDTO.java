package com.example.CAPSTONE.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TicketDTO {

    private Long id;
    private Long code;
    private Long eventId;
    private Long userId;
    private Long seatingAreaId;
    private BigDecimal price;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate paymentDate;


}
