package be.ucll.service.models.match;


public class Stats {
    private Long neutralMinionsKilledTeamJungle;
    private Long visionScore;
    private Long magicDamageDealtToChampions;
    private Long largestMultiKill;
    private Long totalTimeCrowdControlDealt;
    private Long longestTimeSpentLiving;
    private Long[][] perkVars;
    private Long[] items;
    private Long[] statPerk;
    private Long[] playerScores;
    private Long[] perks;
    private Long tripleKills;
    private Long kills;
    private Long totalScoreRank;
    private Long neutralMinionsKilled;
    private Long damageDealtToTurrets;
    private Long physicalDamageDealtToChampions;
    private Long damageDealtToObjectives;
    private Long totalUnitsHealed;
    private Long totalDamageTaken;
    private Long wardsKilled;
    private Long largestCriticalStrike;
    private Long largestKillingSpree;
    private Long quadraKills;
    private Long magicDamageDealt;
    private Boolean firstBloodAssist;
    private Long damageSelfMitigated;
    private Long magicalDamageTaken;
    private Boolean firstInhibitorKill;
    private Long trueDamageTaken;
    private Long assists;
    private Long goldSpent;
    private Long trueDamageDealt;
    private Long participantId;
    private Long physicalDamageDealt;
    private Long sightWardsBoughtInGame;
    private Long totalDamageDealtToChampions;
    private Long physicalDamageTaken;
    private Long totalPlayerScore;
    private Boolean win;
    private Long objectivePlayerScore;
    private Long totalDamageDealt;
    private Long neutralMinionsKilledEnemyJungle;
    private Long deaths;
    private Long wardsPlaced;
    private Long perkPrimaryStyle;
    private Long perkSubStyle;
    private Long turretKills;
    private Boolean firstBloodKill;
    private Long trueDamageDealtToChampions;
    private Long goldEarned;
    private Long killingSprees;
    private Long unrealKills;
    private Boolean firstTowerAssist;
    private Boolean firstTowerKill;
    private Long champLevel;
    private Long doubleKills;
    private Long inhibitorKills;
    private Boolean firstInhibitorAssist;
    private Long combatPlayerScore;
    private Long visionWardsBoughtInGame;
    private Long pentaKills;
    private Long totalHeal;
    private Long totalMinionsKilled;
    private Long timeCCingOthers;

