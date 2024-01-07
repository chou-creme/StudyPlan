package com.example.StudyPlan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.StudyPlan.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	List<Book> findAllByOrderByBookId();
}