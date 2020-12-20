package be.ucll.service.models.match;


public class Player {

    public String currentPlatformId=null;
    public String summonerName=null;
    public String matchHistoryUri=null;
    public String platformId=null;
    public String currentAccountId=null;
    public Long profileIcon=null;
    public String summonerId=null;
    public String accountId=null;

    /**
     *
     * @param currentPlatformId Player's current platformId when the match was played.
     * @param summonerName
     * @param matchHistoryUri
     * @param platformId 	Player's original platformId.
     * @param currentAccountId 	Player's current accountId when the match was played.
     * @param profileIcon
     * @param summonerId Player's summonerId (Encrypted)
     * @param accountId Player's original accountId.
     */
    public Player(String currentPlatformId, String summonerName, String matchHistoryUri, String platformId, String currentAccountId, Long profileIcon, String summonerId, String accountId) {
        setCurrentPlatformId(currentPlatformId);
        setSummonerName(summonerName);
        setMatchHistoryUri(matchHistoryUri);
        setPlatformId(platformId);
        setCurrentAccountId(currentAccountId);
        setProfileIcon(profileIcon);
        setSummonerId(summonerId);
        setAccountId(accountId);
    }

    public Player(){
        /**Lege contstructor voor error van JSON=>DTO te vermijden**/
    }

    //Getters en setters

    public String getCurrentPlatformId() {
        return currentPlatformId;
    }

    public void setCurrentPlatformId(String currentPlatformId) {
        this.currentPlatformId = currentPlatformId;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public String getMatchHistoryUri() {
        return matchHistoryUri;
    }

    public void setMatchHistoryUri(String matchHistoryUri) {
        this.matchHistoryUri = matchHistoryUri;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getCurrentAccountId() {
        return currentAccountId;
    }

    public void setCurrentAccountId(String currentAccountId) {
        this.currentAccountId = currentAccountId;
    }

    public Long getProfileIcon() {
        return profileIcon;
    }

    public void setProfileIcon(Long profileIcon) {
        this.profileIcon = profileIcon;
    }

    public String getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(String summonerId) {
        this.summonerId = summonerId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}