    public Stats(Long neutralMinionsKilledTeamJungle, Long visionScore, Long magicDamageDealtToChampions, Long largestMultiKill, Long totalTimeCrowdControlDealt, Long longestTimeSpentLiving, Long perk1Var1, Long perk1Var3, Long perk1Var2, Long tripleKills, Long perk5, Long perk4, Long playerScore9, Long playerScore8, Long kills, Long playerScore1, Long playerScore0, Long playerScore3, Long playerScore2, Long playerScore5, Long playerScore4, Long playerScore7, Long playerScore6, Long perk5Var1, Long perk5Var3, Long perk5Var2, Long totalScoreRank, Long neutralMinionsKilled, Long statPerk1, Long statPerk0, Long damageDealtToTurrets, Long physicalDamageDealtToChampions, Long damageDealtToObjectives, Long perk2Var2, Long perk2Var3, Long totalUnitsHealed, Long perk2Var1, Long perk4Var1, Long totalDamageTaken, Long perk4Var3, Long wardsKilled, Long largestCriticalStrike, Long largestKillingSpree, Long quadraKills, Long magicDamageDealt, Boolean firstBloodAssist, Long item2, Long item3, Long item0, Long item1, Long item6, Long item4, Long item5, Long perk1, Long perk0, Long perk3, Long perk2, Long perk3Var3, Long perk3Var2, Long perk3Var1, Long damageSelfMitigated, Long magicalDamageTaken, Long perk0Var2, Boolean firstInhibitorKill, Long trueDamageTaken, Long assists, Long perk4Var2, Long goldSpent, Long trueDamageDealt, Long participantId, Long physicalDamageDealt, Long sightWardsBoughtInGame, Long totalDamageDealtToChampions, Long physicalDamageTaken, Long totalPlayerScore, Boolean win, Long objectivePlayerScore, Long totalDamageDealt, Long neutralMinionsKilledEnemyJungle, Long deaths, Long wardsPlaced, Long perkPrimaryStyle, Long perkSubStyle, Long turretKills, Boolean firstBloodKill, Long trueDamageDealtToChampions, Long goldEarned, Long killingSprees, Long unrealKills, Boolean firstTowerAssist, Boolean firstTowerKill, Long champLevel, Long doubleKills, Long inhibitorKills, Boolean firstInhibitorAssist, Long perk0Var1, Long combatPlayerScore, Long perk0Var3, Long visionWardsBoughtInGame, Long pentaKills, Long totalHeal, Long totalMinionsKilled, Long timeCCingOthers, Long statPerk2) {
        setItems(item0,item1,item2,item3,item4,item5,item6);
        setPerks(perk0,perk1,perk2,perk3,perk4,perk5);
        setPerkVars(perk0Var1,perk0Var2,perk0Var3,perk1Var1,perk1Var2,perk1Var3,perk2Var1,perk2Var2,perk2Var3,perk3Var1,perk3Var2,perk3Var3,perk4Var1,perk4Var2,perk4Var3,perk5Var1,perk5Var2,perk5Var3);
        setPlayerScores(playerScore0,playerScore1,playerScore2,playerScore3,playerScore4,playerScore5,playerScore6,playerScore7,playerScore8,playerScore9);
        setStatPerk(statPerk0,statPerk1,statPerk2);
        setNeutralMinionsKilledTeamJungle(neutralMinionsKilledTeamJungle);
        setVisionScore(visionScore);
        setMagicDamageDealtToChampions(magicDamageDealtToChampions);
        setLongestTimeSpentLiving(longestTimeSpentLiving);
        setTripleKills(tripleKills);
        setFirstBloodAssist(firstBloodAssist);
        setTotalScoreRank(totalScoreRank);
        setTotalUnitsHealed(totalUnitsHealed);
        setTotalPlayerScore(totalPlayerScore);
        setTotalHeal(totalHeal);
        setTotalTimeCrowdControlDealt(totalTimeCrowdControlDealt);
        setTotalDamageDealt(totalDamageDealt);
        setTotalDamageTaken(totalDamageTaken);
        setDamageDealtToTurrets(damageDealtToTurrets);
        setDamageDealtToObjectives(damageDealtToObjectives);
        setDamageSelfMitigated(damageSelfMitigated);
        setLargestCriticalStrike(largestCriticalStrike);
        setLargestMultiKill(largestMultiKill);
        setLargestKillingSpree(largestKillingSpree);
        setNeutralMinionsKilled(neutralMinionsKilled);
        setPhysicalDamageDealtToChampions(physicalDamageDealtToChampions);
        setWardsKilled(wardsKilled);
        setQuadraKills(quadraKills);
        setMagicDamageDealt(magicDamageDealt);
        setMagicalDamageTaken(magicalDamageTaken);
        setFirstInhibitorKill(firstInhibitorKill);
        setTrueDamageTaken(trueDamageTaken);
        setAssists(assists);
        setGoldSpent(goldSpent);
        setTrueDamageDealt(trueDamageDealt);
        setParticipantId(participantId);
        setPhysicalDamageDealt(physicalDamageDealt);
        setSightWardsBoughtInGame(sightWardsBoughtInGame);
        setTotalDamageDealtToChampions(totalDamageDealtToChampions);
        setPhysicalDamageTaken(physicalDamageTaken);
        setWin(win);
        setObjectivePlayerScore(objectivePlayerScore);
        setNeutralMinionsKilledEnemyJungle(neutralMinionsKilledEnemyJungle);
        setDeaths(deaths);
        setWardsPlaced(wardsPlaced);
        setPerkPrimaryStyle(perkPrimaryStyle);
        setPerkSubStyle(perkSubStyle);
        setTurretKills(turretKills);
        setFirstBloodKill(firstBloodKill);
        setTrueDamageDealtToChampions(trueDamageDealtToChampions);
        setGoldEarned(goldEarned);
        setKillingSprees(killingSprees);
        setUnrealKills(unrealKills);
        setFirstTowerAssist(firstTowerAssist);
        setFirstTowerKill(firstTowerKill);
        setChampLevel(champLevel);
        setDoubleKills(doubleKills);
        setInhibitorKills(inhibitorKills);
        setFirstInhibitorAssist(firstInhibitorAssist);
        setCombatPlayerScore(combatPlayerScore);
        setVisionWardsBoughtInGame(visionWardsBoughtInGame);
        setPentaKills(pentaKills);
        setTotalMinionsKilled(totalMinionsKilled);
        setTimeCCingOthers(timeCCingOthers);
    }

