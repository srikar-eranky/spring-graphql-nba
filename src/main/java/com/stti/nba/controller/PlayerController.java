package com.stti.nba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.Arguments;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.stti.nba.entity.Player;
import com.stti.nba.repository.PlayerDAO;

@Controller
public class PlayerController {
    
    @Autowired
    PlayerDAO playerDAO;

    // get
    @QueryMapping("players")
    public List<Player> players(){
        return playerDAO.getAllPlayers();
    }

    @QueryMapping("playerByPlayerId")
    public Player playerByPlayerId(@Argument("id") int id){
        return playerDAO.getPlayerByPlayerId(id);
    }

    @QueryMapping("playersByTeamId")
    public List<Player> playersByTeamId(@Argument("teamId") int id){
        return playerDAO.getPlayersByTeamId(id);
    }

    // post
    @MutationMapping
    public int createPlayer(@Argument int teamId, @Argument String name, @Argument String position){
        return playerDAO.createPlayer(teamId,name,position);
    }

    //update

    //delete
}
