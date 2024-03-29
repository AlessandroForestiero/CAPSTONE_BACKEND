package com.example.CAPSTONE.Services;

import com.example.CAPSTONE.Exeptions.NotFoundException;
import com.example.CAPSTONE.Models.Event;
import com.example.CAPSTONE.Models.SeatingArea;
import com.example.CAPSTONE.Models.Ticket;
import com.example.CAPSTONE.Models.User;
import com.example.CAPSTONE.Repositories.EventRepo;
import com.example.CAPSTONE.Repositories.SeatingAreaRepo;
import com.example.CAPSTONE.Repositories.TicketRepo;
import com.example.CAPSTONE.Repositories.UserRepo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TicketService {
    @Autowired
    private TicketRepo ticketRepo;
    @Autowired
    private EventRepo eventRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private SeatingAreaRepo seatingAreaRepo;

    public Ticket createTicket(Long eventId, Long userId, Long seatingAreaId) {
        Event event = eventRepo.findById(eventId).orElseThrow();
        User user = userRepo.findById(userId).orElseThrow();
        SeatingArea seatingArea = seatingAreaRepo.findById(seatingAreaId).orElseThrow();
        Ticket ticket = new Ticket();
        ticket.setEvent(event);
        ticket.setUser(user);
        ticket.setSeatingArea(seatingArea);
        ticket.setPrice(seatingArea.getPrice());
        ticket.setPaymentDate(LocalDate.now());
        return ticketRepo.save(ticket);
    }

    public String generateQrCodeData(Ticket ticket) {
        return "TicketID: " + ticket.getId() + ", Event: " + ticket.getEvent().getNome() + ", User: " + ticket.getUser().getUsername();
    }

    public byte[] generateQrCodeImage(String data) {

        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);

            return byteArrayOutputStream.toByteArray();
        } catch (WriterException | IOException e) {

            e.printStackTrace();
            return null;
        }
    }

    public List<Ticket> getTicketsByUser(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new NotFoundException("user not found"));
        return user.getOwnTickets();
    }
    public List<Ticket> getTicketsByEvent(Long id){
        Event event = eventRepo.findById(id).orElseThrow(()-> new NotFoundException("event not found"));
        return event.getTickets();
    }
    public Ticket getById(Long id) {
        return ticketRepo.findById(id).orElseThrow();
    }

    public Page<Ticket> getAllTickets(Pageable pageable) {
        return ticketRepo.findAll(pageable);
    }

    public void deleteTicket(Long id) {
        Ticket ticket = getById(id);
        ticketRepo.delete(ticket);
    }

}