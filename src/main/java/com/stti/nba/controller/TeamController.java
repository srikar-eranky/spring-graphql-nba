package com.stti.nba.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.stti.nba.entity.Team;
import com.stti.nba.entity.TeamInput;
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
    public int createTeam(@Argument TeamInput teamInput) {
        Random random = new Random();
        int teamId = 10000 + random.nextInt(90000);
        return teamDAO.createTeam(teamId, teamInput);
    }

    @MutationMapping(name = "updateTeam")
    public int updateTeam(@Argument int teamId, @Argument TeamInput teamInput) {
        return teamDAO.updateTeam(teamId, teamInput);
    }

    @MutationMapping(name = "deleteTeam")
    public Team deleteTeam(@Argument int teamId) {
        return teamDAO.deleteTeam(teamId);
    }
}
