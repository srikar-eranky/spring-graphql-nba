package com.stti.nba.repository;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stti.nba.entity.TeamStats;
import com.stti.nba.entity.TeamStatsInput;
import com.stti.nba.errors.dataexceptions.InvalidArgumentException;
import com.stti.nba.errors.dataexceptions.TeamNotFoundException;

@Repository
public class TeamStatsDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @SuppressWarnings("deprecation")
    public List<TeamStats> getTeamStats(int teamId) {
        try {
            return jdbcTemplate.query("SELECT * FROM TEAMSTATS WHERE team_id = ?", new Object[]{teamId}, new TeamStatsRowMapper());
        } catch (Exception e) {
            e.printStackTrace();
            throw new TeamNotFoundException("Team " + teamId  + " not found");
        }
    }

    public int updateTeamStats(TeamStatsInput teamStatsInput) { 
        if(teamStatsInput == null) {
            throw new InvalidArgumentException("input");
        }

        Integer played = teamStatsInput.getPlayed();
        Integer won = teamStatsInput.getWon();
        Integer lost = teamStatsInput.getLost();
        Double ppg = teamStatsInput.getPpg();
        Double rpg = teamStatsInput.getRpg();
        Double apg = teamStatsInput.getApg();
        Double fgpercent = teamStatsInput.getFgpercent();
        Double ftpercent = teamStatsInput.getFtpercent();
        String season = teamStatsInput.getSeason();
        
        StringBuilder sql = new StringBuilder("UPDATE TEAMSTATS SET ");
        ArrayList<Object> params = new ArrayList<>();
        boolean first = true;

        if(played < 0) {
            throw new InvalidArgumentException("played");
        } else {
            if(played != null) {
                sql.append("played = ?");
                params.add(played);
                first = false;
            }
        }

        if (won < 0) {
            throw new InvalidArgumentException("won");
        } else {
            if(won != null) {
                if(!first) sql.append(", ");
                sql.append("won = ?");
                params.add(won);
                first = false;
            }
        }

        if (lost != null) {
            if (lost < 0) {
                throw new InvalidArgumentException("lost");
            } else {
                sql.append(first ? "" : ", ").append("lost = ?");
                params.add(lost);
                first = false;
            }
        }
    
        if (ppg != null) {
            if (ppg < 0) {
                throw new InvalidArgumentException("ppg");
            } else {
                sql.append(first ? "" : ", ").append("ppg = ?");
                params.add(ppg);
                first = false;
            }
        }
    
        if (rpg != null) {
            if (rpg < 0) {
                throw new InvalidArgumentException("rpg");
            } else {
                sql.append(first ? "" : ", ").append("rpg = ?");
                params.add(rpg);
                first = false;
            }
        }
    
        if (apg != null) {
            if (apg < 0) {
                throw new InvalidArgumentException("apg");
            } else {
                sql.append(first ? "" : ", ").append("apg = ?");
                params.add(apg);
                first = false;
            }
        }
    
        if (fgpercent != null) {
            if (fgpercent < 0) {
                throw new InvalidArgumentException("fgpercent");
            } else {
                sql.append(first ? "" : ", ").append("fgpercent = ?");
                params.add(fgpercent);
                first = false;
            }
        }
    
        if (ftpercent != null) {
            if (ftpercent < 0) {
                throw new InvalidArgumentException("ftpercent");
            } else {
                sql.append(first ? "" : ", ").append("ftpercent = ?");
                params.add(ftpercent);
                first = false;
            }
        }

        if(season != null) {
            sql.append(first ? "" : ", ").append("season = ?");
            params.add(season);
            first = false;
        }

        sql.append(" WHERE team_id = ?");
        params.add(teamStatsInput.getTeamId());

        if(params.size() <= 1) {
            throw new InvalidArgumentException("no args provided");
        }

        try {
            return jdbcTemplate.update(sql.toString(), params);
        } catch (Exception e) {
            throw new TeamNotFoundException("Team " + teamStatsInput.getTeamId() + " not found");
        }
    }

    public TeamStats getTeamStatsBySeason(int teamId, String season) {
        try {
            List<TeamStats> teamStatsList = getTeamStats(teamId);
            for(TeamStats teamStat : teamStatsList) {
                if(teamStat.getSeason().equals(season)) {
                    return teamStat;
                }
            }
            return null;
        } catch (Exception e) {
            throw new TeamNotFoundException("Team " + teamId + " not found");
        }
        
    }
}
