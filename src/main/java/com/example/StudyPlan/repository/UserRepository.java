package com.example.StudyPlan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.StudyPlan.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}