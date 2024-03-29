package com.example.CAPSTONE.Controllers;

import com.example.CAPSTONE.DTO.TicketDTO;
import com.example.CAPSTONE.DTO.UserDTO;
import com.example.CAPSTONE.Exeptions.BadRequestException;
import com.example.CAPSTONE.Models.Ticket;
import com.example.CAPSTONE.Models.User;
import com.example.CAPSTONE.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseEntity<Page<UserDTO>> getAllUsers(Pageable pageable) {
        Page<User> users = userService.getAllUsers(pageable);
        Page<UserDTO> userDTOS = users.map(this::convertToDTO);
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);

    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable long id) {
        User user = userService.getUserById(id);
        UserDTO userDTO = convertToDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/user/username/{userName}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String userName) {
        User user = userService.getUserByUsername(userName);
        UserDTO userDTO = convertToDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
    @GetMapping("/user/{id}/tickets")
    public ResponseEntity<List<TicketDTO>> getUserTickets(@PathVariable Long id){
        List<Ticket> tickets = userService.getAllTicketFromUser(id);
        List<TicketDTO> ticketDTOS = tickets.stream().map(this::convertToDTOticket).collect(Collectors.toList());
        return new  ResponseEntity<>(ticketDTOS,HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }
        User user = userService.createUser(userDTO);
        UserDTO userDTOr = convertToDTO(user);
        return new ResponseEntity<>(userDTOr, HttpStatus.CREATED);
    }
    @PutMapping("/user/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO,@PathVariable Long id, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }
        User user = userService.updateUser(userDTO,id);
        UserDTO userDTOr = convertToDTO(user);
        return new ResponseEntity<>(userDTOr, HttpStatus.CREATED);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setPassword("**********");
        return dto;
    }
    private TicketDTO convertToDTOticket(Ticket ticket) {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setId(ticket.getId());
        ticketDTO.setEventId(ticket.getEvent().getId());
        ticketDTO.setUserId(ticket.getUser().getId());
        ticketDTO.setSeatingAreaId(ticket.getSeatingArea().getId());
        ticketDTO.setPrice(ticket.getPrice());
        ticketDTO.setPaymentDate(ticket.getPaymentDate());
        return ticketDTO;
    }
}