    public Long getNeutralMinionsKilledTeamJungle() {
        return neutralMinionsKilledTeamJungle;
    }

    public void setNeutralMinionsKilledTeamJungle(Long neutralMinionsKilledTeamJungle) {
        this.neutralMinionsKilledTeamJungle = neutralMinionsKilledTeamJungle;
    }

    public Long getVisionScore() {
        return visionScore;
    }

    public void setVisionScore(Long visionScore) {
        this.visionScore = visionScore;
    }

    public Long getMagicDamageDealtToChampions() {
        return magicDamageDealtToChampions;
    }

    public void setMagicDamageDealtToChampions(Long magicDamageDealtToChampions) {
        this.magicDamageDealtToChampions = magicDamageDealtToChampions;
    }

    public Long getLargestMultiKill() {
        return largestMultiKill;
    }

    public void setLargestMultiKill(Long largestMultiKill) {
        this.largestMultiKill = largestMultiKill;
    }

    public Long getTotalTimeCrowdControlDealt() {
        return totalTimeCrowdControlDealt;
    }

    public void setTotalTimeCrowdControlDealt(Long totalTimeCrowdControlDealt) {
        this.totalTimeCrowdControlDealt = totalTimeCrowdControlDealt;
    }

    public Long getLongestTimeSpentLiving() {
        return longestTimeSpentLiving;
    }

    public void setLongestTimeSpentLiving(Long longestTimeSpentLiving) {
        this.longestTimeSpentLiving = longestTimeSpentLiving;
    }

    public Long getTripleKills() {
        return tripleKills;
    }

    public void setTripleKills(Long tripleKills) {
        this.tripleKills = tripleKills;
    }

    public Long getKills() {
        return kills;
    }

    public void setKills(Long kills) {
        this.kills = kills;
    }

    public Long getTotalScoreRank() {
        return totalScoreRank;
    }

    public void setTotalScoreRank(Long totalScoreRank) {
        this.totalScoreRank = totalScoreRank;
    }

    public Long getNeutralMinionsKilled() {
        return neutralMinionsKilled;
    }

    public void setNeutralMinionsKilled(Long neutralMinionsKilled) {
        this.neutralMinionsKilled = neutralMinionsKilled;
    }

    public Long getDamageDealtToTurrets() {
        return damageDealtToTurrets;
    }

    public void setDamageDealtToTurrets(Long damageDealtToTurrets) {
        this.damageDealtToTurrets = damageDealtToTurrets;
    }

    public Long getPhysicalDamageDealtToChampions() {
        return physicalDamageDealtToChampions;
    }

    public void setPhysicalDamageDealtToChampions(Long physicalDamageDealtToChampions) {
        this.physicalDamageDealtToChampions = physicalDamageDealtToChampions;
    }

    public Long getDamageDealtToObjectives() {
        return damageDealtToObjectives;
    }

    public void setDamageDealtToObjectives(Long damageDealtToObjectives) {
        this.damageDealtToObjectives = damageDealtToObjectives;
    }

