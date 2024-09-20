package com.Quiz.one.entity;

import javax.persistence.*;

@Entity
@IdClass(TestBase.class)
public class Test {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "MEMBER_ID")
	private Member member;
	
	@ManyToOne
	@JoinColumn(name = "Server_Name")
	private Orders oreders;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Orders getOreders() {
		return oreders;
	}

	public void setOreders(Orders oreders) {
		this.oreders = oreders;
	}
	
	
}
