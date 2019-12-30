package com.beerhouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.beerhouse.dto.BeerRequestDTO;
import com.beerhouse.dto.BeerResponseDTO;
import com.beerhouse.exceptions.ResourceNotFoundException;
import com.beerhouse.mapper.BeerMapper;
import com.beerhouse.model.Beer;
import com.beerhouse.repository.BeerRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BeerService {

	@Autowired
	private BeerRepository beerRepository;
	
	@Autowired
	private BeerMapper beerMapper;
	
	public BeerResponseDTO create(BeerRequestDTO objDTO) {
		Beer mappedEntity = this.beerMapper.mapRequestDTOtoEntity(objDTO);
		
		Beer entity = this.beerRepository.save(mappedEntity);
		
		return this.beerMapper.mapEntityToResponseDTO(entity);
	}
	
	public BeerResponseDTO update(BeerRequestDTO objDTO) {
		Beer entity = this.beerRepository.findById(objDTO.getId());
		if(entity == null)	{
			log.error("No entity found for id: {}", objDTO.getId());
			throw new ResourceNotFoundException("No resource found for id: " + objDTO.getId());
		}
		entity.setName(objDTO.getName());
		entity.setIngredients(objDTO.getIngredients());
		entity.setCategory(objDTO.getCategory());
		entity.setPrice(objDTO.getPrice());
		entity.setAlcoholContent(convertAlcoholContentFromString(objDTO));
		
		Beer updatedEntity = this.beerRepository.save(entity);
		
		return this.beerMapper.mapEntityToResponseDTO(updatedEntity);
	}
	
	public void delete(Long id) {
		Beer entity = this.beerRepository.findById(id);
		if(entity == null)	{
			log.error("No entity found for id: {}", id);
			throw new ResourceNotFoundException("No resource found for id: " + id);
		}
		
		this.beerRepository.delete(entity);
	}
	
	public BeerResponseDTO findById(Long id) {
		Beer entity = this.beerRepository.findById(id);
		if(entity == null)	{
			log.error("No entity found for id: {}", id);
			throw new ResourceNotFoundException("No resource found for id: " + id);
		}
	
		return this.beerMapper.mapEntityToResponseDTO(entity);
	}
	
	public Page<BeerResponseDTO> findAll(Pageable pageable) {
		Page<Beer> entities = this.beerRepository.findAll(pageable);
		Page<BeerResponseDTO> listDTO = entities.map(this::convertToBeerDTO);
		
		return listDTO;
	}
	
	private Float convertAlcoholContentFromString(BeerRequestDTO objDTO) {
		String alcoholContent = objDTO.getAlcoholContent().substring(0, objDTO.getAlcoholContent().length() - 1);
		
		return Float.valueOf(alcoholContent);
	}
	
	private BeerResponseDTO convertToBeerDTO(Beer entity) {
		return this.beerMapper.mapEntityToResponseDTO(entity);
	}
}
