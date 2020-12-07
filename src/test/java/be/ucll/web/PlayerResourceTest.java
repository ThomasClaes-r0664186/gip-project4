package be.ucll.web;

import be.ucll.AbstractIntegrationTest;

import be.ucll.dto.PlayerDTO;
import be.ucll.models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PlayerResourceTest extends AbstractIntegrationTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	void createPlayerOk() throws Exception {
		PlayerDTO playerDTO = new PlayerDTO("7Stijn7", "Stijn", "Verbieren");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/player")
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andReturn();
		Player createdPlayer = fromMvcResult(mvcResult, Player.class);

		assertEquals("6eoeDFzmaqHUyG8JfAHRJ7tR3FVoOpaUcVY3_Dh8KeK8Lks", createdPlayer.getSummonerID());
		assertEquals("YHVhodh86GLW8W2omXryMzlcWq9uz7Pw3ChmF_C6LY9KqdI", createdPlayer.getAccountId());
		assertEquals("Om0LPHADz1kQKM5rGA41I8eFklXi3P0xE82Ag0MyKb6mzSMFxdkxSx5CL_52nx2YMyJqfW8X_X6aEw", createdPlayer.getPuuID());
		assertEquals(playerDTO.getLeagueName(), createdPlayer.getLeagueName());
		assertEquals(playerDTO.getFirstName(), createdPlayer.getFirstName());
		assertEquals(playerDTO.getLastName(), createdPlayer.getLastName());

	}
}
