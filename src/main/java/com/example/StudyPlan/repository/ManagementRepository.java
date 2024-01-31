package com.example.StudyPlan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.StudyPlan.entity.Management;

public interface ManagementRepository extends JpaRepository<Management, Long> {

	List<Management> findAllByOrderById();
}