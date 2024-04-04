package com.example.CAPSTONE.DTO;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String token;
    private long userId;
    private String userName;
    public AuthResponseDTO(String token, long userId, String userName) {
        this.token = token;
        this.userId = userId;
        this.userName = userName;
    }
}
