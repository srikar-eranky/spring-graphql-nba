package com.stti.nba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.stti.nba.entity.PlayerStats;
import com.stti.nba.entity.PlayerStatsInput;
import com.stti.nba.repository.PlayerStatsDAO;

@Controller
public class PlayerStatsController {

    @Autowired
    PlayerStatsDAO playerStatsDAO;

    @QueryMapping(name = "getPlayerStats")
    public List<PlayerStats> getPlayerStats(@Argument("playerId") int playerId) {
        return playerStatsDAO.getPlayerStats(playerId);
    }

    @QueryMapping(name = "getPlayerStatsBySeason")
    public PlayerStats getPlayerStatsBySeason(@Argument("playerId") int playerId, @Argument("season") String season) {
        return playerStatsDAO.getPlayerStatsBySeason(playerId, season);
    }

    @MutationMapping(name = "updatePlayerStats")
    public int updatePlayerStats(@Argument("playerStatsInput") PlayerStatsInput playerStatsInput) {
        return playerStatsDAO.updatePlayerStats(playerStatsInput);
    }
}
