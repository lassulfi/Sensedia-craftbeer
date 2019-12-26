package com.beerhouse.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
	
	@PostMapping(consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<BeerDTO> create(@RequestBody BeerDTO beer) {
		BeerDTO objDTO = this.beerService.create(beer);
		objDTO.add(linkTo(methodOn(BeerController.class).findById(objDTO.getKey())).withSelfRel());
		
		return ResponseEntity.ok().body(objDTO);
	}
	
	@PutMapping(value = "/{beer-id}", consumes = { "application/json" }, 
			produces = { "application/json" })
	public ResponseEntity<BeerDTO> update(@PathVariable("beer-id") Long id, @RequestBody BeerDTO beer) {
		beer.setKey(id);
		BeerDTO objDTO = this.beerService.update(beer);
		objDTO.add(linkTo(methodOn(BeerController.class).findById(objDTO.getKey())).withSelfRel());
		
		return ResponseEntity.ok().body(objDTO);
	}
	
	@GetMapping(produces = { "application/json" })
	public ResponseEntity<List<BeerDTO>> findAll() {
		List<BeerDTO> listDTO = this.beerService.findAll();
		listDTO.forEach(objDTO -> objDTO.add(linkTo(methodOn(BeerController.class).findById(objDTO.getKey())).withSelfRel()));
		
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping(value = "/{beer-id}", produces = { "application/json" })
	public ResponseEntity<BeerDTO> findById(@PathVariable("beer-id") Long id) {
		BeerDTO objDTO = this.beerService.findById(id);		
		objDTO.add(linkTo(methodOn(BeerController.class).findById(objDTO.getKey())).withSelfRel());
		
		return ResponseEntity.ok().body(objDTO);
	}
	
	@DeleteMapping(value = "/{beer-id}")
	public ResponseEntity<Void> delete(@PathVariable("beer-id") Long id) {
		this.beerService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping(value = "/{beer-id}", consumes = { "application/json" }, 
			produces = { "application/json" })
	public ResponseEntity<BeerDTO> patch(@PathVariable("beer-id") Long id, @RequestBody BeerDTO beer) {
		beer.setKey(id);
		BeerDTO objDTO = this.beerService.update(beer);
		objDTO.add(linkTo(methodOn(BeerController.class).findById(objDTO.getKey())).withSelfRel());
		
		return ResponseEntity.ok().body(objDTO);
		
	}
}
