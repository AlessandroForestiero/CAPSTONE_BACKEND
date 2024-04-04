package com.example.CAPSTONE.Controllers;

import com.example.CAPSTONE.DTO.AuthResponseDTO;
import com.example.CAPSTONE.DTO.LoginDTO;
import com.example.CAPSTONE.DTO.UserDTO;
import com.example.CAPSTONE.Exeptions.BadRequestException;
import com.example.CAPSTONE.Exeptions.LoginFaultException;
import com.example.CAPSTONE.Models.User;
import com.example.CAPSTONE.Repositories.UserRepo;
import com.example.CAPSTONE.Security.JwtTools;
import com.example.CAPSTONE.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private JwtTools jwtTools;
    @Autowired
    private UserService userService;

    @PostMapping("/auth/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody @Validated UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }

        User user = userService.createUser(userDTO);
        String token = jwtTools.createToken(user);
        AuthResponseDTO authResponse = new AuthResponseDTO(token, user.getId(), user.getName());

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Validated LoginDTO loginDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().toString());
        }

        User user = userService.getUserByUsername(loginDTO.getUserName());

        if (user != null && user.getPassword().equals(loginDTO.getPassword())) {
            String token = jwtTools.createToken(user);
            AuthResponseDTO authResponse = new AuthResponseDTO(token,user.getId(),user.getName());
            return ResponseEntity.ok(authResponse);

        } else {
            throw new LoginFaultException("bad username or passoword");
        }

    }
}
