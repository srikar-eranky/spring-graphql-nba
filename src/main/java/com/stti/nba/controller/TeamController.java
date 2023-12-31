package com.stti.nba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.stti.nba.entity.Team;
import com.stti.nba.repository.TeamDAO;

@Controller
public class TeamController {

    @Autowired
    TeamDAO teamDAO;

    @QueryMapping
    public List<Team> teams(){
        return teamDAO.getAllTeams();

    }

}
