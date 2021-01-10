package be.ucll.service.models;

import be.ucll.exceptions.ParameterInvalidException;
import be.ucll.service.models.match.Participant;
import be.ucll.service.models.match.ParticipantIdentity;
import be.ucll.service.models.match.Team;

import java.util.List;

public class Match {

    /**
     * Dynamisch gebruik van de Match:
     * -------------------------------
     * 1. Kies de properties van de lijst
     * 2. Zet de gekozen properties in de Match constructor parameters (of maak er een nieuwe aan)
     * 3. Zet de setters van de gekozen properties in de contructor body
     *
     */

    /**TE KIEZEN PROPERTIES**/
    //properties die je kan opvragen (deze kan in de constructor)
    private Long seasonId;
    private Long queueId;
    private Long gameId;
    private List<ParticipantIdentity> participantIdentities;
    private String gameVersion;
    private String platformId;
    private String gameMode;
    private Long mapId;
    private String gameType;
    private List<Team> teams;
    private List<Participant> participants;
    private Long gameDuration;
    private Long gameCreation;
    /**EINDE TE KIEZEN PROPERTIES**/

    //Deze properties worden automatisch ingevuld
    private String seasonName;
    private String mapDescription;
    private String mapNotes;
    private String mapName;
    private String gameModeDescription;
    private String gameTypeDescription;
    /**
     * @Ign
     * @param seasonId Season ids are used in match history to indicate which season a match was played. Valid types: http://static.developer.riotgames.com/docs/lol/seasons.json
     * @param queueId Used to indicate which kind of match was played. valid types: http://static.developer.riotgames.com/docs/lol/queues.json
     * @param gameId
     * @param participantIdentities Participant identity information. Participant identity information is purposefully excluded for custom games.
     * @param gameVersion The major.minor version typically indicates the patch the match was played on.
     * @param platformId Platform where the match was played.
     * @param gameMode Valid types: http://static.developer.riotgames.com/docs/lol/gameModes.json
     * @param mapId Map ids are used in match history to indicate which map a match was played. Valid types: http://static.developer.riotgames.com/docs/lol/maps.json
     * @param gameType Type of match game valid types: http://static.developer.riotgames.com/docs/lol/gameTypes.json
     * @param teams Team information.
     * @param participants Participant information.
     * @param gameDuration Match duration in seconds.
     * @param gameCreation Designates the timestamp when champion select ended and the loading screen appeared, NOT when the game timer was at 0:00.
     */
    public Match(Long gameId  /*Vul hier je properties dat je nodig hebt*/) {
        setGameId(gameId);

        /*Zet hier de setters voor de propeties die je hierboven hebt gezet.*/
    }

    /*
     * //VOORBEELD CONSTRUCTOR
     * public Match()(Long seasonId, Long queueId, Long gameId){
     *      setSeasonId(seasonId);
     *      setQueueId(queueId);
     *      setGameId(gameId);
     * }
     */

    public Match(){
        /**Lege contstructor voor error van JSON=>DTO te vermijden**/
    }

    //Getters en setters

    public Long getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(Long seasonId) throws ParameterInvalidException {
        switch (seasonId.toString()){
            case "0":
                this.seasonId = seasonId;
                this.seasonName = "PRESEASON 3";
                break;
            case "1":
                this.seasonId = seasonId;
                this.seasonName = "SEASON 3";
                break;
            case "2":
                this.seasonId = seasonId;
                this.seasonName = "PRESEASON 2014";
                break;
            case "3":
                this.seasonId = seasonId;
                this.seasonName = "SEASON 2014";
                break;
            case "4":
                this.seasonId = seasonId;
                this.seasonName = "PRESEASON 2015";
                break;
            case "5":
                this.seasonId = seasonId;
                this.seasonName = "SEASON 2015";
                break;
            case "6":
                this.seasonId = seasonId;
                this.seasonName = "PRESEASON 2016";
                break;
            case "7":
                this.seasonId = seasonId;
                this.seasonName = "SEASON 2016";
                break;
            case "8":
                this.seasonId = seasonId;
                this.seasonName = "PRESEASON 2017";
                break;
            case "9":
                this.seasonId = seasonId;
                this.seasonName = "SEASON 2017";
                break;
            case "10":
                this.seasonId = seasonId;
                this.seasonName = "PRESEASON 2018";
                break;
            case "11":
                this.seasonId = seasonId;
                this.seasonName = "SEASON 2018";
                break;
            case "12":
                this.seasonId = seasonId;
                this.seasonName = "PRESEASON 2019";
                break;
            case "13":
                this.seasonId = seasonId;
                this.seasonName = "SEASON 2019";
                break;
            default:
                throw new ParameterInvalidException(seasonId.toString());
        }
    }

