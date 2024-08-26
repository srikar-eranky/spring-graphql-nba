package com.stti.nba.entity;


public class PlayerStats {
    private int id;
    private int playerId;
    private double minpergame;
    private double ppg;
    private double rpg;
    private double apg;
    private double bpg;
    private double tov;
    private double stealspergame;
    private double fgpercent;
    private double ftpercent;
    private double fgthreepercent;
    private String season;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public double getBpg() {
        return bpg;
    }

    public void setBpg(double bpg) {
        this.bpg = bpg;
    }

    public double getTov() {
        return tov;
    }

    public void setTov(double tov) {
        this.tov = tov;
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

    public double getFgthreepercent() {
        return fgthreepercent;
    }
    
    public void setFgthreepercent(double fgthreepercent) {
        this.fgthreepercent = fgthreepercent;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }
}
