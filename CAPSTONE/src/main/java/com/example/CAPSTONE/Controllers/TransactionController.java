package com.example.CAPSTONE.Controllers;

import com.example.CAPSTONE.DTO.TicketDTO;
import com.example.CAPSTONE.DTO.TransactionDataDTO;
import com.example.CAPSTONE.Models.Ticket;
import com.example.CAPSTONE.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TransactionController {

        @Autowired
        private UserService userService;

        @Autowired
        private TicketService ticketService;

        @Autowired
        private SeatingAreaService seatingAreaService;

        @Autowired
        private EventService eventService;

        @Autowired
        private TransactionService transactionService;

    @PostMapping("/purchase")
    public ResponseEntity<List<TicketDTO>> purchaseTickets(@RequestBody TransactionDataDTO request) {
        Long userId = request.getUserId();
        Long seatingAreaId = request.getSeatingAreaId();
        Long eventId = request.getEventId();
        int ticketsNumber = request.getTicketsNumber();

        List<Ticket> purchasedTickets = transactionService.purchaseTickets(userId, seatingAreaId, eventId, ticketsNumber);
        List<TicketDTO> ticketDTOs = purchasedTickets.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(ticketDTOs, HttpStatus.CREATED);
    }

    private TicketDTO convertToDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setId(ticket.getId());
        dto.setEventId(ticket.getEvent().getId());
        dto.setUserId(ticket.getUser().getId());
        dto.setSeatingAreaId(ticket.getSeatingArea().getId());
        dto.setPrice(ticket.getPrice());
        dto.setPaymentDate(ticket.getPaymentDate());
        return dto;
    }

}
