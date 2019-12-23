package com.beerhouse.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.beerhouse.dto.BeerDTO;
import com.beerhouse.exceptions.ResourceNotFoundException;
import com.beerhouse.mapper.BeerMapper;
import com.beerhouse.model.Beer;
import com.beerhouse.repository.BeerRepository;


@RunWith(SpringRunner.class)
@SpringBootTest()
@ActiveProfiles("dev")
public class BeerServiceTest {
	
	@MockBean
	private BeerRepository beerRepository;
	
	@MockBean
	private BeerMapper beerMapper;
	
	@Autowired
	private BeerService beerService;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup() throws Exception {
		BDDMockito.given(this.beerRepository.findById(Mockito.anyLong())).willReturn(new Beer());
		BDDMockito.given(this.beerRepository.findById(Mockito.eq(0L))).willThrow(new ResourceNotFoundException("No resource found for id: 0"));
		BDDMockito.given(this.beerRepository.save(Mockito.any(Beer.class))).willReturn(new Beer());
		BDDMockito.given(this.beerMapper.mapEntityToDTO(Mockito.any(Beer.class))).willReturn(new BeerDTO());
	}
	
	@Test
	public void mustFindBeerById() {
		BeerDTO objDTO = this.beerService.findById(1L);
		
		assertNotNull(objDTO);
	}
	
	@Test
	public void mustPersistBeer() {
		BeerDTO objDTO = this.beerService.create(new BeerDTO());
	
		assertNotNull(objDTO);
	}
	
	@Test
	public void mustThrowExceptionWhenIfIdIsInvalid() throws ResourceNotFoundException {
		exception.expect(ResourceNotFoundException.class);
		exception.expectMessage("No resource found for id: 0");
		
		BeerDTO objDTO = this.beerService.findById(0L);
		
	}
}
