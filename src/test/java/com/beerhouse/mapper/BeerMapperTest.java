package com.beerhouse.mapper;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.beerhouse.dto.BeerDTO;
import com.beerhouse.model.Beer;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class BeerMapperTest {

	@Autowired
	private BeerMapper mapper;
	
	@Test
	public void shouldMapEntityToDTO() {
		Beer entity = new Beer();
		entity.setName("Bavaria");
		entity.setCategory("Nacionais");
		entity.setIngredients("Puro malte, água, cevada");
		entity.setPrice(5.00f);
		entity.setAlcoholContent(6.0f);
		
		BeerDTO objDTO = mapper.mapEntityToDTO(entity);
		
		Assert.assertThat(objDTO.getName(), CoreMatchers.is("Bavaria"));
		Assert.assertThat(objDTO.getCategory(), CoreMatchers.is("Nacionais"));
		Assert.assertThat(objDTO.getIngredients(), CoreMatchers.is("Puro malte, água, cevada"));
		Assert.assertThat(objDTO.getPrice(), CoreMatchers.is(5.00f));
		Assert.assertThat(objDTO.getAlcoholContent(), CoreMatchers.is("6,0%"));
	}
	
	@Test
	public void shouldMapDTOtoEntity() {
		BeerDTO objDTO = new BeerDTO();
		objDTO.setAlcoholContent("6.0%");
		objDTO.setName("Bavaria");
		objDTO.setCategory("Nacionais");
		objDTO.setIngredients("Puro malte, água, cevada");
		objDTO.setPrice(5.00f);
		
		Beer entity = mapper.mapDTOtoEntity(objDTO);
		
		Assert.assertThat(entity.getName(), CoreMatchers.is("Bavaria"));
		Assert.assertThat(entity.getCategory(), CoreMatchers.is("Nacionais"));
		Assert.assertThat(entity.getIngredients(), CoreMatchers.is("Puro malte, água, cevada"));
		Assert.assertThat(entity.getPrice(), CoreMatchers.is(5.00f));
		Assert.assertThat(entity.getAlcoholContent(), CoreMatchers.is(6.0f));
	}
}
