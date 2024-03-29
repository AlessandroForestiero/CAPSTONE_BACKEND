package com.example.CAPSTONE.Services;

import com.example.CAPSTONE.DTO.UserDTO;
import com.example.CAPSTONE.Exeptions.NotFoundException;
import com.example.CAPSTONE.Models.Ticket;
import com.example.CAPSTONE.Models.User;
import com.example.CAPSTONE.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public User getUserById(long id) {
        return userRepo.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
    }

    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> new NotFoundException("Username not found"));
    }

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return userRepo.save(user);
    }


    public User updateUser(UserDTO userDTO, long id) throws NotFoundException {
        User user = getUserById(id);
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return userRepo.save(user);
    }

    public void deleteUser(long id) throws NotFoundException {
        userRepo.delete(getUserById(id));
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepo.findAll(pageable);
    }

    public List<Ticket> getAllTicketFromUser(Long id) {
        User user = getUserById(id);
        List<Ticket> tickets = user.getOwnTickets();
        return tickets;
    }

}
