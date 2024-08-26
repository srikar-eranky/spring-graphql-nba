package com.stti.nba.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.stti.nba.entity.TeamStats;

public class TeamStatsRowMapper implements RowMapper<TeamStats> {
    
    @Override
    public TeamStats mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        final TeamStats teamStats = new TeamStats();
        teamStats.setId(rs.getInt("id"));
        teamStats.setTeamId(rs.getInt("team_id"));
        teamStats.setPlayed(rs.getInt("played"));
        teamStats.setWon(rs.getInt("won"));
        teamStats.setLost(rs.getInt("lost"));
        teamStats.setPpg(rs.getDouble("ppg"));
        teamStats.setRpg(rs.getDouble("rpg"));
        teamStats.setApg(rs.getDouble("apg"));
        teamStats.setFgpercent(rs.getDouble("fgpercent"));
        teamStats.setFtpercent(rs.getDouble("ftpercent"));
        teamStats.setFgthreepercent(rs.getDouble("fgthreepercent"));
        teamStats.setSeason(rs.getString("season"));
        teamStats.setConfRank(rs.getInt("conf_rank"));
        
        return teamStats;
    }
}
