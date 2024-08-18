package com.stti.nba.repository;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.stti.nba.entity.Team;
import com.stti.nba.errors.dataexceptions.InvalidArgumentException;
import com.stti.nba.errors.dataexceptions.TeamAlreadyExistsException;
import com.stti.nba.errors.dataexceptions.TeamNotFoundException;

@Repository
public class TeamDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    PlayerDAO playerDAO;

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
            Team team = jdbcTemplate.queryForObject("SELECT * FROM TEAM WHERE id = ?", new Object[]{teamId}, new TeamRowMapper());
            team.setPlayers(playerDAO.getPlayersByTeamId(teamId));
            return team;
        } catch (EmptyResultDataAccessException e) {
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
    public int updateTeam(int teamId, String name, String city, String coach) {
        
        StringBuilder sql = new StringBuilder("UPDATE Team SET ");
        ArrayList<Object> arr = new ArrayList<>();
        boolean first = true;

        if(name != null) {
            sql.append("name = ?");
            arr.add(name);
            first = false;
        }

        if(city != null) {
            if(!first) {
                sql.append(", ");
            }
            sql.append("city = ?");
            arr.add(city);
            first = false;
        }

        if(coach != null) {
            if(!first) {
                sql.append(", ");
            }
            sql.append("coach = ?");
            arr.add(coach);
            first = false;
        }
        sql.append(" WHERE id = ? ");
        arr.add(teamId);
        Object[] array = arr.toArray();

        if(first) {
            throw new InvalidArgumentException("no args provided");
        }
        
        try {
            return jdbcTemplate.update(sql.toString(), array);
        } catch (Exception e) {
            throw new TeamNotFoundException("Team " + teamId + " not found");
        }
    }

    public Team deleteTeam(int teamId) {
        Team t = getTeamByID(teamId);

        if(t != null) {
            playerDAO.deletePlayersOnTeam(teamId);
            t.setPlayers(null);
            jdbcTemplate.update("DELETE FROM Team WHERE id = ?", new Object[]{teamId});
            return t;
        }
        return null;
    }
}
