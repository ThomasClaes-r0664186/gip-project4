package be.ucll.service;

import be.ucll.constants.Constants;

import be.ucll.service.models.Summoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class SummonerService {

    private final RestTemplate RESTTEMPLATE;
    private final String URL_SEARCH_BY_NAME = "https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/";

    @Autowired
    public SummonerService(RestTemplateBuilder restTemplatebuilder) {
        this.RESTTEMPLATE = restTemplatebuilder.build();
    }

    /**
     * De summoner ophalen bij de league of legends api
     * @param summonerName De gebruikersnaam van het bestaande spelersaccount bij league of legends
     * @return De League of legends speler
     */

    // De summonerService gaat de summoner ophalen bij de league of legends api.
    // Indien gevonden returnt hij een Optional van Summoner terug.
    public Optional<Summoner> getSummoner(String summonerName){
        try {
            // Omdat de url geen 'Verboden tekens' mag bevatten wordt elke parameter geëncodeerd. Zo wordt een spatie bv. %20
            String name = URLEncoder.encode(summonerName, StandardCharsets.UTF_8);
            // Een Get-request wordt uitgevoerd op de league of legends api. De respons wordt gemapt naar de class: Summoner.
            return Optional.ofNullable(RESTTEMPLATE.getForObject(URL_SEARCH_BY_NAME + name + "?api_key=" + Constants.API_KEY, Summoner.class));
        }catch (Exception e){
            System.out.println(e.toString());
            // indien mislukt geven we lege Optional terug.
            return Optional.empty();
        }
    }

}
