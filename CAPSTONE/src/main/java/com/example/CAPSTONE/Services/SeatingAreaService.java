package com.example.CAPSTONE.Services;

import com.example.CAPSTONE.DTO.EventDTO;
import com.example.CAPSTONE.DTO.SeatingAreaDTO;
import com.example.CAPSTONE.Exeptions.NotFoundException;
import com.example.CAPSTONE.Models.Event;
import com.example.CAPSTONE.Models.SeatingArea;
import com.example.CAPSTONE.Repositories.EventRepo;
import com.example.CAPSTONE.Repositories.SeatingAreaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatingAreaService {
    @Autowired
    private SeatingAreaRepo seatingAreaRepo;
    @Autowired
    private EventRepo eventRepo;
    @Autowired
    private EventService eventService;

    public SeatingArea getById(Long id) throws NotFoundException {
        return seatingAreaRepo.findById(id).orElseThrow(() -> new NotFoundException("seatingChart with id " + id + " not found"));
    }

    public Page<SeatingArea> getAll(Pageable pageable) {
        return seatingAreaRepo.findAll(pageable);
    }


    public SeatingArea createSeatingArea(SeatingAreaDTO seatingAreaDTO) {
        SeatingArea seatingArea = new SeatingArea();
        seatingArea.setName(seatingAreaDTO.getName());
        seatingArea.setPrice(seatingAreaDTO.getPrice());
        seatingArea.setAvailableSeat(seatingAreaDTO.getAvailableSeats());
        seatingArea.setTotSeats(seatingAreaDTO.getTotSeats());
        Event event = eventRepo.findById(seatingAreaDTO.getIdEvent()).orElseThrow(() -> new NotFoundException("event not found"));
        seatingArea.setEvent(event);
        seatingAreaRepo.save(seatingArea);
        event.getSeatingArea().add(seatingArea);
        eventRepo.save(event);
        return seatingArea;
    }

    public SeatingArea updateSeatingChart(Long id, SeatingAreaDTO seatingAreaDTO) throws NotFoundException {
        SeatingArea seatingArea = getById(id);
        seatingArea.setName(seatingAreaDTO.getName());
        seatingArea.setPrice(seatingAreaDTO.getPrice());
        return seatingAreaRepo.save(seatingArea);
    }

    public void delete(long id) throws NotFoundException {
        SeatingArea seatingArea = getById(id);
        seatingAreaRepo.delete(seatingArea);
    }
    public List<SeatingArea> getSeatingAreaByEventId(Long eventId) {
        return seatingAreaRepo.findByEventId(eventId);
    }

}
