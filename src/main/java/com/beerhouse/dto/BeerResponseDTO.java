package com.beerhouse.dto;

import java.io.Serializable;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({ "id", "name", "ingredients", "alcoholContent", "price", "category" })
public class BeerResponseDTO extends ResourceSupport implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	private Long key;

	private String name;

	private String ingredients;

	private String alcoholContent;

	private Float price;

	private String category;
}
