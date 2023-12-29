package com.example.StudyPlan.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GoalForm {

    private Long id;

    private Long userId;

    @NotEmpty
    @Size(max = 1000)
    private String description;
}
