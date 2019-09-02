package com.example.springframeworkhateoas.repository;

import com.example.springframeworkhateoas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {
}
