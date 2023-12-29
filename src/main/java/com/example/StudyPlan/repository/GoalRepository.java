package com.example.StudyPlan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.StudyPlan.entity.Goal;

public interface GoalRepository extends JpaRepository<Goal, Long> {
}