    public Long getTotalUnitsHealed() {
        return totalUnitsHealed;
    }

    public void setTotalUnitsHealed(Long totalUnitsHealed) {
        this.totalUnitsHealed = totalUnitsHealed;
    }

    public Long getTotalDamageTaken() {
        return totalDamageTaken;
    }

    public void setTotalDamageTaken(Long totalDamageTaken) {
        this.totalDamageTaken = totalDamageTaken;
    }

    public Long getWardsKilled() {
        return wardsKilled;
    }

    public void setWardsKilled(Long wardsKilled) {
        this.wardsKilled = wardsKilled;
    }

    public Long getLargestCriticalStrike() {
        return largestCriticalStrike;
    }

    public void setLargestCriticalStrike(Long largestCriticalStrike) {
        this.largestCriticalStrike = largestCriticalStrike;
    }

    public Long getLargestKillingSpree() {
        return largestKillingSpree;
    }

    public void setLargestKillingSpree(Long largestKillingSpree) {
        this.largestKillingSpree = largestKillingSpree;
    }

    public Long getQuadraKills() {
        return quadraKills;
    }

    public void setQuadraKills(Long quadraKills) {
        this.quadraKills = quadraKills;
    }

    public Long getMagicDamageDealt() {
        return magicDamageDealt;
    }

    public void setMagicDamageDealt(Long magicDamageDealt) {
        this.magicDamageDealt = magicDamageDealt;
    }

    public Boolean getFirstBloodAssist() {
        return firstBloodAssist;
    }

    public void setFirstBloodAssist(Boolean firstBloodAssist) {
        this.firstBloodAssist = firstBloodAssist;
    }

    public Long getDamageSelfMitigated() {
        return damageSelfMitigated;
    }

    public void setDamageSelfMitigated(Long damageSelfMitigated) {
        this.damageSelfMitigated = damageSelfMitigated;
    }

    public Long getMagicalDamageTaken() {
        return magicalDamageTaken;
    }

    public void setMagicalDamageTaken(Long magicalDamageTaken) {
        this.magicalDamageTaken = magicalDamageTaken;
    }

    public Boolean getFirstInhibitorKill() {
        return firstInhibitorKill;
    }

    public void setFirstInhibitorKill(Boolean firstInhibitorKill) {
        this.firstInhibitorKill = firstInhibitorKill;
    }

    public Long getTrueDamageTaken() {
        return trueDamageTaken;
    }

    public void setTrueDamageTaken(Long trueDamageTaken) {
        this.trueDamageTaken = trueDamageTaken;
    }

    public Long getAssists() {
        return assists;
    }

    public void setAssists(Long assists) {
        this.assists = assists;
    }

    public Long getGoldSpent() {
        return goldSpent;
    }

    public void setGoldSpent(Long goldSpent) {
        this.goldSpent = goldSpent;
    }

    public Long getTrueDamageDealt() {
        return trueDamageDealt;
    }

