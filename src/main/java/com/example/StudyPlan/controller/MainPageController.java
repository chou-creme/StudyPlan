package com.example.StudyPlan.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.StudyPlan.entity.Goal;
import com.example.StudyPlan.repository.GoalRepository;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;

@Controller
public class MainPageController {

	@Autowired
    private ModelMapper modelMapper;
    @Autowired
    private GoalRepository repository;
    
    @GetMapping("/mainpage")
    public String index(Principal principal, Model model) throws IOException {
        //ログインユーザのGOALを取得するのであれば必要な処理だが、Goal全権取得するだけなら今はいらない
        //Authentication authentication = (Authentication) principal;
        //UserInf user = (UserInf) authentication.getPrincipal();

        List<Goal> goals = repository.findAll();

        //model.addAttribute("hoge", topics); //hogeという名前で、変数topicsをビューに渡す
        model.addAttribute("goals", goals); //goalsという名前で、変数goalsをビューに渡す
        return "mainpage/index";
    }
}