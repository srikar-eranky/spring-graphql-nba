package com.stti.nba.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stti.nba.entity.Team;
import com.stti.nba.errors.dataexceptions.TeamAlreadyExistsException;
import com.stti.nba.errors.dataexceptions.TeamNotFoundException;

@Repository
public class TeamDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // get all teams
    public List<Team> getAllTeams(){
        return jdbcTemplate.query("SELECT * from TEAM", new TeamRowMapper());
    }

    // get team by team id
    @SuppressWarnings("deprecation")
    public Team getTeamByID(int teamId) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM TEAM WHERE id = ?", new Object[]{teamId}, new TeamRowMapper());
        } catch (Exception e) {
            throw new TeamNotFoundException("Team " + teamId + " could not be found");
        }
    }

    // create a new team
    public int createTeam(String name, String city, String coach) {
        try {
            return jdbcTemplate.update("INSERT into TEAM (name,city,coach) VALUES (?,?,?)", name, city, coach);
        } catch (Exception e) {
            throw new TeamAlreadyExistsException("Team already exists");
        }
    }

    //update team

}
