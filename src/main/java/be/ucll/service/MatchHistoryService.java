package be.ucll.service;

import be.ucll.config.ApplicationConfiguration;

import be.ucll.service.models.Summoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class MatchHistoryService {

    private final RestTemplate RESTTEMPLATE;
    private final String URL_SEARCH_BY_NAME = "https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/";
    private final ApplicationConfiguration applicationConfiguration;

    @Autowired
    public MatchHistoryService(RestTemplateBuilder restTemplatebuilder, ApplicationConfiguration applicationConfiguration) {
        this.RESTTEMPLATE = restTemplatebuilder.build();
        this.applicationConfiguration = applicationConfiguration;
    }


}
