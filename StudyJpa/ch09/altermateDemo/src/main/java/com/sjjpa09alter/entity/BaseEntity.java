package com.sjjpa09alter.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity {

	private String createBy;
	
	@CreationTimestamp
	private LocalDateTime creationData;
	
	private String lastModifiedBy;
	
	@UpdateTimestamp
	private LocalDateTime lastModified;

	
	
}
