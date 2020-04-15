package com.algaworks.collecttitle.model;

public enum TitleStatus {
	
	PENDENTE("Pendente"),
	RECEBIDO("Recebido");
	
	private String description;
	
	TitleStatus(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
}
