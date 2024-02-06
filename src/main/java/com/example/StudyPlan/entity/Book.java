package com.example.StudyPlan.entity;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "books")
@Data
@EqualsAndHashCode(callSuper = false)
public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "books_id_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private int pages;

    @Column(nullable = false)
    private LocalDate starting_date;

    @Column(nullable = false)
    private LocalDate estimatedcompletion_date;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;
    
    @Column(nullable = false)
    private Long userId;

    //進捗管理画面で1日のペースを表示する
    public int calcTargetPage(int lapsedDays) {
    	if (lapsedDays == pages) return pages;

    	return (int) Math.ceil(1.0 * pages / requiredDayNum() * lapsedDays);
    }
    
    private int requiredDayNum() {
    	return (int) ChronoUnit.DAYS.between(starting_date, estimatedcompletion_date);
    }
    
    @OneToOne(mappedBy = "book")
    public Management management;
    
    public boolean hasCompleted() {
    	return management != null && management.getCompletion_date() != null;
    }
 }