package com.example.CAPSTONE.Services;

import com.example.CAPSTONE.DTO.EventDTO;
import com.example.CAPSTONE.Exeptions.NotFoundException;
import com.example.CAPSTONE.Models.Event;
import com.example.CAPSTONE.Models.SeatingArea;
import com.example.CAPSTONE.Models.TipoEvento;
import com.example.CAPSTONE.Repositories.EventRepo;
import com.sun.jdi.request.EventRequest;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepo eventRepo;

    public Event getEventById(long id) throws NotFoundException {
        return eventRepo.findById(id).orElseThrow(() -> new NotFoundException("Event with id " + id + "not found"));
    }
    public List<Event> getEventByName(String name){
        return eventRepo.findByNomeContainingIgnoreCase(name);
    }
    public List<Event> getEventByType(TipoEvento tipo){
        return eventRepo.findByTipoEvento(tipo);
    }
    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    public List<Event> getEventByFiltri(Optional<TipoEvento> tipoEvento,Optional<String> luogo, Optional<String> nome) {
       TipoEvento tipovalue = tipoEvento.isPresent() ? tipoEvento.get():null;
        String luogoValue = luogo.isPresent() ? luogo.get() : null;
        String nomeValue = nome.isPresent() ? nome.get() : null;
       if(nomeValue==null) {
           nomeValue = "";
       }
        return eventRepo.findByFiltri(tipovalue,luogoValue, nomeValue);
    }

    public Event createEvent(EventDTO eventDTO) {
        Event event = new Event();
        event.setNome(eventDTO.getNome());
        event.setDescrizione(eventDTO.getDescrizione());
        event.setLuogo(eventDTO.getLuogo());
        event.setImmagine(eventDTO.getImmagine());
        event.setDataEvento(eventDTO.getDataEvento());
        event.setDataFineAcquisti(eventDTO.getDataFineAcquisti());
        event.setTipoEvento(eventDTO.getTipoEvento());
        return eventRepo.save(event);
    }

    public Event updateEvent(Long id, EventDTO eventDTO) {
        Event event = getEventById(id);
        event.setNome(eventDTO.getNome());
        event.setDescrizione(eventDTO.getDescrizione());
        event.setLuogo(eventDTO.getLuogo());
        event.setImmagine(eventDTO.getImmagine());
        event.setDataEvento(event.getDataEvento());
        event.setTipoEvento(eventDTO.getTipoEvento());
        event.setImmagine(eventDTO.getImmagine());
        return eventRepo.save(event);
    }

    public void deleteEvent(Long id) {
        Event event = getEventById(id);
        eventRepo.delete(event);
    }

    public Event uploadImage(Long id, String url) throws NotFoundException {
        Event event = getEventById(id);
        event.setImmagine(url);
        return eventRepo.save(event);
    }

}
