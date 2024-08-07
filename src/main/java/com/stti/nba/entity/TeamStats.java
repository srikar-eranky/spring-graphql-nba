package com.stti.nba.entity;

public class TeamStats {

    private int id;
    private int teamId;
    private int played;
    private int won;
    private int lost;
    private double ppg;
    private double rpg;
    private double apg;
    private double fgpercent;
    private double ftpercent;
    private String season;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public int getWon() {
        return won;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
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
