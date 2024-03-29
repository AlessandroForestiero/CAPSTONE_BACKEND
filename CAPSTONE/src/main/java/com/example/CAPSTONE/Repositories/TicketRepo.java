package com.example.CAPSTONE.Repositories;

import com.example.CAPSTONE.Models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface TicketRepo extends JpaRepository<Ticket,Long> , PagingAndSortingRepository<Ticket,Long>{

}
