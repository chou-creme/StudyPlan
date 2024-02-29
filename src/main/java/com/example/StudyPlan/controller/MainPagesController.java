package com.example.StudyPlan.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.StudyPlan.entity.Goal;
import com.example.StudyPlan.entity.Management;
import com.example.StudyPlan.entity.UserInf;
import com.example.StudyPlan.form.BookForm;
import com.example.StudyPlan.form.ManagementForm;
import com.example.StudyPlan.form.UserForm;
import com.example.StudyPlan.entity.Book;
import com.example.StudyPlan.repository.BookRepository;
import com.example.StudyPlan.repository.GoalRepository;
import com.example.StudyPlan.repository.ManagementRepository;

import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.modelmapper.ModelMapper;

@Controller
public class MainPagesController {

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private GoalRepository goalrepository;
	@Autowired
	private BookRepository bookrepository;
	@Autowired
	private ManagementRepository managementrepository;

	@GetMapping("/mainpages")
	public String index(Principal principal, Model model) throws IOException {
		// ログインユーザのGOALを取得するのであれば必要な処理だが、Goal全権取得するだけなら今はいらない
		Authentication authentication = (Authentication) principal;
		UserInf user = (UserInf) authentication.getPrincipal();

		// TODO: Userに対応したGoalを取得
		Goal goal = goalrepository.findAll().get(1);

		// model.addAttribute("hoge", topics); //hogeという名前で、変数topicsをビューに渡す
		model.addAttribute("goal", goal); // 、変数goalsをビューに渡す

		List<Book> books = bookrepository.findAllByOrderById();
		List<BookForm> list = new ArrayList<>();
		for (Book entity : books) {
			BookForm form = getBook(user, entity);
			list.add(form);
		}
//		String s = "";
//		if (StringUtils.hasText(s)) {
//			
//		}
//		if (s != null && s.isEmpty()) {
//			
//		}
		
		model.addAttribute("books", books);
		return "mainpages/index";
	}
	
	public BookForm getBook(UserInf user, Book entity) throws IOException {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.typeMap(Book.class, BookForm.class).addMappings(mapper -> mapper.skip(BookForm::setUser));
        modelMapper.typeMap(Management.class, ManagementForm.class).addMappings(mapper -> mapper.skip(ManagementForm::setBook));

        BookForm form = modelMapper.map(entity, BookForm.class);
        
        UserForm userForm = modelMapper.map(entity.getUser(), UserForm.class);
        form.setUser(userForm);
        
        //ManagementForm management = new ManagementForm();
            	   //management = modelMapper.map(entity.getManagement(), ManagementForm.class);
                   //if (Objects.isNull(management.getCompletion_date())) {
                       //form.setManagement(management);
                   //}

        return form;
        
	}
	
	public ManagementForm getManagement(UserInf user, Management entity) throws IOException {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.typeMap(Management.class, ManagementForm.class).addMappings(mapper -> mapper.skip(ManagementForm::setUser));

        ManagementForm form = modelMapper.map(entity, ManagementForm.class);
        
        UserForm userForm = modelMapper.map(entity.getUser(), UserForm.class);
        form.setUser(userForm);

        return form;
	}
}