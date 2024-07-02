package com.stti.nba.repository;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stti.nba.entity.Player;
import com.stti.nba.errors.dataexceptions.InvalidArgumentException;
import com.stti.nba.errors.dataexceptions.PlayerAlreadyExistsException;
import com.stti.nba.errors.dataexceptions.PlayerNotFoundException;

@Repository
public class PlayerDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // get all players
    public List<Player> getAllPlayers(){
        return jdbcTemplate.query("SELECT * from PLAYER", new PlayerRowMapper());
    }

    // get a player by their player id
    @SuppressWarnings("deprecation")
    public Player getPlayerByPlayerId(@Argument("id") int id) throws PlayerNotFoundException {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM PLAYER where id = ?", new Object[]{id}, new PlayerRowMapper());
        } catch (Exception e){
            String errMsg = "Player " + id + " can't be found";
            throw new PlayerNotFoundException(errMsg);
        }
    }

    // get all players in a team
    @SuppressWarnings("deprecation")
    public List<Player> getPlayersByTeamId(int teamId) {
        // Use a single query to check for team existence and retrieve players
        List<Player> players = jdbcTemplate.query(
                "SELECT * FROM PLAYER WHERE TEAM_ID = ? AND EXISTS (SELECT 1 FROM TEAM WHERE ID = ?)",
                new Object[]{teamId, teamId},
                new PlayerRowMapper()
        );
        return players;
    }

    // create a player
    public int createPlayer(int teamId, String name, String position) {
        try {
            return jdbcTemplate.update("INSERT into PLAYER (team_id, name, position) VALUES (?, ?, ?)", teamId, name, position);
        } catch (Exception e) {
            throw new PlayerAlreadyExistsException("Player already exists");
        }
    }

    // update Player's name and age
    public int updatePlayer(int playerId, String name, Integer age) {

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
        sql.append(" WHERE id = ?");
        arr.add(playerId);
        Object[] array = arr.toArray(new Object[0]);

        if(first) {
            throw new InvalidArgumentException("no args provided");
        }

        int count = jdbcTemplate.update(sql.toString(), array);
        
        if(count <= 0) {
            throw new PlayerNotFoundException("player " + playerId + " not found");
        }
        
        return count;
    }

    // delete a player - returns deleted player
    public Player deletePlayer(int playerId) {
        Player p = getPlayerByPlayerId(playerId);

        if(p != null) {
            jdbcTemplate.update("DELETE FROM Player WHERE id = ?", new Object[]{playerId});
            return p;
        }
        return null;
    }
}