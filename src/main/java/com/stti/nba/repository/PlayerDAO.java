package com.stti.nba.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stti.nba.entity.Player;

@Repository
public class PlayerDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Player> getAllPlayers(){
        return jdbcTemplate.query("SELECT * from PLAYER", new PlayerRowMapper());
    }

    public Player getPlayerByPlayerId(int id){
        if(id < 1 || id > 6){
            throw new IllegalArgumentException("id out of bounds");
        }
        return jdbcTemplate.queryForObject("SELECT * FROM PLAYER where id = ?", new Object[]{id}, new PlayerRowMapper());
    }

    public List<Player> getPlayersByTeamId(int id){
        if(id < 1 || id > 4){
            throw new IllegalArgumentException("id out of bounds");
        }
        return jdbcTemplate.query("Select * from PLAYER where team_id = ?", new Object[]{id}, new PlayerRowMapper());
    }

    public int createPlayer(int teamId, String name, String position){
        return jdbcTemplate.update("INSERT into PLAYER (team_id, name, position) VALUES (?, ?, ?)", teamId, name, position);
    }
}