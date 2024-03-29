package com.example.CAPSTONE.DTO;

import lombok.Data;

@Data
public class TransactionDataDTO {

        private Long userId;
        private Long seatingAreaId;
        private Long eventId;
        private int ticketsNumber;


    }