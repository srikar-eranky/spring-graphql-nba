package com.stti.nba.repository;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stti.nba.entity.Player;
import com.stti.nba.entity.PlayerInput;
import com.stti.nba.entity.PlayerStats;
import com.stti.nba.errors.dataexceptions.InvalidArgumentException;
import com.stti.nba.errors.dataexceptions.PlayerAlreadyExistsException;
import com.stti.nba.errors.dataexceptions.PlayerNotFoundException;
import com.stti.nba.errors.dataexceptions.TeamNotFoundException;

@Repository
public class PlayerDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    PlayerStatsDAO playerStatsDAO;

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public void setTemplate(JdbcTemplate jdbcTemplate) { 
        this.jdbcTemplate = jdbcTemplate;
    }

    // get all players
    public List<Player> getAllPlayers(){
        return jdbcTemplate.query("SELECT * from PLAYER", new PlayerRowMapper());
    }

    // get a player by their player id
    @SuppressWarnings("deprecation")
    public Player getPlayerByPlayerId(@Argument("id") int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM PLAYER where id = ?", new Object[]{id}, new PlayerRowMapper());
        } catch (EmptyResultDataAccessException e){
            String errMsg = "Player " + id + " can't be found";
            throw new PlayerNotFoundException(errMsg);
        }
    }

    // get all players in a team
    @SuppressWarnings("deprecation")
    public List<Player> getPlayersByTeamId(int teamId) {
        // Use a single query to check for team existence and retrieve players
        try {
            return jdbcTemplate.query("SELECT * FROM PLAYER WHERE TEAM_ID = ?", new Object[]{teamId}, new PlayerRowMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new TeamNotFoundException("team " + teamId + " not found");
        }
    }

    // create a player
    public int createPlayer(int playerId, PlayerInput playerInput) {
        // case 1: same player cannot be on same team
        // case 2: same player cannot be on different teams
        // case 3: players on different teams can have the same name
        int teamId = playerInput.getTeamId();
        String name = playerInput.getName();
        int age = playerInput.getAge();
        String height = playerInput.getHeight();
        String position = playerInput.getPosition();

        if(age < 0) {
            throw new InvalidArgumentException("age");
        }

        try {
            return jdbcTemplate.update("INSERT into PLAYER (id, team_id, name, age, height, position) VALUES (?, ?, ?, ?, ?, ?)", playerId, teamId, name, age, height, position);
        } catch (DuplicateKeyException e) {
            throw new PlayerAlreadyExistsException("Player already exists");
        }
    }

    // update Player's name and age
    public int updatePlayer(int playerId, PlayerInput playerInput) {
        Integer age = playerInput.getAge();
        int teamId = playerInput.getTeamId();
        String name = playerInput.getName();
        String height = playerInput.getHeight();
        String position = playerInput.getPosition();

        if (age != null && age.intValue() <= 0) {
            throw new InvalidArgumentException("age");
        }

        StringBuilder sql = new StringBuilder("UPDATE Player SET ");
        ArrayList<Object> arr = new ArrayList<>();
        boolean first = true;

        if(name != null) {
            sql.append("name = ?");
            arr.add(name);
            first = false;
        }

        if(age != null) {
            if(!first) {
                sql.append(",");
            }
            sql.append("age = ?");
            arr.add(age.intValue());
            first  = false;
        }

        if (height != null) {
            if (!first) {
                sql.append(", ");
            }
            sql.append("height = ?");
            arr.add(height);
            first = false;
        }

        if (position != null) {
            if (!first) {
                sql.append(", ");
            }
            sql.append("position = ?");
            arr.add(position);
            first = false;
        }

        if (!first) {
            sql.append(", ");
        }
        sql.append("team_id = ?");
        arr.add(teamId);

        sql.append(" WHERE id = ?");
        arr.add(playerId);
        Object[] array = arr.toArray(new Object[0]);

        if(first) {
            throw new InvalidArgumentException("no args provided");
        }

        try {
            return jdbcTemplate.update(sql.toString(), array);
        } catch (Exception e){
            throw new PlayerNotFoundException("player " + playerId + " not found");
        }
    }

    // delete a player - returns deleted player
    public Player deletePlayer(int playerId) {
        Player p = getPlayerByPlayerId(playerId);
        List<PlayerStats> playerStats = playerStatsDAO.deletePlayerStats(playerId);

        if(p != null && playerStats != null) {
            jdbcTemplate.update("DELETE FROM Player WHERE id = ?", new Object[]{playerId});
            return p;
        }
        return null;
    }

    public List<Player> deletePlayersOnTeam(int teamId) {
        List<Player> players = getPlayersByTeamId(teamId);

        try {
            for(Player p : players) {
                deletePlayer(p.getId());
            }
            return players;
        } catch (Exception e) {
            throw new TeamNotFoundException("Team " + teamId + " not found");
        }
    }

    public List<String> getPositions() {
        try {
            return jdbcTemplate.query("SELECT * FROM POSITION", (rs, rowNum) -> rs.getString("position"));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}