package be.ucll.service.models.match;

public class Types {
    public static class Timeline{
        public enum Roles {
            DUO, NONE, SOLO, DUO_CARRY, DUO_SUPPORT
        }
        public enum Lanes {
            MID, MIDDLE, TOP, JUNGLE, BOT, BOTTOM
        }
    }
    public static class Team{
        public enum Win {
            Fail,Win
        }
    }
    public static class Participant{
        public enum HighestAchievedSeasonTier {
            CHALLENGER, MASTER, DIAMOND, PLATINUM, GOLD, SILVER, BRONZE, UNRANKED
        }
    }

}
