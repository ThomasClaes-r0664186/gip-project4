package be.ucll.service;

import be.ucll.config.ApplicationConfiguration;
import be.ucll.dto.MatchHistoryDTO;
import be.ucll.service.models.CurrentGameInfo;
import be.ucll.service.models.Match;
import be.ucll.service.models.Summoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LiveStatsService {
    private final RestTemplate RESTTEMPLATE;
    private final String URL_SEARCH_BY_SUMMONER_ID = "https://euw1.api.riotgames.com/lol/spectator/v4/active-games/by-summoner/";
    private final ApplicationConfiguration applicationConfiguration;

    @Autowired
    public LiveStatsService(RestTemplateBuilder restTemplatebuilder, ApplicationConfiguration applicationConfiguration) {
        this.RESTTEMPLATE = restTemplatebuilder.build();
        this.applicationConfiguration = applicationConfiguration;
    }

    public Optional<CurrentGameInfo> getActiveGames(String summonerId){
        URLEncoder.encode(summonerId, StandardCharsets.UTF_8);
        CurrentGameInfo currentGameInfo = RESTTEMPLATE.getForObject(URL_SEARCH_BY_SUMMONER_ID + summonerId + "?api_key=" + applicationConfiguration.getApiKey(), CurrentGameInfo.class);
        return Optional.ofNullable(currentGameInfo);
    }
}
