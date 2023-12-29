package com.example.StudyPlan.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.StudyPlan.entity.Goal;
import com.example.StudyPlan.entity.User;
import com.example.StudyPlan.entity.UserInf;
import com.example.StudyPlan.form.GoalForm;
import com.example.StudyPlan.form.UserForm;
import com.example.StudyPlan.repository.GoalRepository;

@Controller
public class GoalsController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private GoalRepository repository;

    @GetMapping("/users/{userId}/goals/new")
    public String newGoal(@PathVariable("userId") long userId, Model model) {
        GoalForm form = new GoalForm();
        form.setUserId(userId);
        model.addAttribute("form", form);

        return "goals/new";
    }

    @PostMapping("/users/{userId}/goal")
    public String create(@PathVariable("userId") long userId, @Validated @ModelAttribute("form") GoalForm form,
            BindingResult result, Model model, RedirectAttributes redirAttrs, Locale locale) {
        if (result.hasErrors()) {
            model.addAttribute("hasMessage", true);
            model.addAttribute("class", "alert-danger");
            model.addAttribute("message", messageSource.getMessage("goals.create.flash.1", new String[] {}, locale));
            return "golas/new";
        }

        Goal entity = modelMapper.map(form, Goal.class);
        entity.setUserId(userId);
        repository.saveAndFlush(entity);

        redirAttrs.addFlashAttribute("hasMessage", true);
        redirAttrs.addFlashAttribute("class", "alert-info");
        redirAttrs.addFlashAttribute("message",
                messageSource.getMessage("goals.create.flash.2", new String[] {}, locale));

        return "redirect:/maimpage";
    }
    
    public GoalForm getGoal(UserInf user, Goal entity) throws FileNotFoundException, IOException {
    	modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.typeMap(Goal.class, GoalForm.class).addMappings(mapper -> mapper.skip(GoalForm::setUser));
    	modelMapper.typeMap(User.class, UserForm.class).addMappings(mapper -> mapper.skip(UserForm::setGoals));
    
    	UserForm form = modelMapper.map(entity, UserForm.class);
    	
    	List<GoalForm> goals = new ArrayList<GoalForm>();
    	
    	       for (Goal goalEntity : entity.getGoals()) {
    	           GoalForm goal = modelMapper.map(goalEntity, GoalForm.class);
    	           goals.add(goal);
    	       }
    	       form.setGoals(goals);
    
    }
}