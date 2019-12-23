package com.beerhouse.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.beerhouse.dto.BeerDTO;
import com.beerhouse.exceptions.ResourceNotFoundException;
import com.beerhouse.repository.BeerRepository;
import com.beerhouse.service.BeerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class BeerControllerTest {

	private static final String BEER_API_URL = "/api/beers/v1/";
	private static final Long ID = 1L;
	private static final String NAME = "My Beer";
	private static final String INGREDIENTS = "My favorite ingredients";
	private static final String ALCOHOL_CONTENT = "6.0%";
	private static final String CATEGORY = "My category";
	private static final Float PRICE = 10.0f;
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private BeerService beerService;
	
	@MockBean
	private BeerRepository beerRepository;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Test
	public void shouldSendErrorMessageIfIdIsInvalid() throws Exception {
		BDDMockito.given(this.beerService.findById(Mockito.anyLong()))
			.willThrow(new ResourceNotFoundException("No resource found for id: 0"));
		
		mvc.perform(MockMvcRequestBuilders.get(BEER_API_URL + "0").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.message").value("No resource found for id: 0"));
	}
	
	@Test
	public void shouldReturnBeerForValidId() throws Exception {
		BDDMockito.given(this.beerService.findById(Mockito.anyLong())).willReturn(this.getBeerData());
		
		mvc.perform(MockMvcRequestBuilders.get(BEER_API_URL + ID).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(ID))
			.andExpect(jsonPath("$.name").value(NAME))
			.andExpect(jsonPath("$.ingredients").value(INGREDIENTS))
			.andExpect(jsonPath("$.alcoholContent").value(ALCOHOL_CONTENT))
			.andExpect(jsonPath("$.price").value(PRICE))
			.andExpect(jsonPath("$.category").value(CATEGORY));
	}
	
	@Test
	public void shouldCreateBeer() throws Exception {
		BDDMockito.given(this.beerService.create(Mockito.any(BeerDTO.class))).willReturn(this.getBeerData());
		
		String response = this.mapper.writeValueAsString(this.getBeerData());
		
		mvc.perform(MockMvcRequestBuilders
				.post(BEER_API_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(response))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(ID))
			.andExpect(jsonPath("$.name").value(NAME))
			.andExpect(jsonPath("$.ingredients").value(INGREDIENTS))
			.andExpect(jsonPath("$.alcoholContent").value(ALCOHOL_CONTENT))
			.andExpect(jsonPath("$.price").value(PRICE))
			.andExpect(jsonPath("$.category").value(CATEGORY));
	}
	
	@Test
	public void shouldUpdateBeer() throws Exception {
		BDDMockito.given(this.beerService.update(Mockito.any(BeerDTO.class))).willReturn(this.getBeerData());
		
		String response = this.mapper.writeValueAsString(this.getBeerData());
		
		mvc.perform(MockMvcRequestBuilders
				.put(BEER_API_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(response))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value(ID))
			.andExpect(jsonPath("$.name").value(NAME))
			.andExpect(jsonPath("$.ingredients").value(INGREDIENTS))
			.andExpect(jsonPath("$.alcoholContent").value(ALCOHOL_CONTENT))
			.andExpect(jsonPath("$.price").value(PRICE))
			.andExpect(jsonPath("$.category").value(CATEGORY));
	}
		
	private BeerDTO getBeerData() {
		BeerDTO objDTO = new BeerDTO();
		objDTO.setId(ID);
		objDTO.setAlcoholContent(ALCOHOL_CONTENT);
		objDTO.setName(NAME);
		objDTO.setIngredients(INGREDIENTS);
		objDTO.setPrice(PRICE);
		objDTO.setCategory(CATEGORY);
		
		return objDTO;
	}
}
