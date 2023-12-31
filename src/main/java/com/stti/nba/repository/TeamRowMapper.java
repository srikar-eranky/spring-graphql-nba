package com.stti.nba.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.stti.nba.entity.Team;

public class TeamRowMapper implements RowMapper<Team>{

    @Override
    public Team mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Team team = new Team();
        team.setId(rs.getInt("id"));
        team.setName(rs.getString("name"));
        team.setCity(rs.getString("city"));
        team.setArena(rs.getString("arena"));
        team.setCoach(rs.getString("coach"));
        team.setOwner(rs.getString("owner"));
        team.setFounded(rs.getString("founded"));

        return team;
        
    }

    
}
