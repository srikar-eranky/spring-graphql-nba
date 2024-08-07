package com.stti.nba.entity;

import java.util.List;

public class Team {
    private int id;
    private String name;
    private String city;
    private String coach;
    private String arena;
    private String founded;
    private String owner;
    private boolean active;
    private List<Player> players;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCoach() {
        return coach;
    }
    public void setCoach(String coach) {
        this.coach = coach;
    }
    public String getArena() {
        return arena;
    }
    public void setArena(String arena) {
        this.arena = arena;
    }
    public String getFounded() {
        return founded;
    }
    public void setFounded(String founded) {
        this.founded = founded;
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public boolean getActive(){
        return this.active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public List<Player> getPlayers() {
        return players;
    }
    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
