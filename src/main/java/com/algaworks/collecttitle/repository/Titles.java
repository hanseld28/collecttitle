package com.algaworks.collecttitle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.collecttitle.model.Title;

public interface Titles extends JpaRepository<Title, Long> {
	
	public List<Title> findByDescriptionContaining(String description);
}
