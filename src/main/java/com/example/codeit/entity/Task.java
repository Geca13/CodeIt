package com.example.codeit.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
	
	@Id
	private Integer id;
	
	private String description;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	private Integer minRepeats;
	
	private Boolean isRisky;
	
	private String url;

}
