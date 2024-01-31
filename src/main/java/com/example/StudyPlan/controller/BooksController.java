package com.example.StudyPlan.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.StudyPlan.entity.Book;
import com.example.StudyPlan.entity.UserInf;
import com.example.StudyPlan.form.BookForm;
import com.example.StudyPlan.form.UserForm;
import com.example.StudyPlan.repository.BookRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BooksController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BookRepository repository;
    @Autowired
    private HttpServletRequest request;

    @GetMapping("/books")
    public String newBook(Model model) {
        model.addAttribute("form", new BookForm());
        return "books/new";
    }

    @PostMapping("/book")
    public String create(Principal principal, @Validated @ModelAttribute("form") BookForm form, BindingResult result,
            Model model, RedirectAttributes redirAttrs)
            throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("hasMessage", true);
            model.addAttribute("class", "alert-danger");
            model.addAttribute("message", "登録に失敗しました。");
            return "books/new";
        }

        Book entity = new Book();
        Authentication authentication = (Authentication) principal;
        UserInf user = (UserInf) authentication.getPrincipal();
        entity.setUserId(user.getUserId());
        entity.setTitle(form.getTitle());
        entity.setPages(form.getPages());
        entity.setStarting_date(form.getStarting_date());
        entity.setEstimatedcompletion_date(form.getEstimatedcompletion_date());
        repository.saveAndFlush(entity);

        redirAttrs.addFlashAttribute("hasMessage", true);
        redirAttrs.addFlashAttribute("class", "alert-info");
        redirAttrs.addFlashAttribute("message", "登録に成功しました。");

        return "redirect:/mainpages";
    }
}