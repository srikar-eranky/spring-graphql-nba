package com.stti.nba.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.stti.nba.entity.Player;

public class PlayerRowMapper implements RowMapper<Player> {

    @Override
    public Player mapRow(ResultSet rs, int rowNum) throws SQLException {
        final Player player = new Player();
        player.setId(rs.getInt("id"));
        player.setTeamId(rs.getInt("team_id"));
        player.setName(rs.getString("name"));
        player.setAge(rs.getInt("age"));
        player.setHeight(rs.getString("height"));
        player.setPosition(rs.getString("position"));

        return player;
        
    }

}
