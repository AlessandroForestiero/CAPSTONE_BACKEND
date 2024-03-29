package com.example.CAPSTONE.Controllers;

import com.example.CAPSTONE.DTO.SeatingAreaDTO;
import com.example.CAPSTONE.Exeptions.BadRequestException;
import com.example.CAPSTONE.Models.Event;
import com.example.CAPSTONE.Models.SeatingArea;
import com.example.CAPSTONE.Services.EventService;
import com.example.CAPSTONE.Services.SeatingAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SeatingAreaController {
    @Autowired
    private SeatingAreaService seatingAreaService;
    @Autowired
    private EventService eventService;

    @GetMapping("/seating_area")
    public ResponseEntity<Page<SeatingAreaDTO>> getAllSeating(Pageable pageable) {
        Page<SeatingArea> seatinArea = seatingAreaService.getAll(pageable);
        Page<SeatingAreaDTO> seatingAreaDTO = seatinArea.map(this::convertToDTO);
        return new ResponseEntity<>(seatingAreaDTO, HttpStatus.OK);
    }

    @GetMapping("/seating_area/{id}")
    public ResponseEntity<SeatingAreaDTO> getSeatingAreaById(@PathVariable Long id) {
        SeatingArea seatingArea = seatingAreaService.getById(id);
        SeatingAreaDTO seatingAreaDTO = convertToDTO(seatingArea);
        return new ResponseEntity<>(seatingAreaDTO, HttpStatus.OK);
    }
    @GetMapping("/seating_area/event/{eventId}")
    public ResponseEntity<List<SeatingAreaDTO>> getSeatingAreaByEventId(@PathVariable Long eventId){
        List<SeatingArea> seatingArea=seatingAreaService.getSeatingAreaByEventId(eventId);
        List<SeatingAreaDTO> seatingAreaDTO=seatingArea.stream().map(this::convertToDTO).toList();
        return new ResponseEntity<>(seatingAreaDTO,HttpStatus.OK);
    }

    @PutMapping("/seating_area")
    public ResponseEntity<SeatingAreaDTO> createSeatingArea(@RequestBody SeatingAreaDTO seatingAreaDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }
        SeatingArea seatingArea = seatingAreaService.createSeatingArea(seatingAreaDTO);
        SeatingAreaDTO createdDTO = convertToDTO(seatingArea);
        return new ResponseEntity<>(createdDTO, HttpStatus.CREATED);
    }

    private SeatingAreaDTO convertToDTO(SeatingArea seatingArea) {
        SeatingAreaDTO dto = new SeatingAreaDTO();
        dto.setName(seatingArea.getName());
        dto.setTotSeats(seatingArea.getTotSeats());
        dto.setAvailableSeats(seatingArea.getTotSeats());
        dto.setPrice(seatingArea.getPrice());
        dto.setId(seatingArea.getId());
        dto.setIdEvent(seatingArea.getEvent().getId());
        return dto;
    }
 }
