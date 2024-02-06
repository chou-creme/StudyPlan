package com.example.StudyPlan.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.StudyPlan.entity.Management;

public interface ManagementRepository extends JpaRepository<Management, Long> {

    public Optional<Management> findById(Long id);
	
	List<Management> findAllByOrderById();
}