package com.stti.nba.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.stti.nba.entity.Player;
import com.stti.nba.entity.PlayerInput;
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
    public Player playerByPlayerId(@Argument("id") int id) {
        return playerDAO.getPlayerByPlayerId(id);
    }

    @QueryMapping("playersByTeamId")
    public List<Player> playersByTeamId(@Argument("teamId") int id){
        return playerDAO.getPlayersByTeamId(id);
    }

    // post
    @MutationMapping(name = "createPlayer")
    public int createPlayer(@Argument int teamId, @Argument PlayerInput playerInput){
        // do not add player to non existent team
        Random random = new Random();
        int playerId = 1000 + random.nextInt(9000);
        return playerDAO.createPlayer(playerId, teamId, playerInput);
    }

    //update
    @MutationMapping(name = "updatePlayer")
    public int updatePlayer(@Argument int playerId, @Argument PlayerInput playerInput) {
        return playerDAO.updatePlayer(playerId, playerInput);
    }

    //delete
    @MutationMapping(name = "deletePlayer")
    public Player deletePlayer(@Argument int playerId) {
        return playerDAO.deletePlayer(playerId);
    }
}
