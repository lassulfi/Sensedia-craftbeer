package com.beerhouse.mapper;

import org.springframework.stereotype.Service;

import com.beerhouse.dto.BeerRequestDTO;
import com.beerhouse.dto.BeerResponseDTO;
import com.beerhouse.model.Beer;

@Service
public class BeerMapper {

	public BeerResponseDTO mapEntityToResponseDTO(Beer entity) {
		BeerResponseDTO objDTO = new BeerResponseDTO();
		objDTO.setKey(entity.getId());
		objDTO.setName(entity.getName());
		objDTO.setCategory(entity.getCategory());
		objDTO.setIngredients(entity.getIngredients());
		objDTO.setAlcoholContent(String.format("%.1f", entity.getAlcoholContent()) + "%");
		objDTO.setPrice(entity.getPrice());
		
		return objDTO;
	}
	
	public Beer mapRequestDTOtoEntity(BeerRequestDTO objDTO) {
		Beer entity = new Beer();
		entity.setId(objDTO.getId());
		entity.setName(objDTO.getName());
		entity.setCategory(objDTO.getCategory());
		entity.setIngredients(objDTO.getIngredients());
		entity.setPrice(objDTO.getPrice());
		
		String alcoholContent = objDTO.getAlcoholContent().substring(0, objDTO.getAlcoholContent().length() - 1);
		entity.setAlcoholContent(Float.valueOf(alcoholContent));
		
		return entity;
	}
}
