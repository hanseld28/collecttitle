package com.algaworks.collecttitle.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.collecttitle.model.Title;
import com.algaworks.collecttitle.model.TitleStatus;
import com.algaworks.collecttitle.repository.Titles;

@Service
public class TitleService {
	
	@Autowired
	private Titles titles;
	
	public void save(Title title) {
		try {
			titles.save(title);
		} catch(DataIntegrityViolationException ex) {
			throw new IllegalArgumentException("Formato de data inválido");
		}
	}

	public void deleteById(Long id) {
		titles.deleteById(id);
	}

	public List<Title> findAll() {
		return titles.findAll();
	}

	public String receive(Long id) {
		Optional<Title> response = titles.findById(id);
		
		if(response.isPresent()) {
			Title title = response.get();
			title.setStatus(TitleStatus.RECEBIDO);
			titles.save(title);
			
			return title.getStatus().getDescription();
		}
		return null;
	}

	public List<Title> findByDescriptionContaining(String description) {
		return titles.findByDescriptionContaining(description);
	}
}
