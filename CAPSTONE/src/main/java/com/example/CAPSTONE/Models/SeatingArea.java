package com.example.CAPSTONE.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class SeatingArea {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private int totSeats;
    @NotNull
    private int availableSeat;
    @NotNull
    private BigDecimal price;
    @ManyToOne
    @NotNull
    private Event event;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "seatingArea")
    private List<Ticket> tickets;
}