    public Long getQueueId() {
        return queueId;
    }

    public void setQueueId(Long queueId) throws ParameterInvalidException {
        switch(queueId.toString()){
            case "0":
                this.mapName = "Custom games";
                this.queueId = queueId;
                break;
            case "2":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "5v5 Blind Pick games";
                this.mapNotes = "Deprecated in patch 7.19 in favor of queueId 430";break;
            case "4":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "5v5 Ranked Solo games";
                this.mapNotes = "Deprecated in favor of queueId 420";
                this.queueId = queueId;
                break;
            case "6":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "5v5 Ranked Premade games";
                this.mapNotes = "Game mode deprecated";
                this.queueId = queueId;
                break;
            case "7":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "Co-op vs AI games";
                this.mapNotes = "Deprecated in favor of queueId 32 and 33";
                this.queueId = queueId;
                break;
            case "8":
                this.mapName = "Twisted Treeline";
                this.mapDescription = "3v3 Normal games";
                this.mapNotes = "Deprecated in patch 7.19 in favor of queueId 460";
                this.queueId = queueId;
                break;
            case "9":
                this.mapName = "Twisted Treeline";
                this.mapDescription = "3v3 Ranked Flex games";
                this.mapNotes = "Deprecated in patch 7.19 in favor of queueId 470";
                this.queueId = queueId;
                break;
            case "14":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "5v5 Draft Pick games";
                this.mapNotes = "Deprecated in favor of queueId 400";
                this.queueId = queueId;
                break;
            case "16":
                this.mapName = "Crystal Scar";
                this.mapDescription = "5v5 Dominion Blind Pick games";
                this.mapNotes = "Game mode deprecated";
                this.queueId = queueId;
                break;
            case "17":
                this.mapName = "Crystal Scar";
                this.mapDescription = "5v5 Dominion Draft Pick games";
                this.mapNotes = "Game mode deprecated";
                this.queueId = queueId;
                break;
            case "25":
                this.mapName = "Crystal Scar";
                this.mapDescription = "Dominion Co-op vs AI games";
                this.mapNotes = "Game mode deprecated";
                this.queueId = queueId;
                break;
            case "31":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "Co-op vs AI Intro Bot games";
                this.mapNotes = "Deprecated in patch 7.19 in favor of queueId 830";
                this.queueId = queueId;
                break;
            case "32":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "Co-op vs AI Beginner Bot games";
                this.mapNotes = "Deprecated in patch 7.19 in favor of queueId 840";
                this.queueId = queueId;
                break;
            case "33":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "Co-op vs AI Intermediate Bot games";
                this.mapNotes = "Deprecated in patch 7.19 in favor of queueId 850";
                this.queueId = queueId;
                break;
            case "41":
                this.mapName = "Twisted Treeline";
                this.mapDescription = "3v3 Ranked Team games";
                this.mapNotes = "Game mode deprecated";
                this.queueId = queueId;
                break;
            case "42":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "5v5 Ranked Team games";
                this.mapNotes = "Game mode deprecated";
                this.queueId = queueId;
                break;
            case "52":
                this.mapName = "Twisted Treeline";
                this.mapDescription = "Co-op vs AI games";
                this.mapNotes = "Deprecated in patch 7.19 in favor of queueId 800";
                this.queueId = queueId;
                break;
            case "61":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "5v5 Team Builder games";
                this.mapNotes = "Game mode deprecated";
                this.queueId = queueId;
                break;
            case "65":
                this.mapName = "Howling Abyss";
                this.mapDescription = "5v5 ARAM games";
                this.mapNotes = "Deprecated in patch 7.19 in favor of queueId 450";
                this.queueId = queueId;
                break;
            case "67":
                this.mapName = "Howling Abyss";
                this.mapDescription = "ARAM Co-op vs AI games";
                this.mapNotes = "Game mode deprecated";
                this.queueId = queueId;
                break;
            case "70":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "One for All games";
                this.mapNotes = "Deprecated in patch 8.6 in favor of queueId 1020";
                this.queueId = queueId;
                break;
            case "72":
                this.mapName = "Howling Abyss";
                this.mapDescription = "1v1 Snowdown Showdown games";
                this.queueId = queueId;
                break;
            case "73":
                this.mapName = "Howling Abyss";
                this.mapDescription = "2v2 Snowdown Showdown games";
                this.queueId = queueId;
                break;
            case "75":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "6v6 Hexakill games";
                this.queueId = queueId;
                break;
            case "76":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "Ultra Rapid Fire games";
                this.queueId = queueId;
                break;
            case "78":
                this.mapName = "Howling Abyss";
                this.mapDescription = "One For All: Mirror Mode games";
                this.queueId = queueId;
                break;
            case "83":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "Co-op vs AI Ultra Rapid Fire games";
                this.queueId = queueId;
                break;
            case "91":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "Doom Bots Rank 1 games";
                this.mapNotes = "Deprecated in patch 7.19 in favor of queueId 950";
                this.queueId = queueId;
                break;
            case "92":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "Doom Bots Rank 2 games";
                this.mapNotes = "Deprecated in patch 7.19 in favor of queueId 950";
                this.queueId = queueId;
                break;
            case "93":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "Doom Bots Rank 5 games";
                this.mapNotes = "Deprecated in patch 7.19 in favor of queueId 950";
                this.queueId = queueId;
                break;
            case "96":
                this.mapName = "Crystal Scar";
                this.mapDescription = "Ascension games";
                this.mapNotes = "Deprecated in patch 7.19 in favor of queueId 910";
                this.queueId = queueId;
                break;
            case "98":
                this.mapName = "Twisted Treeline";
                this.mapDescription = "6v6 Hexakill games";
                this.queueId = queueId;
                break;
            case "100":
                this.mapName = "Butcher's Bridge";
                this.mapDescription = "5v5 ARAM games";
                this.queueId = queueId;
                break;
            case "300":
                this.mapName = "Howling Abyss";
                this.mapDescription = "Legend of the Poro King games";
                this.mapNotes = "Deprecated in patch 7.19 in favor of queueId 920";
                this.queueId = queueId;
                break;
            case "310":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "Nemesis games";
                this.queueId = queueId;
                break;
            case "313":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "Black Market Brawlers games";
                this.queueId = queueId;
                break;
            case "315":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "Nexus Siege games";
                this.mapNotes = "Deprecated in patch 7.19 in favor of queueId 940";
                this.queueId = queueId;
                break;
            case "317":
                this.mapName = "Crystal Scar";
                this.mapDescription = "Definitely Not Dominion games";
                this.queueId = queueId;
                break;
            case "318":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "ARURF games";
                this.mapNotes = "Deprecated in patch 7.19 in favor of queueId 900";
                this.queueId = queueId;
                break;
            case "325":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "All Random games";
                this.queueId = queueId;
                break;
            case "400":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "5v5 Draft Pick games";
                this.queueId = queueId;
                break;
            case "410":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "5v5 Ranked Dynamic games";
                this.mapNotes = "Game mode deprecated in patch 6.22";
                this.queueId = queueId;
                break;
            case "420":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "5v5 Ranked Solo games";
                this.queueId = queueId;
                break;
            case "430":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "5v5 Blind Pick games";
                this.queueId = queueId;
                break;
            case "440":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "5v5 Ranked Flex games";
                this.queueId = queueId;
                break;
            case "450":
                this.mapName = "Howling Abyss";
                this.mapDescription = "5v5 ARAM games";
                this.queueId = queueId;
                break;
            case "460":
                this.mapName = "Twisted Treeline";
                this.mapDescription = "3v3 Blind Pick games";
                this.mapNotes = "Deprecated in patch 9.23";
                this.queueId = queueId;
                break;
            case "470":
                this.mapName = "Twisted Treeline";
                this.mapDescription = "3v3 Ranked Flex games";
                this.mapNotes = "Deprecated in patch 9.23";
                this.queueId = queueId;
                break;
            case "600":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "Blood Hunt Assassin games";
                this.queueId = queueId;
                break;
            case "610":
                this.mapName = "Cosmic Ruins";
                this.mapDescription = "Dark Star: Singularity games";
                this.queueId = queueId;
                break;
            case "700":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "Clash games";
                this.mapNotes = null;
                this.queueId = queueId;
                break;
            case "800":
                this.mapName = "Twisted Treeline";
                this.mapDescription = "Co-op vs. AI Intermediate Bot games";
                this.mapNotes = "Deprecated in patch 9.23";
                this.queueId = queueId;
                break;
            case "810":
                this.mapName = "Twisted Treeline";
                this.mapDescription = "Co-op vs. AI Intro Bot games";
                this.mapNotes = "Deprecated in patch 9.23";
                this.queueId = queueId;
                break;
            case "820":
                this.mapName = "Twisted Treeline";
                this.mapDescription = "Co-op vs. AI Beginner Bot games";
                this.queueId = queueId;
                break;
            case "830":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "Co-op vs. AI Intro Bot games";
                this.queueId = queueId;
                break;
            case "840":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "Co-op vs. AI Beginner Bot games";
                this.queueId = queueId;
                break;
            case "850":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "Co-op vs. AI Intermediate Bot games";
                this.queueId = queueId;
                break;
            case "900":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "URF games";
                this.queueId = queueId;
                break;
            case "910":
                this.mapName = "Crystal Scar";
                this.mapDescription = "Ascension games";
                this.queueId = queueId;
                break;
            case "920":
                this.mapName = "Howling Abyss";
                this.mapDescription = "Legend of the Poro King games";
                this.queueId = queueId;
                break;
            case "940":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "Nexus Siege games";
                this.queueId = queueId;
                break;
            case "950":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "Doom Bots Voting games";
                this.queueId = queueId;
                break;
            case "960":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "Doom Bots Standard games";
                this.queueId = queueId;
                break;
            case "980":
                this.mapName = "Valoran City Park";
                this.mapDescription = "Star Guardian Invasion: Normal games";
                this.queueId = queueId;
                break;
            case "990":
                this.mapName = "Valoran City Park";
                this.mapDescription = "Star Guardian Invasion: Onslaught games";
                this.queueId = queueId;
                break;
            case "1000":
                this.mapName = "Overcharge";
                this.mapDescription = "PROJECT: Hunters games";
                this.queueId = queueId;
                break;
            case "1010":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "Snow ARURF games";
                this.queueId = queueId;
                break;
            case "1020":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "One for All games";
                this.queueId = queueId;
                break;
            case "1030":
                this.mapName = "Crash Site";
                this.mapDescription = "Odyssey Extraction: Intro games";
                this.queueId = queueId;
                break;
            case "1040":
                this.mapName = "Crash Site";
                this.mapDescription = "Odyssey Extraction: Cadet games";
                this.queueId = queueId;
                break;
            case "1050":
                this.mapName = "Crash Site";
                this.mapDescription = "Odyssey Extraction: Crewmember games";
                this.queueId = queueId;
                break;
            case "1060":
                this.mapName = "Crash Site";
                this.mapDescription = "Odyssey Extraction: Captain games";
                this.queueId = queueId;
                break;
            case "1070":
                this.mapName = "Crash Site";
                this.mapDescription = "Odyssey Extraction: Onslaught games";
                this.queueId = queueId;
                break;
            case "1090":
                this.mapName = "Convergence";
                this.mapDescription = "Teamfight Tactics games";
                this.queueId = queueId;
                break;
            case "1100":
                this.mapName = "Convergence";
                this.mapDescription = "Ranked Teamfight Tactics games";
                this.queueId = queueId;
                break;
            case "1110":
                this.mapName = "Convergence";
                this.mapDescription = "Teamfight Tactics Tutorial games";
                this.queueId = queueId;
                break;
            case "1111":
                this.mapName = "Convergence";
                this.mapDescription = "Teamfight Tactics test games";
                this.queueId = queueId;
                break;
            case "1200":
                this.mapName = "Nexus Blitz";
                this.mapDescription = "Nexus Blitz games";
                this.mapNotes = "Deprecated in patch 9.2";
                this.queueId = queueId;
                break;
            case "1300":
                this.mapName = "Nexus Blitz";
                this.mapDescription = "Nexus Blitz games";
                this.queueId = queueId;
                break;
            case "2000":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "Tutorial 1";
                this.queueId = queueId;
                break;
            case "2010":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "Tutorial 2";
                this.queueId = queueId;
                break;
            case "2020":
                this.mapName = "Summoner's Rift";
                this.mapDescription = "Tutorial 3";
                this.queueId = queueId;
                break;
            default:
                throw new ParameterInvalidException(queueId.toString());
        }
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public List<ParticipantIdentity> getParticipantIdentities() {
        return participantIdentities;
    }

    public void setParticipantIdentities(List<ParticipantIdentity> participantIdentities) {
        this.participantIdentities = participantIdentities;
    }

    public String getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(String gameVersion) {
        this.gameVersion = gameVersion;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) throws ParameterInvalidException {
        switch (gameMode){
            case "CLASSIC":
                this.gameModeDescription =  "Classic Summoner's Rift and Twisted Treeline games";
                this.gameMode = gameMode;
                break;
            case "ODIN":
                this.gameModeDescription =  "Dominion/Crystal Scar games";
                this.gameMode = gameMode;
                break;
            case "ARAM":
                this.gameModeDescription =  "ARAM games";
                this.gameMode = gameMode;
                break;
            case "TUTORIAL":
                this.gameModeDescription =  "Tutorial games";
                this.gameMode = gameMode;
                break;
            case "URF":
                this.gameModeDescription =  "URF games";
                this.gameMode = gameMode;
                break;
            case "DOOMBOTSTEEMO":
                this.gameModeDescription =  "Doom Bot games";
                this.gameMode = gameMode;
                break;
            case "ONEFORALL":
                this.gameModeDescription =  "One for All games";
                this.gameMode = gameMode;
                break;
            case "ASCENSION":
                this.gameModeDescription =  "Ascension games";
                this.gameMode = gameMode;
                break;
            case "FIRSTBLOOD":
                this.gameModeDescription =  "Snowdown Showdown games";
                this.gameMode = gameMode;
                break;
            case "KINGPORO":
                this.gameModeDescription =  "Legend of the Poro King games";
                this.gameMode = gameMode;
                break;
            case "SIEGE":
                this.gameModeDescription =  "Nexus Siege games";
                this.gameMode = gameMode;
                break;
            case "ASSASSINATE":
                this.gameModeDescription =  "Blood Hunt Assassin games";
                this.gameMode = gameMode;
                break;
            case "ARSR":
                this.gameModeDescription =  "All Random Summoner's Rift games";
                this.gameMode = gameMode;
                break;
            case "DARKSTAR":
                this.gameModeDescription =  "Dark Star: Singularity games";
                this.gameMode = gameMode;
                break;
            case "STARGUARDIAN":
                this.gameModeDescription =  "Star Guardian Invasion games";
                this.gameMode = gameMode;
                break;
            case "PROJECT":
                this.gameModeDescription =  "PROJECT: Hunters games";
                this.gameMode = gameMode;
                break;
            case "GAMEMODEX":
            case "NEXUSBLITZ":
                this.gameModeDescription =  "Nexus Blitz games";
                this.gameMode = gameMode;
                break;
            case "ODYSSEY":
                this.gameModeDescription =  "Odyssey: Extraction games";
                this.gameMode = gameMode;
                break;
            default:
                throw new ParameterInvalidException(gameMode);
        }
    }

    public Long getMapId() {
        return mapId;
    }

    public void setMapId(Long mapId) throws ParameterInvalidException {
        switch(mapId.toString()){
            case "1":
                this.mapName = "Summoner's Rift";
                this.mapNotes = "Original Summer variant";
                this.mapId = mapId;
                break;
            case "2":
                this.mapName = "Summoner's Rift";
                this.mapNotes = "Original Autumn variant";
                break;
            case "3":
                this.mapName = "The Proving Grounds";
                this.mapNotes = "Tutorial Map";
                this.mapId = mapId;
                break;
            case "4":
                this.mapName = "Twisted Treeline";
                this.mapNotes = "Original Version";
                this.mapId = mapId;
                break;
            case "8":
                this.mapName = "The Crystal Scar";
                this.mapNotes = "Dominion map";
                this.mapId = mapId;
                break;
            case "10":
                this.mapName = "Twisted Treeline";
                this.mapNotes = "Last TT map";
                this.mapId = mapId;
                break;
            case "11":
                this.mapName = "Summoner's Rift";
                this.mapNotes = "Current Version";
                this.mapId = mapId;
                break;
            case "12":
                this.mapName = "Howling Abyss";
                this.mapNotes = "ARAM map";
                this.mapId = mapId;
                break;
            case "14":
                this.mapName = "Butcher's Bridge";
                this.mapNotes = "Alternate ARAM map";
                this.mapId = mapId;
                break;
            case "16":
                this.mapName = "Cosmic Ruins";
                this.mapNotes = "Dark Star: Singularity map";
                this.mapId = mapId;
                break;
            case "18":
                this.mapName = "Valoran City Park";
                this.mapNotes = "Star Guardian Invasion map";
                this.mapId = mapId;
                break;
            case "19":
                this.mapName = "Substructure 43";
                this.mapNotes = "PROJECT: Hunters map";
                this.mapId = mapId;
                break;
//            Error in league API http://static.developer.riotgames.com/docs/lol/maps.json 2 keer mapId 20
//            case "20":
//                this.mapName = "Crash Site";
//                this.mapNotes = "Odyssey: Extraction map";
//                this.mapId = mapId;
//                break;
            case "20":
                this.mapName = "Convergence";
                this.mapNotes = "Teamfight Tactics map";
                this.mapId = mapId;
                break;
            case "21":
                this.mapName = "Nexus Blitz";
                this.mapNotes = "Nexus Blitz map";
                this.mapId = mapId;
                break;
            default:
                throw new ParameterInvalidException(mapId.toString());
        }
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) throws ParameterInvalidException {
        switch (gameType){
            case "CUSTOM_GAME":
                this.gameTypeDescription = "Custom games";
                this.gameType = gameType;
                break;
            case "TUTORIAL_GAME":
                this.gameTypeDescription = "Tutorial games";
                this.gameType = gameType;
                break;
            case "MATCHED_GAME":
                this.gameTypeDescription = "all other games";
                this.gameType = gameType;
                break;
            default:
                throw new ParameterInvalidException(gameType);
        }
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public Long getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(Long gameDuration) {
        this.gameDuration = gameDuration;
    }

    public Long getGameCreation() {
        return gameCreation;
    }

    public void setGameCreation(Long gameCreation) {
        this.gameCreation = gameCreation;
    }

    public String getSeason() {
        return seasonName;
    }

    public String getMapDescription() {
        return mapDescription;
    }

    public String getMapNotes() {
        return mapNotes;
    }

    public String getMapName() {
        return mapName;
    }

    public String getGameModeDescription() {
        return gameModeDescription;
    }

    public String getGameTypeDescription() {
        return gameTypeDescription;
    }
}
