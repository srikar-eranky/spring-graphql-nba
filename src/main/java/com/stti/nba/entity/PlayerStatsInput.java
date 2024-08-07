package com.stti.nba.entity;

public class PlayerStatsInput {
    private int playerId;
    private double minpergame;
    private double ppg;
    private double rpg;
    private double apg;
    private double stealspergame;
    private double fgpercent;
    private double ftpercent;
    private String season;

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public double getMinpergame() {
        return minpergame;
    }

    public void setMinpergame(double minpergame) {
        this.minpergame = minpergame;
    }

    public double getPpg() {
        return ppg;
    }

    public void setPpg(double ppg) {
        this.ppg = ppg;
    }

    public double getRpg() {
        return rpg;
    }

    public void setRpg(double rpg) {
        this.rpg = rpg;
    }

    public double getApg() {
        return apg;
    }

    public void setApg(double apg) {
        this.apg = apg;
    }

    public double getStealspergame() {
        return stealspergame;
    }

    public void setStealspergame(double stealspergame) {
        this.stealspergame = stealspergame;
    }

    public double getFgpercent() {
        return fgpercent;
    }

    public void setFgpercent(double fgpercent) {
        this.fgpercent = fgpercent;
    }

    public double getFtpercent() {
        return ftpercent;
    }

    public void setFtpercent(double ftpercent) {
        this.ftpercent = ftpercent;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }
}
