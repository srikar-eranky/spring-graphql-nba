package com.stti.nba.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.stti.nba.entity.PlayerStats;

public class PlayerStatsRowMapper implements RowMapper<PlayerStats> {

    @Override
    public PlayerStats mapRow(ResultSet rs, int rowNum) throws SQLException {
        final PlayerStats playerStats = new PlayerStats();
        playerStats.setId(rs.getInt("id"));
        playerStats.setPlayerId(rs.getInt("player_id"));
        playerStats.setMinpergame(rs.getDouble("minpergame"));
        playerStats.setPpg(rs.getDouble("ppg"));
        playerStats.setRpg(rs.getDouble("rpg"));
        playerStats.setApg(rs.getDouble("apg"));
        playerStats.setStealspergame(rs.getDouble("stealspergame"));
        playerStats.setBpg(rs.getDouble("bpg"));                // Added line for bpg
        playerStats.setTov(rs.getDouble("tov"));                // Added line for tov
        playerStats.setFgpercent(rs.getDouble("fgpercent"));
        playerStats.setFtpercent(rs.getDouble("ftpercent"));
        playerStats.setFgthreepercent(rs.getDouble("fgthreepercent")); // Added line for fgthreepercent
        playerStats.setSeason(rs.getString("season"));
        return playerStats;
    }
}
