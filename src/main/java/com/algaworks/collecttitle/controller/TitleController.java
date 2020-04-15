package com.algaworks.collecttitle.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.collecttitle.model.Title;
import com.algaworks.collecttitle.model.TitleStatus;
import com.algaworks.collecttitle.repository.filter.TitleFilter;
import com.algaworks.collecttitle.service.TitleService;


@Controller
@RequestMapping(value = "/titles")
public class TitleController {
	
	private static final String VIEW_REGISTER = "RegisterTitle";
	private static final String VIEW_SEARCH = "SearchTitle";
	private static final String REDIRECT_VIEW_SEARCH = "redirect:/titles";
	private static final String REDIRECT_VIEW_REGISTER = "redirect:/titles/new";
	
	@Autowired
	private TitleService titleService;
	
	@GetMapping("/new")
	public ModelAndView newTitle() {
		ModelAndView modelAndView = new ModelAndView(VIEW_REGISTER);
		modelAndView.addObject(new Title());
		
		return modelAndView;
	}
	
	@PostMapping
	public String save(@Validated Title title, Errors errors, RedirectAttributes attributes) {
		if(errors.hasErrors()) {
			return VIEW_REGISTER;
		}
		try {
			titleService.save(title);
			attributes.addFlashAttribute("message", "Título salvo com sucesso!");
			return REDIRECT_VIEW_REGISTER;
		} catch(IllegalArgumentException ex) {
			errors.rejectValue("expirationDate", null, ex.getMessage());
			return VIEW_REGISTER;
		}
	}
	
	@GetMapping
	public ModelAndView search(@ModelAttribute("filter") TitleFilter filter) {
		String description = filter.getDescription();
		List<Title> titlesFiltered = titleService.findByDescriptionContaining(description);
		
		ModelAndView modelAndView = new ModelAndView(VIEW_SEARCH);
		modelAndView.addObject("titles", titlesFiltered);
		
		return modelAndView;
	}
	
	@GetMapping(value = "/{id}")
	public ModelAndView edit(@PathVariable("id") Title title) {
		ModelAndView modelAndView = new ModelAndView(VIEW_REGISTER);
		modelAndView.addObject(title);
		
		return modelAndView;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public String delete(@PathVariable("id") Long id, RedirectAttributes attributes) {
		titleService.deleteById(id);
		
		attributes.addFlashAttribute("message", "Título excluído com sucesso!");
		
		return REDIRECT_VIEW_SEARCH;
	}
	
	@RequestMapping(value = "/{id}/receive", method = RequestMethod.PUT)
	public @ResponseBody String receive(@PathVariable("id") Long id) {	
		return titleService.receive(id);
	}
	
	
	
	@ModelAttribute("allTitleStatus")
	public List<TitleStatus> allTitleStatus() {
		return Arrays.asList(TitleStatus.values());
	}
	
}
