package com.stti.nba.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.stti.nba.entity.Player;
import com.stti.nba.errors.dataexceptions.InvalidArgumentException;
import com.stti.nba.errors.dataexceptions.PlayerAlreadyExistsException;
import com.stti.nba.errors.dataexceptions.PlayerNotFoundException;


@ExtendWith(MockitoExtension.class)
public class PlayerTests {

    @Mock
    JdbcTemplate jdbcTemplate;

    PlayerDAO playerDAO;
    Player p1;
    Player p2;
    Player p3;
    Player p4;
    Player p5;

    @BeforeEach
    public void setUp() {
        playerDAO = new PlayerDAO();
        ReflectionTestUtils.setField(playerDAO, "jdbcTemplate", jdbcTemplate);

        p1 = new Player();
        p1.setName("Steph Curry");
        p1.setPosition("GUARD");
        p1.setId(1);
        p1.setTeamId(1);

        p2 = new Player();
        p2.setName("Draymond Green");
        p2.setPosition("FORWARD");
        p2.setId(2);
        p2.setTeamId(2);

        p3 = new Player();
        p3.setName("Klay Thompson");
        p3.setPosition("GUARD");
        p3.setId(3);
        p3.setTeamId(1);

        p4 = new Player();
        p4.setName("Iggy");
        p4.setPosition("CENTER");
        p4.setId(3);
        p4.setTeamId(1);

        p5 = new Player();
        p5.setName("Lebron James");
        p5.setPosition("GUARD");
        p5.setId(3);
        p5.setTeamId(2);
    }

    @Test
    public void testGetPlayers_Success () {

        List<Player> players = List.of(p1, p2, p3);
        Mockito.when(jdbcTemplate.query(
            ArgumentMatchers.anyString(),
            ArgumentMatchers.any(PlayerRowMapper.class)
        )).thenReturn(players);

        List<Player> playerList = playerDAO.getAllPlayers();

        assertEquals(3, playerList.size());
        assertAll("playerList", () -> {
            Player player1 = playerList.get(0);
            Player player2 = playerList.get(1);
            Player player3 = playerList.get(2);
            assertNotNull(player1);
            assertAll("p1", () -> {
                assertEquals(p1.getName(), player1.getName());
                assertEquals(p1.getPosition(), player1.getPosition());
            });
            assertNotNull(player2);
            assertAll("player2", () -> {
                assertEquals(player2.getName(), p2.getName());
                assertEquals(player2.getPosition(), p2.getPosition());
            });
            assertNotNull(player3);
            assertAll("player3", () -> {
                assertEquals(player3.getName(), p3.getName());
                assertEquals(player3.getPosition(), p3.getPosition());
            });
        });
    }

    @Test
    public void testGetPlayer_Success () {
        Mockito.when(jdbcTemplate.queryForObject(ArgumentMatchers.anyString(), ArgumentMatchers.any(Object[].class), ArgumentMatchers.any(PlayerRowMapper.class))).
        thenReturn(p1);

        Player player1 = playerDAO.getPlayerByPlayerId(1);
        assertEquals(p1.getName(), player1.getName());
        assertEquals(p1.getPosition(), player1.getPosition());
    }

    @Test
    public void testGetPlayer_Failure() {

        Mockito.when(jdbcTemplate.queryForObject(ArgumentMatchers.anyString(), ArgumentMatchers.any(Object[].class), ArgumentMatchers.any(PlayerRowMapper.class)))
            .thenThrow(new EmptyResultDataAccessException(0));

        assertThrows(PlayerNotFoundException.class, () -> playerDAO.getPlayerByPlayerId(27));
    }

    @Test
    public void testGetPlayersOnTeam_Success () {
        List<Player> playersOnTeam = List.of(p5);
        Mockito.when(jdbcTemplate.query(ArgumentMatchers.anyString(), ArgumentMatchers.any(Object[].class), ArgumentMatchers.any(PlayerRowMapper.class))).thenReturn(playersOnTeam);

        List<Player> returnedPlayerList = playerDAO.getPlayersByTeamId(2);
        assertEquals(1, returnedPlayerList.size());
        assertAll("returnedPlayerList", () -> {
            Player player1 = returnedPlayerList.get(0);
            assertNotNull(player1);
            assertAll("player1", () -> {
                assertEquals(player1.getName(), p5.getName());
                assertEquals(player1.getPosition(), p5.getPosition());
            });
        });
    }

