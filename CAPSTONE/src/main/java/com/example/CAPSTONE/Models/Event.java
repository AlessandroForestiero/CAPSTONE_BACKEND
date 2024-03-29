package com.example.CAPSTONE.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Entity
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String nome;
    @NotNull
    private String luogo;
    @NotNull
    private LocalDate dataEvento;
    @NotNull
    private LocalDate dataFineAcquisti;
    @Enumerated(EnumType.STRING)
    private TipoEvento tipoEvento;
    @NotNull
    private String immagine;
    @NotNull
    private String descrizione;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "event", cascade = CascadeType.ALL)
    private List<Ticket> tickets;
    @OneToMany(mappedBy = "event",cascade = CascadeType.ALL)
    private List<SeatingArea> seatingArea;

}
