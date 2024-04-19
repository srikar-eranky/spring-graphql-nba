package com.stti.nba.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stti.nba.entity.Team;

@Repository
public class TeamDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Team> getAllTeams(){
        return jdbcTemplate.query("SELECT * from TEAM", new TeamRowMapper());
    }

    public Team getTeamByID(int id){
        if(id < 1 || id > 4){
            throw new IllegalArgumentException("id out of bounds");
        }
        return jdbcTemplate.queryForObject("SELECT * FROM TEAM WHERE id = ?", new Object[]{id}, new TeamRowMapper());
    }
}