    @Test
    public void testCreatePlayer_Success() {
        Mockito.when(jdbcTemplate.update(anyString(), anyInt(), anyString(), anyString())).thenReturn(1);
        assertEquals(playerDAO.createPlayer(2, "Anthony Davis", "CENTER"), 1);
    }

    @Test
    public void testCreatePlayer_Failure() {
        Mockito.when(jdbcTemplate.update(anyString(), anyInt(), anyString(), anyString())).thenThrow(new DuplicateKeyException(null));
        assertThrows(PlayerAlreadyExistsException.class, () -> playerDAO.createPlayer(1, "Steph Curry", ""));
    }

    @Test
    public void testUpdatePlayer_AgeFailure () {
        assertThrows(InvalidArgumentException.class, () -> playerDAO.updatePlayer(1, "Steph Curry", -40));
    }

    @Test
    public void testUpdatePlayer_InvalidArgs () {
        assertThrows(InvalidArgumentException.class, () -> playerDAO.updatePlayer(1, null, null));
    }

    @Test
    public void testUpdatePlayer_PlayerNotExist () {
        Mockito.when(jdbcTemplate.update(ArgumentMatchers.anyString(), ArgumentMatchers.any(Object[].class))).thenThrow(new RuntimeException());
        assertThrows(PlayerNotFoundException.class, () -> playerDAO.updatePlayer(27, "", 30));
    }

    @Test
    public void testUpdatePlayer_Success () {
        Mockito.when(jdbcTemplate.update(ArgumentMatchers.anyString(), ArgumentMatchers.any(Object[].class))).thenReturn(1);
        int count = playerDAO.updatePlayer(1, "Canon Curry", 31);
        assertEquals(1, count);
    }

    @Test
    public void testDeletePlayer_Success () {
        Mockito.when(jdbcTemplate.queryForObject(anyString(), ArgumentMatchers.any(Object[].class), ArgumentMatchers.any(PlayerRowMapper.class))).thenReturn(p1);
        Mockito.when(jdbcTemplate.update(anyString(), ArgumentMatchers.any(Object[].class))).thenReturn(1);
        Player p = playerDAO.deletePlayer(1);
        assertEquals(p.getName(), p1.getName());
    }

    @Test
    public void testDeletePlayer_Failure () {
        Mockito.when(jdbcTemplate.queryForObject(anyString(), ArgumentMatchers.any(Object[].class), ArgumentMatchers.any(PlayerRowMapper.class))).thenThrow(new EmptyResultDataAccessException(0));
        assertThrows(PlayerNotFoundException.class, () -> playerDAO.deletePlayer(28));
    }

    @Test
    public void testDeletePlayersOnTeam_Success () {
        List<Player> players = List.of(p1, p3, p4);
        Mockito.when(jdbcTemplate.query(anyString(), ArgumentMatchers.any(Object[].class), ArgumentMatchers.any(PlayerRowMapper.class))).thenReturn(players);
        Mockito.when(jdbcTemplate.update(anyString(), ArgumentMatchers.any(Object[].class))).thenReturn(1);
        List<Player> returnedPlayers = playerDAO.deletePlayersOnTeam(1);
        assertEquals(3, returnedPlayers.size());
        assertAll("playerList", () -> {
            Player player1 = returnedPlayers.get(0);
            Player player2 = returnedPlayers.get(1);
            Player player3 = returnedPlayers.get(2);
            assertNotNull(player1);
            assertAll("p1", () -> {
                assertEquals(p1.getName(), player1.getName());
                assertEquals(p1.getPosition(), player1.getPosition());
            });
            assertNotNull(player2);
            assertAll("p3", () -> {
                assertEquals(p3.getName(), player2.getName());
                assertEquals(p3.getPosition(), player2.getPosition());
            });
            assertNotNull(player3);
            assertAll("p4", () -> {
                assertEquals(p4.getName(), player3.getName());
                assertEquals(p4.getPosition(), player3.getPosition());
            });
        });
    }
}