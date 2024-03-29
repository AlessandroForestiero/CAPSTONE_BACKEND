package com.example.CAPSTONE.Services;

import com.example.CAPSTONE.Exeptions.NotFoundException;
import com.example.CAPSTONE.Models.Event;
import com.example.CAPSTONE.Models.SeatingArea;
import com.example.CAPSTONE.Models.Ticket;
import com.example.CAPSTONE.Models.User;
import com.example.CAPSTONE.Repositories.SeatingAreaRepo;
import com.example.CAPSTONE.Repositories.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class TransactionService {
    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private SeatingAreaService seatingAreaService;

    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    private SeatingAreaRepo seatingAreaRepo;

    public List<Ticket> purchaseTickets(Long userId, Long seatingAreaId, Long eventId, int ticketsNumber) {
        User user = userService.getUserById(userId);
        Event event = eventService.getEventById(eventId);
        SeatingArea seatingArea = seatingAreaService.getById(seatingAreaId);

        LocalDate today = LocalDate.now();
        if (event == null || today.isAfter(event.getDataFineAcquisti())) {
            throw new NotFoundException("Event not found or it's too late to purchase tickets for this event");
        }


        if (seatingArea == null || seatingArea.getAvailableSeat() < ticketsNumber) {
            throw new IllegalArgumentException("Seating area doesn't exist or tickets are not available");
        }

        List<Ticket> tickets = new ArrayList<>();
        BigDecimal ticketPrice = seatingArea.getPrice();


        for (int i = 0; i < ticketsNumber; i++) {
            Ticket ticket = new Ticket();
            ticket.setEvent(event);
            ticket.setUser(user);
            ticket.setSeatingArea(seatingArea);
            ticket.setPrice(ticketPrice);
            ticket.setPaymentDate(LocalDate.now());
            ticketRepo.save(ticket);
            tickets.add(ticket);
        }


        decrementAvailableSeats(seatingAreaId, ticketsNumber);

        return tickets;
    }

    private void decrementAvailableSeats(Long seatingAreaId, int ticketsNumber) {
        SeatingArea seatingArea = seatingAreaService.getById(seatingAreaId);
        if (seatingArea != null) {
            int availableSeats = seatingArea.getAvailableSeat();
            int newAvailableSeats = availableSeats - ticketsNumber;
            if (newAvailableSeats < 0) {
                throw new IllegalStateException("No available seats left in the seating area");
            }
            seatingArea.setAvailableSeat(newAvailableSeats);
            seatingAreaRepo.save(seatingArea);
        } else {
            throw new NotFoundException("Seating area not found with ID: " + seatingAreaId);
        }
    }

}
