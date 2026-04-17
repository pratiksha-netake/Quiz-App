package com.QuizApp.QuizApp.Model;

import lombok.Data;

@Data
public class Response {
	private Integer id;
	private String response;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setResponse(String response) {
		this.response = response;
	}
	public String getResponse() {		
		return response;
	}

}
