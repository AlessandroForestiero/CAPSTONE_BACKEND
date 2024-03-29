package com.example.CAPSTONE.Controllers;

import com.cloudinary.Cloudinary;
import com.example.CAPSTONE.DTO.EventDTO;
import com.example.CAPSTONE.DTO.SeatingAreaDTO;
import com.example.CAPSTONE.Exeptions.BadRequestException;
import com.example.CAPSTONE.Exeptions.NotFoundException;
import com.example.CAPSTONE.Models.Event;
import com.example.CAPSTONE.Models.SeatingArea;
import com.example.CAPSTONE.Models.TipoEvento;
import com.example.CAPSTONE.Repositories.EventRepo;
import com.example.CAPSTONE.Services.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
public class EventController {
    @Autowired
    private EventService eventService;
    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private Cloudinary cloudinary;

    @PostMapping("/event")
    public ResponseEntity<EventDTO> AddEvent(@RequestBody EventDTO eventDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }
        Event event = eventService.createEvent(eventDTO);
        EventDTO eventDTOr = convertToDTO(event);
        return new ResponseEntity<>(eventDTOr, HttpStatus.CREATED);
    }

    @PutMapping("/event/{id}")
    public ResponseEntity<Event> UpdateEvent(@RequestBody EventDTO eventDTO, @PathVariable Long id, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }
        Event event = eventService.getEventById(id);
        if (event == null) {
            throw new NotFoundException("Event not found with id: " + id);
        }
        Event updatedEvent = eventService.updateEvent(id, eventDTO);
        return new ResponseEntity<>(updatedEvent, HttpStatus.ACCEPTED);
    }

    @GetMapping("/event")
    public ResponseEntity<List<EventDTO>> getAllEvent() {
        List<Event> events = eventService.getAllEvents();
        List<EventDTO> eventDTOs = events.stream().map(this::convertToDTO).toList();
        return new ResponseEntity<>(eventDTOs, HttpStatus.OK);
    }

    @GetMapping("/event/name/{name}")
    public ResponseEntity<List<EventDTO>> getEventByNome(@PathVariable String name) {
        List<Event> events = eventService.getEventByName(name);
        List<EventDTO> eventDTOS = events.stream().map(this::convertToDTO).toList();
        return new ResponseEntity<>(eventDTOS, HttpStatus.OK);
    }

    @GetMapping("/event/search")
    public ResponseEntity<List<EventDTO>> getEventByNameOrCity(@RequestParam("tipoEvento") Optional<TipoEvento> tipoEvento,
                                                               @RequestParam("luogo") Optional<String> luogo, @RequestParam("nome") Optional<String> nome) {

        List<Event> events = eventService.getEventByFiltri(tipoEvento, luogo, nome);
        List<EventDTO> eventDTOS = events.stream().map(this::convertToDTO).toList();
        return new ResponseEntity<>(eventDTOS, HttpStatus.OK);
    }

    @GetMapping("/event/search/type")
    public ResponseEntity<List<EventDTO>> getEventByType(@RequestParam("tipo") TipoEvento tipo) {
        List<Event> events = eventService.getEventByType(tipo);
        List<EventDTO> eventDTOS = events.stream().map(this::convertToDTO).toList();
        return new ResponseEntity<>(eventDTOS, HttpStatus.OK);
    }

    ;

    @GetMapping("event/id/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        if (event != null) {
            EventDTO eventDTO = convertToDTO(event);
            return new ResponseEntity<>(eventDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/event/{id}")
    public ResponseEntity<Event> deleteEvent(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        if (event == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        eventRepo.delete(event);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public EventDTO convertToDTO(Event event) {
        EventDTO dto = new EventDTO();
        dto.setId(event.getId());
        dto.setNome(event.getNome());
        dto.setLuogo(event.getLuogo());
        dto.setDataEvento(event.getDataEvento());
        dto.setDataFineAcquisti(event.getDataFineAcquisti());
        dto.setTipoEvento(event.getTipoEvento());
        dto.setImmagine(event.getImmagine());
        dto.setDescrizione(event.getDescrizione());
        return dto;
    }

    @PatchMapping("/event/{id}/upload")
    public ResponseEntity<Event> uploadLogo(@PathVariable Long id, @RequestParam("upload") MultipartFile multipartFile) {
        try {
            Event event = eventService.uploadImage(id, (String) cloudinary.uploader().upload(multipartFile.getBytes(), new HashMap()).get("url"));
            return new ResponseEntity<>(event, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}

