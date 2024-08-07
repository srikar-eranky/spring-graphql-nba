package com.stti.nba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.stti.nba.entity.TeamStats;
import com.stti.nba.entity.TeamStatsInput;
import com.stti.nba.repository.TeamStatsDAO;

@Controller
public class TeamStatsController {

    @Autowired
    TeamStatsDAO teamStatsDAO;

    @QueryMapping(name = "getTeamStats")
    public List<TeamStats> getPlayerStats(@Argument("teamId") int teamId) {
        return teamStatsDAO.getTeamStats(teamId);
    }

    @QueryMapping(name = "getTeamStatsBySeason")
    public TeamStats getTeamStatsBySeason(@Argument("teamId") int teamId, @Argument("season") String season) {
        return teamStatsDAO.getTeamStatsBySeason(teamId, season);
    }

    @MutationMapping(name = "updateTeamStats")
    public int updateTeamStats(@Argument TeamStatsInput teamStatsInput) {
        return teamStatsDAO.updateTeamStats(teamStatsInput);
    }
}
