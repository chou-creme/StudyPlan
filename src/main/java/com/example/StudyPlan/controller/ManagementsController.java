package com.example.StudyPlan.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.security.Principal;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.StudyPlan.entity.Book;
import com.example.StudyPlan.entity.Management;
import com.example.StudyPlan.entity.UserInf;
import com.example.StudyPlan.form.BookForm;
import com.example.StudyPlan.form.ManagementForm;
import com.example.StudyPlan.repository.BookRepository;
import com.example.StudyPlan.repository.ManagementRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ManagementsController {

	private static final Logger log = LoggerFactory.getLogger(ManagementsController.class);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ManagementRepository repository;

	@Autowired
	private BookRepository bookrepository;

	// @GetMapping("/books/{bookId}/managements/new")
	// public String newManagement(@PathVariable("bookId") long bookId, Model model)
	// {
	// ManagementForm form = new ManagementForm();
	// form.setBookId(bookId);
	// model.addAttribute("form", form);

	// return "managements/new";
	// }

	@PostMapping("/books/{bookId}/management")
	public String create(Principal principal, @PathVariable("bookId") long bookId, @Validated @ModelAttribute("form") ManagementForm form,
			BindingResult result, Model model, RedirectAttributes redirAttrs, Locale locale) {
		if (result.hasErrors()) {
			model.addAttribute("hasMessage", true);
			model.addAttribute("class", "alert-danger");
			model.addAttribute("message", "登録に失敗しました。");
			return "managements/new";
		}

		Management entity = modelMapper.map(form, Management.class);
		Authentication authentication = (Authentication) principal;
        UserInf user = (UserInf) authentication.getPrincipal();
        entity.setUserId(user.getUserId());
		entity.setBookId(bookId);
		repository.saveAndFlush(entity);

		redirAttrs.addFlashAttribute("hasMessage", true);
		redirAttrs.addFlashAttribute("class", "alert-info");
		redirAttrs.addFlashAttribute("message", "登録が完了しました。");

		return "redirect:/mainpages";
	}

	@GetMapping("/books/{bookId}/managements/new")
	public String showNewManagement(@PathVariable("bookId") long bookId, Model model) {
		// ここでデータベースからbookを取得し、モデルにセットする
		ManagementForm form = new ManagementForm();

		Book book = bookrepository.getOne(bookId);

		// log.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		// log.info(String.valueOf(bookId));
		form.setTitle(book.getTitle());
		form.setStarting_date(book.getStarting_date());
		model.addAttribute("form", form);
		model.addAttribute("book", book);

		List<LocalDate> dates = getDatesBetween(book.getStarting_date(), book.getEstimatedcompletion_date());
		model.addAttribute("dates", dates);

		return "managements/new"; // Thymeleafのテンプレート名を返す
	}

	//目標完了日の列を出す
	public static List<LocalDate> getDatesBetween(LocalDate starting_date, LocalDate estimatedcompletion_date) {

		return starting_date.datesUntil(estimatedcompletion_date.plusDays(1)).collect(Collectors.toList());
	}
	
	//完了日列の入力フォームを作る
	public String newCompletionDate(Model model) {
        model.addAttribute("form", new ManagementForm());
        return "/books/{bookId}/managements/new";
	}
}