package com.example.StudyPlan.form;

import java.sql.Date;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookForm {

    private Long id;

    private Long userId;

    private String path;

    @NotEmpty
    @Size(max = 1000)
    private String title;

    @NotEmpty
    @Size(max = 1000)
    private int pages;

    @NotEmpty
    private Date starting_date;

    @NotEmpty
    private Date estimatedcompletion_date;

    private UserForm user;

}