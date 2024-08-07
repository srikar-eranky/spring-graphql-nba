package com.stti.nba.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.stti.nba.entity.Player;
import com.stti.nba.entity.Team;
import com.stti.nba.errors.dataexceptions.InvalidArgumentException;
import com.stti.nba.errors.dataexceptions.TeamAlreadyExistsException;
import com.stti.nba.errors.dataexceptions.TeamNotFoundException;

@ExtendWith(MockitoExtension.class)
public class TeamTests {
    @Mock
    JdbcTemplate jdbcTemplate;

    @Mock
    PlayerDAO playerDAO;

    TeamDAO teamDAO;
    Team team1 = new Team();
    Team team2 = new Team();
    List<Team> teams;

    @BeforeEach
    void setup(){
        teamDAO = new TeamDAO();
        ReflectionTestUtils.setField(teamDAO, "jdbcTemplate", jdbcTemplate);
        ReflectionTestUtils.setField(teamDAO, "playerDAO", playerDAO);
        team1.setId(1);
        team1.setName("GSW");
        team1.setCity("SF");
 
        team2.setId(2);
        team2.setName("LAL");
        team2.setCity("LA");

        teams = List.of(team1,team2);
    }
    
    @Test
    public void testGetTeams_Success() {
        Mockito.when(jdbcTemplate.query(ArgumentMatchers.anyString(), ArgumentMatchers.any(TeamRowMapper.class))).thenReturn(teams);

        List<Team> resultTeams = teamDAO.getAllTeams();
        assertEquals(2,resultTeams.size());
        assertAll("resultTeams",
            () -> {
                Team firstTeam = resultTeams.get(0);
                Team secondTeam = resultTeams.get(1);
                assertNotNull(firstTeam);
                assertAll("firstTeam",
                    () -> {
                        assertEquals("GSW", firstTeam.getName());
                        assertEquals("SF",firstTeam.getCity());
                    }
                );
                assertNotNull(secondTeam);
                assertAll("secondTeam",
                    () -> {
                        assertEquals("LAL", secondTeam.getName());
                        assertEquals("LA",secondTeam.getCity());
                    }
                );
            }
        );
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testGetTeam_Success () {
        Mockito.when(jdbcTemplate.queryForObject(anyString(), ArgumentMatchers.any(Object[].class), ArgumentMatchers.any(TeamRowMapper.class))).thenReturn(team1);
        Mockito.when(playerDAO.getPlayersByTeamId(ArgumentMatchers.anyInt())).thenReturn(new ArrayList<Player>());

        Team team = teamDAO.getTeamByID(1);
        assertEquals(team1.getName(), team.getName());
        assertEquals(team1.getCity(), team.getCity());
    }

    @Test
    public void testGetTeam_Failure() {
        Mockito.when(jdbcTemplate.queryForObject(anyString(), ArgumentMatchers.any(Object[].class), ArgumentMatchers.any(TeamRowMapper.class))).thenThrow(new EmptyResultDataAccessException(0));

        assertThrows(TeamNotFoundException.class, () -> teamDAO.getTeamByID(28));
    }

    @Test
    public void testCreateTeam_Success () {
        Mockito.when(jdbcTemplate.update(anyString(), anyString(), anyString(), anyString())).thenReturn(1);

        assertEquals(1, teamDAO.createTeam("Utah Jazz", "Salt Lake City", "Will Hardy"));
    }

    @Test
    public void testCreateTeam_Failure () {
        Mockito.when(jdbcTemplate.update(anyString(), anyString(), anyString(), anyString())).thenThrow(new RuntimeException());

        assertThrows(TeamAlreadyExistsException.class, () -> teamDAO.createTeam("GSW", "", ""));
    }

    @Test
    public void testUpdateTeam_InvalidArgs () {
        assertThrows(InvalidArgumentException.class, () -> teamDAO.updateTeam(0, null, null, null));
    }

    @Test
    public void testUpdateTeam_NotFound () {
        Mockito.when(jdbcTemplate.update(anyString(), ArgumentMatchers.any(Object[].class))).thenThrow(new RuntimeException());

        assertThrows(TeamNotFoundException.class, () -> teamDAO.updateTeam(28, "", "", ""));
    }

    @Test
    public void testUpdateTeam_Success () {
        Mockito.when(jdbcTemplate.update(anyString(), ArgumentMatchers.any(Object[].class))).thenReturn(1);

        assertEquals(1, teamDAO.updateTeam(1, "Warriors", "SJ", "Mark Jackson"));
    }

    @Test
    public void deleteTeam_Success(){
        Mockito.when(jdbcTemplate.queryForObject(anyString(), ArgumentMatchers.any(Object[].class), ArgumentMatchers.any(TeamRowMapper.class))).thenReturn(team1);
        Mockito.when(playerDAO.getPlayersByTeamId(ArgumentMatchers.anyInt())).thenReturn(new ArrayList<Player>());
        Mockito.when(playerDAO.deletePlayersOnTeam(ArgumentMatchers.anyInt())).thenReturn(new ArrayList<Player>());
        Mockito.when(jdbcTemplate.update(anyString(), ArgumentMatchers.any(Object[].class))).thenReturn(1);

        Team t = teamDAO.deleteTeam(1);
        assertEquals(t.getName(), team1.getName());
    }

    @Test
    public void deleteTeam_Failure () {
        Mockito.when(jdbcTemplate.queryForObject(anyString(), ArgumentMatchers.any(Object[].class), ArgumentMatchers.any(TeamRowMapper.class))).thenThrow(new EmptyResultDataAccessException(0));

        assertThrows(TeamNotFoundException.class, () -> teamDAO.deleteTeam(29));
    }

}
