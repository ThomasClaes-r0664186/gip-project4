package be.ucll.web;

import be.ucll.AbstractIntegrationTest;
import be.ucll.dao.PlayerRepository;
import be.ucll.dto.PlayerDTO;
import be.ucll.exceptions.*;
import be.ucll.models.Player;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PlayerResourceTest extends AbstractIntegrationTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Autowired
	private PlayerResource playerResource;

	private Long idPlayerWannesV;
	private Long idPlayerArdes;
	private Long idPlayerAvaIanche;

	@BeforeEach
	void setUp() throws UsernameAlreadyExists, NotFoundException, ParameterInvalidException {

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

		PlayerDTO playerWannesV = new PlayerDTO("WannesV", "Wannes", "Verschraegen");
		PlayerDTO playerArdes = new PlayerDTO("Ardes", "Jarno", "De Smet");
		PlayerDTO playerAvaIanche = new PlayerDTO("AvaIanche", "Ava", "Ianche");

		idPlayerWannesV = Objects.requireNonNull(playerResource.createPlayer(playerWannesV).getBody()).getId();
		idPlayerArdes = Objects.requireNonNull(playerResource.createPlayer(playerArdes).getBody()).getId();
		idPlayerAvaIanche = Objects.requireNonNull(playerResource.createPlayer(playerAvaIanche).getBody()).getId();

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

	@Test
	void createPlayerLeagueNameNULL() throws Exception {
		PlayerDTO playerDTO = new PlayerDTO(null, "Stijn", "Verbieren");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/player")
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("One or more parameters are invalid!", responsMessage );
	}

	@Test
	void createPlayerLeagueNameEmpty() throws Exception {
		PlayerDTO playerDTO = new PlayerDTO("", "Stijn", "Verbieren");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/player")
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("One or more parameters are invalid!", responsMessage );
	}

	@Test
	void createPlayerLeagueNameNotExists() throws Exception {
		PlayerDTO playerDTO = new PlayerDTO("*", "Stijn", "Verbieren");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/player")
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("404 Not Found: [{\"status\":{\"message\":\"Data not found - summoner not found\",\"status_code\":404}}]", responsMessage );
	}

	@Test
	void createPlayerLeagueNameAlreadyExists() throws Exception {
		PlayerDTO playerDTO = new PlayerDTO("WannesV", "Wannes", "Verschraegen");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/player")
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("This user: " + playerDTO.getLeagueName() + " already exists!", responsMessage );
	}

	@Test
	void createPlayerFirstNameNULL() throws Exception {
		PlayerDTO playerDTO = new PlayerDTO("7Stijn7", null, "Verbieren");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/player")
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("One or more parameters are invalid!", responsMessage );
	}

	@Test
	void createPlayerFirstNameEmpty() throws Exception {
		PlayerDTO playerDTO = new PlayerDTO("7Stijn7", "", "Verbieren");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/player")
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("One or more parameters are invalid!", responsMessage );
	}

	@Test
	void createPlayerLastNameNULL() throws Exception {
		PlayerDTO playerDTO = new PlayerDTO("7Stijn7", "Stijn", null);

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/player")
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("One or more parameters are invalid!", responsMessage );
	}

	@Test
	void createPlayerLastNameEmpty() throws Exception {
		PlayerDTO playerDTO = new PlayerDTO("7Stijn7", "Stijn", "");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/player")
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("One or more parameters are invalid!", responsMessage );
	}



	@Test
	void updatePlayerOk() throws Exception {
		final String ID = idPlayerArdes.toString();
		PlayerDTO playerDTO = new PlayerDTO("Ardes", "Arno", "De Smet");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/player/" + ID)
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		Player player = fromMvcResult(mvcResult, Player.class);

		assertEquals(playerDTO.getLeagueName(), player.getLeagueName());
		assertEquals(playerDTO.getFirstName(), player.getFirstName());
		assertEquals(playerDTO.getLastName(), player.getLastName());

	}

	@Test
	void updatePlayerLeagueNameNULL() throws Exception {
		final String ID = idPlayerArdes.toString();
		PlayerDTO playerDTO = new PlayerDTO(null, "Arno", "De Smet");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/player/" + ID)
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("One or more parameters are invalid!", responsMessage );

	}

	@Test
	void updatePlayerLeagueNameEmpty() throws Exception {
		final String ID = idPlayerArdes.toString();
		PlayerDTO playerDTO = new PlayerDTO("", "Arno", "De Smet");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/player/" + ID)
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("One or more parameters are invalid!", responsMessage );

	}

	@Test
	void updatePlayerLeagueNameNotExists() throws Exception {
		final String ID = idPlayerArdes.toString();
		PlayerDTO playerDTO = new PlayerDTO("*", "Arno", "De Smet");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/player/" + ID)
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("404 Not Found: [{\"status\":{\"message\":\"Data not found - summoner not found\",\"status_code\":404}}]", responsMessage );

	}

	@Test
	void updatePlayerLeagueNameAlreadyExists() throws Exception {
		final String ID = idPlayerArdes.toString();
		PlayerDTO playerDTO = new PlayerDTO("WannesV", "Arno", "De Smet");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/player/" + ID)
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("This user: " + playerDTO.getLeagueName() + " already exists!", responsMessage );

	}

	@Test
	void updatePlayerFirstNameNULL() throws Exception {
		final String ID = idPlayerArdes.toString();
		PlayerDTO playerDTO = new PlayerDTO("Ardes", null, "De Smet");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/player/" + ID)
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("One or more parameters are invalid!", responsMessage );

	}

	@Test
	void updatePlayerFirstNameEmpty() throws Exception {
		final String ID = idPlayerArdes.toString();
		PlayerDTO playerDTO = new PlayerDTO("Ardes", "", "De Smet");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/player/" + ID)
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("One or more parameters are invalid!", responsMessage );

	}

	@Test
	void updatePlayerLastNameNULL() throws Exception {
		final String ID = idPlayerArdes.toString();
		PlayerDTO playerDTO = new PlayerDTO("Ardes", "Arno", null);

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/player/" + ID)
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("One or more parameters are invalid!", responsMessage );

	}

	@Test
	void updatePlayerLastNameEmpty() throws Exception {
		final String ID = idPlayerArdes.toString();
		PlayerDTO playerDTO = new PlayerDTO("Ardes", "Arno", "");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/player/" + ID)
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("One or more parameters are invalid!", responsMessage );

	}

	@Test
	void getPlayerOk() throws Exception {
		final String ID = idPlayerAvaIanche.toString();
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/player/" + ID))
				.andExpect(status().isOk())
				.andReturn();
		Player getPlayer = fromMvcResult(mvcResult, Player.class);

		assertEquals("AvaIanche", getPlayer.getLeagueName());
		assertEquals("Ava", getPlayer.getFirstName());
		assertEquals("Ianche", getPlayer.getLastName());
	}

	@Test
	void getPlayerIdIsNegative() throws Exception {
		final String ID = "-1";
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/player/" + ID))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("One or more parameters are invalid!", responsMessage );
	}

	@Test
	void getPlayerIdIs0() throws Exception {
		final String ID = "0";
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/player/" + ID))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("One or more parameters are invalid!", responsMessage );
	}

	@Test
	void deletePlayerOk() throws Exception {
		final String ID = idPlayerAvaIanche.toString();
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/player/" + ID)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("", responsMessage );
	}

	@Test
	void deletePlayerIdIsNegative() throws Exception {
		final String ID = "-1";
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/player/" + ID)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("One or more parameters are invalid!", responsMessage );
	}

	@Test
	void deletePlayerIdIs0() throws Exception {
		final String ID = "0";
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/player/" + ID))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("One or more parameters are invalid!", responsMessage );
	}


}
