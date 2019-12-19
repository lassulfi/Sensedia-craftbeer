package com.beerhouse.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.beerhouse.model.Beer;

@RunWith(SpringRunner.class)
@SpringBootTest()
@ActiveProfiles("dev")
public class BeerRepositoryTest {
	
	@Autowired
	private BeerRepository beerRepository;
	
	@Before
	public void setup() throws Exception {
		Beer beer = new Beer();
		beer.setName("Bavaria");
		beer.setCategory("Nacionais");
		beer.setIngredients("Puro malte, água, cevada");
		beer.setPrice(5.00f);
		beer.setAlcoholContent(6.0f);
	
		this.beerRepository.save(beer);
	}
	
	@After
	public final void tearDown() throws Exception {
		this.beerRepository.deleteAll();
	}
	
	@Test
	public void shouldFindBeerById() {
		Beer beer = this.beerRepository.findById(1L);
		
		assertThat(beer.getName(), is("Bavaria"));
	}
	
	@Test
	public void shouldGetListOfBeers() {
		List<Beer> beers = this.beerRepository.findAll();
		
		assertThat(beers.size(), is(1));
	}
}
