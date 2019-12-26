package com.beerhouse.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beerhouse.dto.BeerDTO;
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
	
	public BeerDTO create(BeerDTO objDTO) {
		Beer mappedEntity = this.beerMapper.mapDTOtoEntity(objDTO);
		
		Beer entity = this.beerRepository.save(mappedEntity);
		
		return this.beerMapper.mapEntityToDTO(entity);
	}
	
	public BeerDTO update(BeerDTO objDTO) {
		Beer entity = this.beerRepository.findById(objDTO.getKey());
		if(entity == null)	{
			log.error("No entity found for id: {}", objDTO.getKey());
			throw new ResourceNotFoundException("No resource found for id: " + objDTO.getKey());
		}
		entity.setName(objDTO.getName());
		entity.setIngredients(objDTO.getIngredients());
		entity.setCategory(objDTO.getCategory());
		entity.setPrice(objDTO.getPrice());
		entity.setAlcoholContent(convertAlcoholContentFromString(objDTO));
		
		Beer updatedEntity = this.beerRepository.save(entity);
		
		return this.beerMapper.mapEntityToDTO(updatedEntity);
	}
	
	public void delete(Long id) {
		Beer entity = this.beerRepository.findById(id);
		if(entity == null)	{
			log.error("No entity found for id: {}", id);
			throw new ResourceNotFoundException("No resource found for id: " + id);
		}
		
		this.beerRepository.delete(entity);
	}
	
	public BeerDTO findById(Long id) {
		Beer entity = this.beerRepository.findById(id);
		if(entity == null)	{
			log.error("No entity found for id: {}", id);
			throw new ResourceNotFoundException("No resource found for id: " + id);
		}
	
		return this.beerMapper.mapEntityToDTO(entity);
	}
	
	public List<BeerDTO> findAll() {
		List<Beer> entities = this.beerRepository.findAll();
		List<BeerDTO> listDTO = entities.stream().map(entity -> this.beerMapper.mapEntityToDTO(entity)).collect(Collectors.toList());
		
		return listDTO;
	}
	
	private Float convertAlcoholContentFromString(BeerDTO objDTO) {
		String alcoholContent = objDTO.getAlcoholContent().substring(0, objDTO.getAlcoholContent().length() - 1);
		
		return Float.valueOf(alcoholContent);
	}
}