    public void setTrueDamageDealt(Long trueDamageDealt) {
        this.trueDamageDealt = trueDamageDealt;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    public Long getPhysicalDamageDealt() {
        return physicalDamageDealt;
    }

    public void setPhysicalDamageDealt(Long physicalDamageDealt) {
        this.physicalDamageDealt = physicalDamageDealt;
    }

    public Long getSightWardsBoughtInGame() {
        return sightWardsBoughtInGame;
    }

    public void setSightWardsBoughtInGame(Long sightWardsBoughtInGame) {
        this.sightWardsBoughtInGame = sightWardsBoughtInGame;
    }

    public Long getTotalDamageDealtToChampions() {
        return totalDamageDealtToChampions;
    }

    public void setTotalDamageDealtToChampions(Long totalDamageDealtToChampions) {
        this.totalDamageDealtToChampions = totalDamageDealtToChampions;
    }

    public Long getPhysicalDamageTaken() {
        return physicalDamageTaken;
    }

    public void setPhysicalDamageTaken(Long physicalDamageTaken) {
        this.physicalDamageTaken = physicalDamageTaken;
    }

    public Long getTotalPlayerScore() {
        return totalPlayerScore;
    }

    public void setTotalPlayerScore(Long totalPlayerScore) {
        this.totalPlayerScore = totalPlayerScore;
    }

    public Boolean getWin() {
        return win;
    }

    public void setWin(Boolean win) {
        this.win = win;
    }

    public Long getObjectivePlayerScore() {
        return objectivePlayerScore;
    }

    public void setObjectivePlayerScore(Long objectivePlayerScore) {
        this.objectivePlayerScore = objectivePlayerScore;
    }

    public Long getTotalDamageDealt() {
        return totalDamageDealt;
    }

    public void setTotalDamageDealt(Long totalDamageDealt) {
        this.totalDamageDealt = totalDamageDealt;
    }

    public Long getNeutralMinionsKilledEnemyJungle() {
        return neutralMinionsKilledEnemyJungle;
    }

    public void setNeutralMinionsKilledEnemyJungle(Long neutralMinionsKilledEnemyJungle) {
        this.neutralMinionsKilledEnemyJungle = neutralMinionsKilledEnemyJungle;
    }

    public Long getDeaths() {
        return deaths;
    }

    public void setDeaths(Long deaths) {
        this.deaths = deaths;
    }

    public Long getWardsPlaced() {
        return wardsPlaced;
    }

    public void setWardsPlaced(Long wardsPlaced) {
        this.wardsPlaced = wardsPlaced;
    }

    public Long getPerkPrimaryStyle() {
        return perkPrimaryStyle;
    }

    public void setPerkPrimaryStyle(Long perkPrimaryStyle) {
        this.perkPrimaryStyle = perkPrimaryStyle;
    }

    public Long getPerkSubStyle() {
        return perkSubStyle;
    }

    public void setPerkSubStyle(Long perkSubStyle) {
        this.perkSubStyle = perkSubStyle;
    }

    public Long getTurretKills() {
        return turretKills;
    }

    public void setTurretKills(Long turretKills) {
        this.turretKills = turretKills;
    }

    public Boolean getFirstBloodKill() {
        return firstBloodKill;
    }

    public void setFirstBloodKill(Boolean firstBloodKill) {
        this.firstBloodKill = firstBloodKill;
    }

    public Long getTrueDamageDealtToChampions() {
        return trueDamageDealtToChampions;
    }

    public void setTrueDamageDealtToChampions(Long trueDamageDealtToChampions) {
        this.trueDamageDealtToChampions = trueDamageDealtToChampions;
    }

    public Long getGoldEarned() {
        return goldEarned;
    }

    public void setGoldEarned(Long goldEarned) {
        this.goldEarned = goldEarned;
    }

    public Long getKillingSprees() {
        return killingSprees;
    }

    public void setKillingSprees(Long killingSprees) {
        this.killingSprees = killingSprees;
    }

    public Long getUnrealKills() {
        return unrealKills;
    }

    public void setUnrealKills(Long unrealKills) {
        this.unrealKills = unrealKills;
    }

    public Boolean getFirstTowerAssist() {
        return firstTowerAssist;
    }

    public void setFirstTowerAssist(Boolean firstTowerAssist) {
        this.firstTowerAssist = firstTowerAssist;
    }

    public Boolean getFirstTowerKill() {
        return firstTowerKill;
    }

    public void setFirstTowerKill(Boolean firstTowerKill) {
        this.firstTowerKill = firstTowerKill;
    }

    public Long getChampLevel() {
        return champLevel;
    }

    public void setChampLevel(Long champLevel) {
        this.champLevel = champLevel;
    }

    public Long getDoubleKills() {
        return doubleKills;
    }

    public void setDoubleKills(Long doubleKills) {
        this.doubleKills = doubleKills;
    }

    public Long getInhibitorKills() {
        return inhibitorKills;
    }

    public void setInhibitorKills(Long inhibitorKills) {
        this.inhibitorKills = inhibitorKills;
    }

    public Boolean getFirstInhibitorAssist() {
        return firstInhibitorAssist;
    }

    public void setFirstInhibitorAssist(Boolean firstInhibitorAssist) {
        this.firstInhibitorAssist = firstInhibitorAssist;
    }

    public Long getCombatPlayerScore() {
        return combatPlayerScore;
    }

    public void setCombatPlayerScore(Long combatPlayerScore) {
        this.combatPlayerScore = combatPlayerScore;
    }

    public Long getVisionWardsBoughtInGame() {
        return visionWardsBoughtInGame;
    }

    public void setVisionWardsBoughtInGame(Long visionWardsBoughtInGame) {
        this.visionWardsBoughtInGame = visionWardsBoughtInGame;
    }

    public Long getPentaKills() {
        return pentaKills;
    }

    public void setPentaKills(Long pentaKills) {
        this.pentaKills = pentaKills;
    }

    public Long getTotalHeal() {
        return totalHeal;
    }

    public void setTotalHeal(Long totalHeal) {
        this.totalHeal = totalHeal;
    }

    public Long getTotalMinionsKilled() {
        return totalMinionsKilled;
    }

    public void setTotalMinionsKilled(Long totalMinionsKilled) {
        this.totalMinionsKilled = totalMinionsKilled;
    }

    public Long getTimeCCingOthers() {
        return timeCCingOthers;
    }

    public void setTimeCCingOthers(Long timeCCingOthers) {
        this.timeCCingOthers = timeCCingOthers;
    }

    public Long[][] getPerkVars() {
        return perkVars;
    }

    public void setPerkVars(Long perk0Var1,Long perk0Var2,Long perk0Var3,
                            Long perk1Var1,Long perk1Var2,Long perk1Var3,
                            Long perk2Var1,Long perk2Var2,Long perk2Var3,
                            Long perk3Var1,Long perk3Var2,Long perk3Var3,
                            Long perk4Var1,Long perk4Var2,Long perk4Var3,
                            Long perk5Var1,Long perk5Var2,Long perk5Var3
                            ) {
        this.perkVars = new Long[][]{
                {perk0Var1,perk0Var2,perk0Var3},
                {perk1Var1,perk1Var2,perk1Var3},
                {perk2Var1,perk2Var2,perk2Var3},
                {perk3Var1,perk3Var2,perk3Var3},
                {perk4Var1,perk4Var2,perk4Var3},
                {perk5Var1,perk5Var2,perk5Var3},
        };
    }

    public Long[] getItems() {
        return items;
    }

    public void setItems(Long item0, Long item1, Long item2, Long item3, Long item4, Long item5, Long item6) {
        this.items = new Long[]{item0, item1, item2, item3, item4, item5, item6};
    }

    public Long[] getStatPerk() {
        return statPerk;
    }

    public void setStatPerk(Long statsPerk0, Long statsPerk1,Long statsPerk2) {
        this.statPerk = new Long[]{statsPerk0,statsPerk1,statsPerk2};
    }

    public Long[] getPlayerScores() {
        return playerScores;
    }

    public void setPlayerScores(Long playerScore0, Long playerScore1,Long playerScore2,Long playerScore3,Long playerScore4,Long playerScore5,Long playerScore6,Long playerScore7,Long playerScore8,Long playerScore9) {
        this.playerScores = new Long[]{playerScore0,playerScore1,playerScore2,playerScore3,playerScore4,playerScore5,playerScore6,playerScore7,playerScore8,playerScore9};
    }

    public Long[] getPerks() {
        return perks;
    }

    public void setPerks(Long perk0, Long perk1, Long perk2, Long perk3, Long perk4, Long perk5) {
        this.perks = new Long[]{perk0,perk1,perk2,perk3,perk4,perk5};
    }
}
