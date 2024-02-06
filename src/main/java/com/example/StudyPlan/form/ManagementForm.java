package com.example.StudyPlan.form;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ManagementForm {

    private Long id;

    private Long userId;

    private Long bookId;

    private LocalDate completion_date;

    private UserForm user;
    
    private String title;
    
    private LocalDate starting_date;
    
    private LocalDate estimatedcompletion_date;
    
    private BookForm book;

}