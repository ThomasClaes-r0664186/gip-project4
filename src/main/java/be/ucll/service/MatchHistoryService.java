package be.ucll.service;

import be.ucll.config.ApplicationConfiguration;

import be.ucll.dto.MatchHistoryDTO;
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
public class MatchHistoryService {

    private final RestTemplate RESTTEMPLATE;
    private final String URL_SEARCH_BY_MATCH_ID = "https://euw1.api.riotgames.com/lol/match/v4/matches/";
    private final ApplicationConfiguration applicationConfiguration;

    @Autowired
    public MatchHistoryService(RestTemplateBuilder restTemplatebuilder, ApplicationConfiguration applicationConfiguration) {
        this.RESTTEMPLATE = restTemplatebuilder.build();
        this.applicationConfiguration = applicationConfiguration;
    }

    public Optional<Match> getMatch(Long matchId){
        URLEncoder.encode(matchId.toString(), StandardCharsets.UTF_8);
        return Optional.ofNullable(RESTTEMPLATE.getForObject(URL_SEARCH_BY_MATCH_ID + matchId + "?api_key=" + applicationConfiguration.getApiKey(), Match.class));

    }
    public List<Match> getMatches(List<Long> matchId){
        List<Match> matches = new ArrayList<>();
        URLEncoder.encode(matchId.toString(), StandardCharsets.UTF_8);
        for (int i =0; i < 3; i++){
             matches.add((RESTTEMPLATE.getForObject(URL_SEARCH_BY_MATCH_ID + matchId.get(i) + "?api_key=" + applicationConfiguration.getApiKey(), Match.class)));
        }
        return matches;
    }
}
