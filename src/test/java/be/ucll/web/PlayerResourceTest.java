package be.ucll.web;

import be.ucll.AbstractIntegrationTest;
import be.ucll.dao.PlayerRepository;
import be.ucll.dto.PlayerDTO;
import be.ucll.exceptions.*;
import be.ucll.models.Player;
import be.ucll.models.Role;
import org.junit.After;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

public class PlayerResourceTest extends AbstractIntegrationTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@BeforeEach
	void setUp() {

		passwordEncoder = new BCryptPasswordEncoder();

		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();

	}


	@Test
	void createPlayerOk() throws Exception {
		final String PASSWORD = "test";

		Player player = new Player.PlayerBuilder()
				.firstName("Stijn")
				.lastName("Verbieren")
				.leagueName("7Stijn7")
				.role(Role.MANAGER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		PlayerDTO playerDTO = new PlayerDTO("zazoeke000", "Stijn", "Verbieren");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/player")
				.with(httpBasic(player.getLeagueName(), PASSWORD))
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").exists())
				.andReturn();
		Player createdPlayer = fromMvcResult(mvcResult, Player.class);

		assertEquals("jXXk8hHq10moWv403zCWm2vyQM1Kp2pmAbJmLpZEf3X28sU", createdPlayer.getSummonerID());
		assertEquals("1NJ_revWDvssBbN4-ye3rFFi2KFFLRPV8tj6Op5EWHkpUng", createdPlayer.getAccountId());
		assertEquals("9GXqtZ6G-cNMLXFXYXuhtTIsBi-EvBuJ0CP4xkUmam1RRZJNR-GVB1InR7Wit9BEWKynlrtSCpebyA", createdPlayer.getPuuID());
		assertEquals(playerDTO.getLeagueName(), createdPlayer.getLeagueName());
		assertEquals(playerDTO.getFirstName(), createdPlayer.getFirstName());
		assertEquals(playerDTO.getLastName(), createdPlayer.getLastName());
	}

	@Test
	void createPlayerNotAuthorization() throws Exception {
		final String PASSWORD = "test";

		Player player = new Player.PlayerBuilder()
				.firstName("Stijn")
				.lastName("Verbieren")
				.leagueName("7Stijn7")
				.role(Role.MANAGER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		PlayerDTO playerDTO = new PlayerDTO("zazoeke000", "Stijn", "Verbieren");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/player")
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andReturn();
	}

	@Test
	void createPlayeBadAuthorizationBadPassword() throws Exception {
		final String PASSWORD = "test";
		final String BAD_PASSWORD = "lala";

		Player player = new Player.PlayerBuilder()
				.firstName("Stijn")
				.lastName("Verbieren")
				.leagueName("7Stijn7")
				.role(Role.MANAGER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		PlayerDTO playerDTO = new PlayerDTO("zazoeke000", "Stijn", "Verbieren");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/player")
				.with(httpBasic(player.getLeagueName(), BAD_PASSWORD))
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andReturn();
	}

	@Test
	void createPlayeBadAuthorizationBadUsername() throws Exception {
		final String PASSWORD = "test";
		final String BAD_USERNAME = "lala";

		Player player = new Player.PlayerBuilder()
				.firstName("Stijn")
				.lastName("Verbieren")
				.leagueName("7Stijn7")
				.role(Role.MANAGER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		PlayerDTO playerDTO = new PlayerDTO("zazoeke000", "Stijn", "Verbieren");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/player")
				.with(httpBasic(BAD_USERNAME, PASSWORD))
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andReturn();
	}

	@Test
	void createPlayeBadAuthorizationBadRole() throws Exception {
		final String PASSWORD = "test";

		Player player = new Player.PlayerBuilder()
				.firstName("Stijn")
				.lastName("Verbieren")
				.leagueName("7Stijn7")
				.role(Role.PLAYER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		PlayerDTO playerDTO = new PlayerDTO("zazoeke000", "Stijn", "Verbieren");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/player")
				.with(httpBasic(player.getLeagueName(), PASSWORD))
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();
	}

	@Test
	void createPlayerLeagueNameNULL() throws Exception {
		final String PASSWORD = "test";

		Player player = new Player.PlayerBuilder()
				.firstName("Stijn")
				.lastName("Verbieren")
				.leagueName("7Stijn7")
				.role(Role.MANAGER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		PlayerDTO playerDTO = new PlayerDTO(null, "Stijn", "Verbieren");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/player")
				.with(httpBasic(player.getLeagueName(), PASSWORD))
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("This parameter may not be empty", responsMessage );
	}

	@Test
	void createPlayerLeagueNameEmpty() throws Exception {
		final String PASSWORD = "test";

		Player player = new Player.PlayerBuilder()
				.firstName("Stijn")
				.lastName("Verbieren")
				.leagueName("7Stijn7")
				.role(Role.MANAGER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		PlayerDTO playerDTO = new PlayerDTO("", "Stijn", "Verbieren");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/player")
				.with(httpBasic(player.getLeagueName(), PASSWORD))
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("This parameter may not be empty", responsMessage );
	}

	@Test
	void createPlayerLeagueNameNotExists() throws Exception {
		final String PASSWORD = "test";

		Player player = new Player.PlayerBuilder()
				.firstName("Stijn")
				.lastName("Verbieren")
				.leagueName("7Stijn7")
				.role(Role.MANAGER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		PlayerDTO playerDTO = new PlayerDTO("*", "Stijn", "Verbieren");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/player")
				.with(httpBasic(player.getLeagueName(), PASSWORD))
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("404 Not Found: [{\"status\":{\"message\":\"Data not found - summoner not found\",\"status_code\":404}}]", responsMessage );
	}

	@Test
	void createPlayerLeagueNameAlreadyExists() throws Exception {
		final String PASSWORD = "test";

		Player playerWannes = new Player.PlayerBuilder()
				.firstName("WannesV")
				.lastName("Verschraegen")
				.leagueName("WannesV")
				.role(Role.PLAYER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		playerRepository.save(playerWannes);

		Player player = new Player.PlayerBuilder()
				.firstName("Stijn")
				.lastName("Verbieren")
				.leagueName("7Stijn7")
				.role(Role.MANAGER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		PlayerDTO playerDTO = new PlayerDTO("WannesV", "Wannes", "Verschraegen");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/player")
				.with(httpBasic(player.getLeagueName(), PASSWORD))
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isConflict())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals(playerDTO.getLeagueName() + " already exists!", responsMessage );
	}

	@Test
	void createPlayerFirstNameNULL() throws Exception {
		final String PASSWORD = "test";

		Player player = new Player.PlayerBuilder()
				.firstName("Stijn")
				.lastName("Verbieren")
				.leagueName("7Stijn7")
				.role(Role.MANAGER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		PlayerDTO playerDTO = new PlayerDTO("zazoeke000", null, "Verbieren");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/player")
				.with(httpBasic(player.getLeagueName(), PASSWORD))
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("This parameter may not be empty", responsMessage );
	}

	@Test
	void createPlayerFirstNameEmpty() throws Exception {
		final String PASSWORD = "test";

		Player player = new Player.PlayerBuilder()
				.firstName("Stijn")
				.lastName("Verbieren")
				.leagueName("7Stijn7")
				.role(Role.MANAGER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		PlayerDTO playerDTO = new PlayerDTO("zazoeke000", "", "Verbieren");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/player")
				.with(httpBasic(player.getLeagueName(), PASSWORD))
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("This parameter may not be empty", responsMessage );
	}

	@Test
	void createPlayerLastNameNULL() throws Exception {
		final String PASSWORD = "test";

		Player player = new Player.PlayerBuilder()
				.firstName("Stijn")
				.lastName("Verbieren")
				.leagueName("7Stijn7")
				.role(Role.MANAGER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		PlayerDTO playerDTO = new PlayerDTO("zazoeke000", "Stijn", null);

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/player")
				.with(httpBasic(player.getLeagueName(), PASSWORD))
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("This parameter may not be empty", responsMessage );
	}

	@Test
	void createPlayerLastNameEmpty() throws Exception {
		final String PASSWORD = "test";

		Player player = new Player.PlayerBuilder()
				.firstName("Stijn")
				.lastName("Verbieren")
				.leagueName("7Stijn7")
				.role(Role.MANAGER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		PlayerDTO playerDTO = new PlayerDTO("zazoeke000", "Stijn", "");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/player")
				.with(httpBasic(player.getLeagueName(), PASSWORD))
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("This parameter may not be empty", responsMessage );
	}



	@Test
	void updatePlayerOk() throws Exception {
		final String PASSWORD = "test";

		Player player = new Player.PlayerBuilder()
				.firstName("Jarno")
				.lastName("De Smet")
				.leagueName("Ardes")
				.role(Role.PLAYER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		final String ID = player.getId().toString();
		PlayerDTO playerDTO = new PlayerDTO("Ardes", "Arno", "De Smet");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/player/" + ID)
				.with(httpBasic(player.getLeagueName(), PASSWORD))
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		Player playerArdes = fromMvcResult(mvcResult, Player.class);

		assertEquals(playerDTO.getLeagueName(), playerArdes.getLeagueName());
		assertEquals(playerDTO.getFirstName(), playerArdes.getFirstName());
		assertEquals(playerDTO.getLastName(), playerArdes.getLastName());

	}

	@Test
	void updatePlayerLeagueNameNULL() throws Exception {
		final String PASSWORD = "test";

		Player player = new Player.PlayerBuilder()
				.firstName("Jarno")
				.lastName("De Smet")
				.leagueName("Ardes")
				.role(Role.PLAYER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		final String ID = player.getId().toString();
		PlayerDTO playerDTO = new PlayerDTO(null, "Arno", "De Smet");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/player/" + ID)
				.with(httpBasic(player.getLeagueName(), PASSWORD))
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("This parameter may not be empty", responsMessage );

	}

	@Test
	void updatePlayerLeagueNameEmpty() throws Exception {
		final String PASSWORD = "test";

		Player player = new Player.PlayerBuilder()
				.firstName("Stijn")
				.lastName("Verbieren")
				.leagueName("7Stijn7")
				.role(Role.MANAGER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		final String ID = player.getId().toString();
		PlayerDTO playerDTO = new PlayerDTO("", "Arno", "De Smet");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/player/" + ID)
				.with(httpBasic(player.getLeagueName(), PASSWORD))
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("This parameter may not be empty", responsMessage );

	}

	@Test
	void updatePlayerLeagueNameNotExists() throws Exception {
		final String PASSWORD = "test";

		Player player = new Player.PlayerBuilder()
				.firstName("Jarno")
				.lastName("De Smet")
				.leagueName("Ardes")
				.role(Role.MANAGER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		final String ID = player.getId().toString();
		PlayerDTO playerDTO = new PlayerDTO("*", "Arno", "De Smet");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/player/" + ID)
				.with(httpBasic(player.getLeagueName(), PASSWORD))
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("404 Not Found: [{\"status\":{\"message\":\"Data not found - summoner not found\",\"status_code\":404}}]", responsMessage );

	}

	@Test
	void updatePlayerLeagueNameAlreadyExists() throws Exception {
		final String PASSWORD = "test";

		Player playerStijn = new Player.PlayerBuilder()
				.firstName("Stijn")
				.lastName("Verbieren")
				.leagueName("7Stijn7")
				.role(Role.PLAYER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		playerRepository.save(playerStijn);

		Player playerWannes = new Player.PlayerBuilder()
				.firstName("Wannes")
				.lastName("Verschraegen")
				.leagueName("WannesV")
				.role(Role.PLAYER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		playerWannes = playerRepository.save(playerWannes);

		final String ID = playerWannes.getId().toString();
		PlayerDTO playerDTO = new PlayerDTO("7Stijn7", "Arno", "De Smet");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/player/" + ID)
				.with(httpBasic(playerWannes.getLeagueName(), PASSWORD))
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals(playerDTO.getLeagueName() + " already exists!", responsMessage );

	}

	@Test
	void updatePlayerFirstNameNULL() throws Exception {
		final String PASSWORD = "test";

		Player player = new Player.PlayerBuilder()
				.firstName("Stijn")
				.lastName("Verbieren")
				.leagueName("Ardes")
				.role(Role.MANAGER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		final String ID = player.getId().toString();
		PlayerDTO playerDTO = new PlayerDTO("Ardes", null, "De Smet");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/player/" + ID)
				.with(httpBasic(player.getLeagueName(), PASSWORD))
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("This parameter may not be empty", responsMessage );

	}

	@Test
	void updatePlayerFirstNameEmpty() throws Exception {
		final String PASSWORD = "test";

		Player player = new Player.PlayerBuilder()
				.firstName("De Smet")
				.lastName("Jarno")
				.leagueName("Ardes")
				.role(Role.MANAGER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		final String ID = player.getId().toString();
		PlayerDTO playerDTO = new PlayerDTO("Ardes", "", "De Smet");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/player/" + ID)
				.with(httpBasic(player.getLeagueName(), PASSWORD))
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("This parameter may not be empty", responsMessage );

	}

	@Test
	void updatePlayerLastNameNULL() throws Exception {
		final String PASSWORD = "test";

		Player player = new Player.PlayerBuilder()
				.firstName("De Smet")
				.lastName("Jarno")
				.leagueName("Ardes")
				.role(Role.MANAGER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		final String ID = player.getId().toString();
		PlayerDTO playerDTO = new PlayerDTO("Ardes", "Arno", null);

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/player/" + ID)
				.with(httpBasic(player.getLeagueName(), PASSWORD))
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("This parameter may not be empty", responsMessage );

	}

	@Test
	void updatePlayerLastNameEmpty() throws Exception {
		final String PASSWORD = "test";

		Player player = new Player.PlayerBuilder()
				.firstName("Ardes")
				.lastName("Jarno")
				.leagueName("De Smet")
				.role(Role.MANAGER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		final String ID = player.getId().toString();
		PlayerDTO playerDTO = new PlayerDTO("Ardes", "Arno", "");

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/player/" + ID)
				.with(httpBasic(player.getLeagueName(), PASSWORD))
				.content(toJson(playerDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("This parameter may not be empty", responsMessage );

	}

	@Test
	void getPlayerOk() throws Exception {
		final String PASSWORD = "test";

		Player player = new Player.PlayerBuilder()
				.firstName("Ava")
				.lastName("Ianche")
				.leagueName("AvaIanche")
				.role(Role.PLAYER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		final String ID = player.getId().toString();
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/player/" + ID)
				.with(httpBasic(player.getLeagueName(), PASSWORD)))
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
		final String PASSWORD = "test";

		Player player = new Player.PlayerBuilder()
				.firstName("Stijn")
				.lastName("Verbieren")
				.leagueName("7Stijn7")
				.role(Role.PLAYER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/player/" + ID)
				.with(httpBasic(player.getLeagueName(), PASSWORD)))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals(ID + " is not valid!", responsMessage );
	}

	@Test
	void getPlayerIdIs0() throws Exception {
		final String ID = "0";
		final String PASSWORD = "test";

		Player player = new Player.PlayerBuilder()
				.firstName("Stijn")
				.lastName("Verbieren")
				.leagueName("7Stijn7")
				.role(Role.PLAYER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/player/" + ID)
				.with(httpBasic(player.getLeagueName(), PASSWORD)))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals(ID + " is not valid!", responsMessage );
	}

	@Test
	void getPlayerIdIsNotFound() throws Exception {
		final String ID = "90000";
		final String PASSWORD = "test";

		Player player = new Player.PlayerBuilder()
				.firstName("Stijn")
				.lastName("Verbieren")
				.leagueName("7Stijn7")
				.role(Role.PLAYER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/player/" + ID)
				.with(httpBasic(player.getLeagueName(), PASSWORD)))
				.andExpect(status().isNotFound())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals(ID + " was not found!", responsMessage );
	}

	@Test
	void deletePlayerOk() throws Exception {
		final String PASSWORD = "test";

		Player player = new Player.PlayerBuilder()
				.firstName("Ava")
				.lastName("Ianche")
				.leagueName("AvaIanche")
				.role(Role.MANAGER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		final String ID = player.getId().toString();
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/player/" + ID)
				.with(httpBasic(player.getLeagueName(), PASSWORD))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("", responsMessage );
	}

	@Test
	void deletePlayerIdIsNegative() throws Exception {
		final String ID = "-1";
		final String PASSWORD = "test";

		Player player = new Player.PlayerBuilder()
				.firstName("Stijn")
				.lastName("Verbieren")
				.leagueName("7Stijn7")
				.role(Role.MANAGER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/player/" + ID)
				.with(httpBasic(player.getLeagueName(), PASSWORD))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals(ID + " is not valid!", responsMessage );
	}

	@Test
	void deletePlayerIdIs0() throws Exception {
		final String ID = "0";
		final String PASSWORD = "test";

		Player player = new Player.PlayerBuilder()
				.firstName("Stijn")
				.lastName("Verbieren")
				.leagueName("7Stijn7")
				.role(Role.MANAGER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/player/" + ID)
				.with(httpBasic(player.getLeagueName(), PASSWORD)))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals(ID + " is not valid!", responsMessage );
	}

	@Test
	void deletePlayerIdIsNotFound() throws Exception {
		final String ID = "90000";
		final String PASSWORD = "test";

		Player player = new Player.PlayerBuilder()
				.firstName("Stijn")
				.lastName("Verbieren")
				.leagueName("7Stijn7")
				.role(Role.MANAGER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/player/" + ID)
				.with(httpBasic(player.getLeagueName(), PASSWORD)))
				.andExpect(status().isNotFound())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals(ID + " was not found!", responsMessage );
	}


	@Test
	void deletePlayerNotAuthorization() throws Exception {
		final String PASSWORD = "test";

		Player player = new Player.PlayerBuilder()
				.firstName("Ava")
				.lastName("Ianche")
				.leagueName("AvaIanche")
				.role(Role.MANAGER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		final String ID = player.getId().toString();
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/player/" + ID)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andReturn();
	}

	@Test
	void deletePlayerBadAuthorizationBadPassword() throws Exception {
		final String PASSWORD = "test";
		final String BAD_PASSWORD = "lala";

		Player player = new Player.PlayerBuilder()
				.firstName("Ava")
				.lastName("Ianche")
				.leagueName("AvaIanche")
				.role(Role.MANAGER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		final String ID = player.getId().toString();
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/player/" + ID)
				.with(httpBasic(player.getLeagueName(), BAD_PASSWORD))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andReturn();

	}

	@Test
	void deletePlayerBadAuthorizationBadUsername() throws Exception {
		final String PASSWORD = "test";
		final String BAD_USERNAME = "lala";

		Player player = new Player.PlayerBuilder()
				.firstName("Ava")
				.lastName("Ianche")
				.leagueName("AvaIanche")
				.role(Role.MANAGER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		final String ID = player.getId().toString();
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/player/" + ID)
				.with(httpBasic(BAD_USERNAME, PASSWORD))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized())
				.andReturn();

	}

	@Test
	void deletePlayerBadAuthorizationBadRole() throws Exception {
		final String PASSWORD = "test";

		Player player = new Player.PlayerBuilder()
				.firstName("Ava")
				.lastName("Ianche")
				.leagueName("AvaIanche")
				.role(Role.PLAYER)
				.password(passwordEncoder.encode(PASSWORD))
				.build();
		player = playerRepository.save(player);

		final String ID = player.getId().toString();
		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/player/" + ID)
				.with(httpBasic(player.getLeagueName(), PASSWORD))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden())
				.andReturn();

		String responsMessage = mvcResult.getResponse().getContentAsString();
		assertEquals("You are unauthorized to do this", responsMessage );
	}

}
