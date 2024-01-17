package org.sayres.springmvc.models;

/**
 * @author Aliaksei Karabelnikau
 */
public class Participation {
    private int participation_id;
    private int player_id;
    private int game_id;
    private int hours_played;
    private String join_date;
    private boolean is_banned;
    private int kills;
    private int death;
    private int rank;

    public Participation() {
    }

    public Participation(int participation_id, int player_id, int game_id, int hours_played, String join_date, boolean is_banned, int kills, int death, int rank) {
        this.participation_id = participation_id;
        this.player_id = player_id;
        this.game_id = game_id;
        this.hours_played = hours_played;
        this.join_date = join_date;
        this.is_banned = is_banned;
        this.kills = kills;
        this.death = death;
        this.rank = rank;
    }

    public int getParticipation_id() {
        return participation_id;
    }

    public void setParticipation_id(int participation_id) {
        this.participation_id = participation_id;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public int getHours_played() {
        return hours_played;
    }

    public void setHours_played(int hours_played) {
        this.hours_played = hours_played;
    }

    public String getJoin_date() {
        return join_date;
    }

    public void setJoin_date(String join_date) {
        this.join_date = join_date;
    }

    public boolean isIs_banned() {
        return is_banned;
    }

    public void setIs_banned(boolean is_banned) {
        this.is_banned = is_banned;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
