package com.sjjpa09alter.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@DynamicInsert
@DynamicUpdate
public class FavorateFood {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long it;
	
	private String Foodname;
	
	@ManyToOne
	@JoinColumn(name = "MEMBER_ID")
	Member member;
}
