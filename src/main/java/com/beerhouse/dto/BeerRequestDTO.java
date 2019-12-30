package com.beerhouse.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({ "id", "name", "ingredients", "alcoholContent", "price", "category" })
public class BeerRequestDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String ingredients;
	private String alcoholContent;
	private Float price;
	private String category;
}
