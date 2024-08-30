package com.stti.nba.repository;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.stti.nba.entity.Team;
import com.stti.nba.entity.TeamInput;
import com.stti.nba.errors.dataexceptions.InvalidArgumentException;
import com.stti.nba.errors.dataexceptions.TeamAlreadyExistsException;
import com.stti.nba.errors.dataexceptions.TeamNotFoundException;

@Repository
public class TeamDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    PlayerDAO playerDAO;

    @Autowired
    TeamStatsDAO teamStatsDAO;

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // get all teams
    public List<Team> getAllTeams(){
        List<Team> teams = jdbcTemplate.query("SELECT * from TEAM", new TeamRowMapper());
        for(Team t : teams) {
            t.setPlayers(playerDAO.getPlayersByTeamId(t.getId()));
        }
        return teams;
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
    public int createTeam(int teamId, TeamInput teamInput) {
        try {
            String sql = "INSERT INTO TEAM (id, name, city, arena, founded, owner, coach, conference) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            return jdbcTemplate.update(sql, 
                teamId, 
                teamInput.getName(), 
                teamInput.getCity(), 
                teamInput.getArena(), 
                teamInput.getFounded(), 
                teamInput.getOwner(), 
                teamInput.getCoach(), 
                teamInput.getConference()
            );
        } catch (Exception e) {
            throw new TeamAlreadyExistsException("Team already exists");
        }
    }

    //update team
    public int updateTeam(int teamId, TeamInput teamInput) {

        String name = teamInput.getName();
        String city = teamInput.getCity();
        String coach = teamInput.getCoach();
        String arena = teamInput.getArena();
        String founded = teamInput.getFounded();
        String owner = teamInput.getOwner();
        String conference = teamInput.getConference();
        
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

        if (arena != null) {
            if (!first) {
                sql.append(", ");
            }
            sql.append("arena = ?");
            arr.add(arena);
            first = false;
        }
        
        if (founded != null) {
            if (!first) {
                sql.append(", ");
            }
            sql.append("founded = ?");
            arr.add(founded);
            first = false;
        }
        
        if (owner != null) {
            if (!first) {
                sql.append(", ");
            }
            sql.append("owner = ?");
            arr.add(owner);
            first = false;
        }

        if (conference != null) {
            if (!first) sql.append(", ");
            sql.append("conference = ?");
            arr.add(conference);
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
            teamStatsDAO.deleteTeamStats(teamId);
            t.setPlayers(null);
            jdbcTemplate.update("DELETE FROM Team WHERE id = ?", new Object[]{teamId});
            return t;
        }
        return null;
    }
}
