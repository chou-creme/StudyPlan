package com.example.StudyPlan.controller;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.StudyPlan.entity.Goal;
import com.example.StudyPlan.entity.UserInf;
import com.example.StudyPlan.form.GoalForm;
import com.example.StudyPlan.form.UserForm;
import com.example.StudyPlan.repository.GoalRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class GoalsController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private GoalRepository repository;

	@Autowired
	private HttpServletRequest request;

	@GetMapping("/goals")
	public String newGoal(Model model) {
		model.addAttribute("form", new GoalForm());
		return "goals/new";
	}

	@PostMapping("/goal")
	public String create(Principal principal, @Validated @ModelAttribute("form") GoalForm form, BindingResult result,
			Model model, RedirectAttributes redirAttrs) throws IOException {
		if (result.hasErrors()) {
			model.addAttribute("hasMessage", true);
			model.addAttribute("class", "alert-danger");
			model.addAttribute("message", "投稿に失敗しました。");
			return "goals/new";
		}

		Goal entity = new Goal();
		Authentication authentication = (Authentication) principal;
		UserInf user = (UserInf) authentication.getPrincipal();
		entity.setUserId(user.getUserId());
		entity.setDescription(form.getDescription());
		repository.saveAndFlush(entity);

		redirAttrs.addFlashAttribute("hasMessage", true);
		redirAttrs.addFlashAttribute("class", "alert-info");
		redirAttrs.addFlashAttribute("message", "投稿に成功しました。");

		return "redirect:/mainpages";
	}

	@GetMapping("/goals/{id}/edit") 
	public String edit(@PathVariable("id") long id, Model model) {
		// TODO: 編集対象のGoalを取得する
		//   TODO: 現状は全件取得しているので、必要なレコードだけとってくる
		Goal goal = repository.findById(id).get();
		// TODO: 編集画面で必要なEntityを集めて、model.addAttributeする
		//   TODO: 画面表示に必要なEntityはこれで全部？
		//   TODO: newGoalでコンパイルエラーが出てるので直す
		
		GoalForm form = new GoalForm();
		form.setDescription(goal.getDescription());
		model.addAttribute("form",form);
		model.addAttribute("goal", goal);
		//model = newGoal(model);
		model.addAttribute("path", "update");
		return "goals/edit";
	}

	/**
	 * 更新する
	 *
	 * @param form  フォーム
	 * @param model モデル
	 * @return テンプレート
	 */
	@PostMapping("/goals/{id}/update")
	public String update(Principal principal, @Validated @ModelAttribute("form") GoalForm form, @PathVariable("id") long id, BindingResult result,
			Model model, RedirectAttributes redirAttrs) throws IOException {
		if (result.hasErrors()) {
			model.addAttribute("hasMessage", true);
			model.addAttribute("class", "alert-danger");
			model.addAttribute("message", "投稿に失敗しました。");
			return "goals/edit";
		}
		//登録対象のGoalの必要なレコードだけを取得する
		//編集したentityをデータベースに保存する
		//編集画面で必要なEntityを集めて、model.addAttributeする
		
		Goal entity = repository.findById(id).get();
		entity.setDescription(form.getDescription());
		repository.saveAndFlush(entity);

		redirAttrs.addFlashAttribute("hasMessage", true);
		redirAttrs.addFlashAttribute("class", "alert-info");
		redirAttrs.addFlashAttribute("message", "投稿に成功しました。");

		return "redirect:/mainpages";
	}
}