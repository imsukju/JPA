package com.Quiz.one.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Orders {

	@Id
	@Column(name = "Server_Name")
	private String name;
	
	
	@OneToMany(mappedBy = "oreders", fetch = FetchType.EAGER)
	private List<Test> Tests = new ArrayList<>();

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public List<Test> getTests() {
		return Tests;
	}


	public void setTests(List<Test> tests) {
		Tests = tests;
	}



		
	
	
}
