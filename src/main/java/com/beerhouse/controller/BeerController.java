package com.beerhouse.controller;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beerhouse.dto.BeerRequestDTO;
import com.beerhouse.dto.BeerResponseDTO;
import com.beerhouse.service.BeerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Beers endpoint", description = "Beers resource endpoint", tags = { "Beers" })
@RestController
@RequestMapping("/api/beers/v1")
public class BeerController {

	@Autowired
	private BeerService beerService;
	
	@Autowired
	private PagedResourcesAssembler<BeerResponseDTO> assembler;
	
	@ApiOperation(value = "Creates a beer entity")
	@PostMapping(consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<BeerResponseDTO> create(@RequestBody BeerRequestDTO beer) {
		BeerResponseDTO objDTO = this.beerService.create(beer);
		objDTO.add(linkTo(methodOn(BeerController.class).findById(objDTO.getKey())).withSelfRel());
		
		return ResponseEntity.ok().body(objDTO);
	}
	
	@ApiOperation(value = "Updates an existing beer entity")
	@PutMapping(value = "/{beer-id}", consumes = { "application/json" }, 
			produces = { "application/json" })
	public ResponseEntity<BeerResponseDTO> update(@PathVariable("beer-id") Long id, @RequestBody BeerRequestDTO beer) {
		beer.setId(id);
		BeerResponseDTO objDTO = this.beerService.update(beer);
		objDTO.add(linkTo(methodOn(BeerController.class).findById(objDTO.getKey())).withSelfRel());
		
		return ResponseEntity.ok().body(objDTO);
	}
	
	@ApiOperation(value = "Returns a list of all beers")
	@GetMapping(produces = { "application/json" })
	public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page, 
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {
		
		Direction sortDirection = "desc".equalsIgnoreCase(direction) ? DESC : ASC;
		
		Pageable pageable = new PageRequest(page, limit, sortDirection, "name");
		
		Page<BeerResponseDTO> beers = this.beerService.findAll(pageable);
		beers.forEach(objDTO -> objDTO.add(linkTo(methodOn(BeerController.class).findById(objDTO.getKey())).withSelfRel()));
		
		PagedResources<?> resources = assembler.toResource(beers);
		
		return new ResponseEntity<>(resources, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Returns a beer by id")
	@GetMapping(value = "/{beer-id}", produces = { "application/json" })
	public ResponseEntity<BeerResponseDTO> findById(@PathVariable("beer-id") Long id) {
		BeerResponseDTO objDTO = this.beerService.findById(id);		
		objDTO.add(linkTo(methodOn(BeerController.class).findById(objDTO.getKey())).withSelfRel());
		
		return ResponseEntity.ok().body(objDTO);
	}
	
	@ApiOperation(value = "Deletes a beer by id")
	@DeleteMapping(value = "/{beer-id}")
	public ResponseEntity<Void> delete(@PathVariable("beer-id") Long id) {
		this.beerService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Executes a partial update on an existing beer")
	@PatchMapping(value = "/{beer-id}", consumes = { "application/json" }, 
			produces = { "application/json" })
	public ResponseEntity<BeerResponseDTO> patch(@PathVariable("beer-id") Long id, @RequestBody BeerRequestDTO beer) {
		beer.setId(id);
		BeerResponseDTO objDTO = this.beerService.update(beer);
		objDTO.add(linkTo(methodOn(BeerController.class).findById(objDTO.getKey())).withSelfRel());
		
		return ResponseEntity.ok().body(objDTO);
		
	}
}
