package com.stti.nba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.stti.nba.entity.Team;
import com.stti.nba.repository.TeamDAO;

@Controller
public class TeamController {

    @Autowired
    TeamDAO teamDAO;

    @QueryMapping("teams")
    public List<Team> teams(){
        return teamDAO.getAllTeams();
    }

    @QueryMapping("teamById")
    public Team teamById(@Argument("id") int id) {
        return teamDAO.getTeamByID(id);
    }

    @MutationMapping(name = "createTeam")
    public int createTeam(@Argument String name, @Argument String city, @Argument String coach) {
        return teamDAO.createTeam(name, city, coach);
    }
}
