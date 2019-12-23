package com.beerhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beerhouse.dto.BeerDTO;
import com.beerhouse.service.BeerService;

@RestController
@RequestMapping("/api/beers/v1")
public class BeerController {

	@Autowired
	private BeerService beerService;
	
	@PostMapping
	public ResponseEntity<BeerDTO> create(@RequestBody BeerDTO beer) {
		BeerDTO objDTO = this.beerService.create(beer);
		
		return ResponseEntity.ok().body(objDTO);
	}
	
	@PutMapping
	public ResponseEntity<BeerDTO> update(@RequestBody BeerDTO beer) {
		BeerDTO objDTO = this.beerService.update(beer);
		
		return ResponseEntity.ok().body(objDTO);
	}
	
	@GetMapping
	public ResponseEntity<List<BeerDTO>> findAll() {
		List<BeerDTO> listDTO = this.beerService.findAll();
		
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping(value = "/{beer-id}")
	public ResponseEntity<BeerDTO> findById(@PathVariable("beer-id") Long id) {
		BeerDTO objDTO = this.beerService.findById(id);		
		
		return ResponseEntity.ok().body(objDTO);
	}
	
	@DeleteMapping(value = "/{beer-id}")
	public ResponseEntity<Void> delete(@PathVariable("beer-id") Long id) {
		this.beerService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}
