package com.beerhouse.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Beer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 80, nullable = false)
	private String name;
	
	@Column(length = 200, nullable = false)
	private String ingredients;
	
	@Column(name =  "alcohol_content", precision = 4, scale = 2, nullable = false)
	private Float alcoholContent;
	
	@Column(precision = 4, scale = 2, nullable = false)
	private Float price;
	
	@Column(length = 150, nullable = false)
	private String category;
}
