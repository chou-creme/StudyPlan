package com.example.StudyPlan.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.StudyPlan.entity.Book;
import com.example.StudyPlan.entity.User;

public interface BookRepository extends JpaRepository<Book, Long> {

	public List<Book> findAllByOrderById();
	
	Book getOne(Long id);
	
}