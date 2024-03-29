package com.example.CAPSTONE.Repositories;

import com.example.CAPSTONE.Models.SeatingArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface SeatingAreaRepo extends JpaRepository<SeatingArea,Long>, PagingAndSortingRepository<SeatingArea,Long> {
    List<SeatingArea> findByEventId(Long id);
}
