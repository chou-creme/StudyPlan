package com.example.StudyPlan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.StudyPlan.entity.Goal;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findAll();
}