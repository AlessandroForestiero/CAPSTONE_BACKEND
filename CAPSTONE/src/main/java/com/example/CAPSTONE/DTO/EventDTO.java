package com.example.CAPSTONE.DTO;

import com.example.CAPSTONE.Models.TipoEvento;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
@Data
public class EventDTO {
    @NotNull
    private Long id;
    @NotNull
    @Size(min = 2, max = 50)
    private String nome;
    @NotNull
    @Size(min = 2, max = 50)
    private String luogo;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataEvento;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataFineAcquisti;

    private TipoEvento tipoEvento;
    @NotBlank
    private String immagine;
    @NotNull
    @Size(min = 2, max = 500)
    private String descrizione;
}
