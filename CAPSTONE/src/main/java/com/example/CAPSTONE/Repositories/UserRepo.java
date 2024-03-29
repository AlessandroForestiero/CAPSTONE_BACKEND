package com.example.CAPSTONE.Repositories;

import com.example.CAPSTONE.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository <User,Long>, PagingAndSortingRepository<User,Long> {
    Optional<User> findByUsername(String username);
}
