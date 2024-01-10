package com.example.StudyPlan.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.StudyPlan.entity.Goal;
import com.example.StudyPlan.entity.UserInf;
import com.example.StudyPlan.form.BookForm;
import com.example.StudyPlan.form.UserForm;
import com.example.StudyPlan.entity.Book;
import com.example.StudyPlan.repository.BookRepository;
import com.example.StudyPlan.repository.GoalRepository;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.modelmapper.ModelMapper;

@Controller
public class MainPageController {

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private GoalRepository goalrepository;
	@Autowired
	private BookRepository bookrepository;

	@GetMapping("/mainpage")
	public String index(Principal principal, Model model) throws IOException {
		// ログインユーザのGOALを取得するのであれば必要な処理だが、Goal全権取得するだけなら今はいらない
		Authentication authentication = (Authentication) principal;
		UserInf user = (UserInf) authentication.getPrincipal();

		List<Goal> goals = goalrepository.findAll();

		// model.addAttribute("hoge", topics); //hogeという名前で、変数topicsをビューに渡す
		model.addAttribute("goals", goals); // goalsという名前で、変数goalsをビューに渡す

		List<Book> books = bookrepository.findAllByOrderById();
		List<BookForm> list = new ArrayList<>();
		for (Book entity : books) {
			BookForm form = getBook(user, entity);
			list.add(form);
		}
		model.addAttribute("books", books);
		return "mainpage/index";
	}
	
	public BookForm getBook(UserInf user, Book entity) throws IOException {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.typeMap(Book.class, BookForm.class).addMappings(mapper -> mapper.skip(BookForm::setUser));

        BookForm form = modelMapper.map(entity, BookForm.class);
        
        UserForm userForm = modelMapper.map(entity.getUser(), UserForm.class);
        form.setUser(userForm);

        return form;
	}
}