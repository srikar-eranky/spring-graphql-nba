package com.stti.nba.repository;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stti.nba.entity.PlayerStats;
import com.stti.nba.entity.PlayerStatsInput;
import com.stti.nba.errors.dataexceptions.InvalidArgumentException;
import com.stti.nba.errors.dataexceptions.PlayerNotFoundException;

@Repository
public class PlayerStatsDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @SuppressWarnings("deprecation")
    public List<PlayerStats> getPlayerStats(int playerId) {
        try {
            return jdbcTemplate.query("SELECT * FROM PLAYERSTATS WHERE player_id = ?", new Object[]{playerId}, new PlayerStatsRowMapper());
        } catch (Exception e) {
            throw new PlayerNotFoundException("Player " + playerId + " not found");
        }
    }

    public int updatePlayerStats(PlayerStatsInput playerStatsInput) {
        if (playerStatsInput == null) {
            throw new InvalidArgumentException("input");
        }

        Double minPerGame = playerStatsInput.getMinpergame();
        Double ppg = playerStatsInput.getPpg();
        Double apg = playerStatsInput.getApg();
        Double rpg = playerStatsInput.getRpg(); 
        Double spg = playerStatsInput.getStealspergame();
        Double bpg = playerStatsInput.getBpg();           // Added block per game (bpg)
        Double tov = playerStatsInput.getTov();           // Added turnovers per game (tov)
        Double fgPercent = playerStatsInput.getFgpercent();
        Double ftPercent = playerStatsInput.getFtpercent();
        Double fgThreePercent = playerStatsInput.getFgthreepercent(); // Added field goal three-point percentage (fgthreepercent)
        String season = playerStatsInput.getSeason();

        StringBuilder sql = new StringBuilder("UPDATE PLAYERSTATS SET ");
        ArrayList<Object> arr = new ArrayList<>();
        boolean first = true;

        if (minPerGame < 0) {
            throw new InvalidArgumentException("minPerGame");
        } else {
            if(minPerGame != null) {
                sql.append("minpergame = ?");
                arr.add(minPerGame);
                first = false;
            }
        }
        if (ppg < 0) {
            throw new InvalidArgumentException("ppg");
        } else {
            if(ppg != null) {
                if(!first) sql.append(", ");
                sql.append("ppg = ?");
                arr.add(ppg);
                first = false;
            }
        }
        
        if (apg < 0) {
            throw new InvalidArgumentException("apg");
        } else {
            if(apg != null) {
                if(!first) sql.append(", ");
                sql.append("apg = ?");
                arr.add(apg);
                first = false;
            }
        }
        
        if (rpg < 0) {
            throw new InvalidArgumentException("rpg");
        } else {
            if(rpg != null) {
                if(!first) sql.append(", ");
                sql.append("rpg = ?");
                arr.add(rpg);
                first = false;
            }
        }
        
        if (spg < 0) {
            throw new InvalidArgumentException("spg");
        } else {
            if(spg != null) {
                if(!first) sql.append(", ");
                sql.append("stealspergame = ?");
                arr.add(spg);
                first = false;
            }
        }

        if (bpg < 0) {   // Added validation for bpg
            throw new InvalidArgumentException("bpg");
        } else {
            if(bpg != null) {
                if(!first) sql.append(", ");
                sql.append("bpg = ?");
                arr.add(bpg);
                first = false;
            }
        }

        if (tov < 0) {   // Added validation for tov
            throw new InvalidArgumentException("tov");
        } else {
            if(tov != null) {
                if(!first) sql.append(", ");
                sql.append("tov = ?");
                arr.add(tov);
                first = false;
            }
        }
        
        if (fgPercent < 0) {
            throw new InvalidArgumentException("fgPercent");
        } else {
            if(fgPercent != null) {
                if(!first) sql.append(", ");
                sql.append("fgpercent = ?");
                arr.add(fgPercent);
                first = false;
            }
        }
        
        if (ftPercent < 0) {
            throw new InvalidArgumentException("ftPercent");
        } else {
            if(ftPercent != null) {
                if(!first) sql.append(", ");
                sql.append("ftpercent = ?");
                arr.add(ftPercent);
                first = false;
            }
        }

        if (fgThreePercent < 0) {   // Added validation for fgthreepercent
            throw new InvalidArgumentException("fgThreePercent");
        } else {
            if(fgThreePercent != null) {
                if(!first) sql.append(", ");
                sql.append("fgthreepercent = ?");
                arr.add(fgThreePercent);
                first = false;
            }
        }

        if(season != null) {
            if(!first) sql.append(", ");
            sql.append("season = ?");
            arr.add(season);
            first = false;
        }
        
        sql.append(" WHERE player_id = ?");
        arr.add(playerStatsInput.getPlayerId());
        Object[] array = arr.toArray(new Object[0]);

        if(first) {
            throw new InvalidArgumentException("no args provided");
        }

        try {
            return jdbcTemplate.update(sql.toString(), array);
        } catch (Exception e){
            throw new PlayerNotFoundException("player " + playerStatsInput.getPlayerId() + " not found");
        }
    }

    public PlayerStats getPlayerStatsBySeason(int playerId, String season) {
        try {
            List<PlayerStats> playerStats = getPlayerStats(playerId);
            for(PlayerStats playerStat : playerStats) {
                if(playerStat.getSeason().equals(season)) {
                    return playerStat;
                }
            }
            return null;
        } catch (Exception e) {
            throw new PlayerNotFoundException("Player " + playerId + " not found");
        }
    }

    public List<PlayerStats> deletePlayerStats(int playerId) {
        List<PlayerStats> playerStats = getPlayerStats(playerId);
        try {
            jdbcTemplate.update("DELETE FROM PLAYERSTATS WHERE player_id = ?", new Object[]{playerId});
            return playerStats;
        } catch (Exception e) {
            throw new PlayerNotFoundException("Player " + playerId + " not found");
        }
    }
}
