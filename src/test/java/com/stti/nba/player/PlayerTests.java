package com.stti.nba.player;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import com.stti.nba.entity.Player;
import com.stti.nba.repository.PlayerDAO;
import com.stti.nba.repository.PlayerRowMapper;

@JdbcTest
@Import(PlayerDAO.class)
@RunWith(MockitoJUnitRunner.class)
public class PlayerTests {
    
    @Mock
    private JdbcTemplate jdbcTemplateMock;

    @InjectMocks
    private PlayerDAO playerDAO;

    @Test
    public void testGetPlayerByPlayerId_Success() {
        // Define mock behavior for successful query
        // Player mockPlayer = new Player();
        // mockPlayer.setName("Stephen Curry");
        // mockPlayer.setAge(35);

        // when(jdbcTemplateMock.queryForObject(("SELECT * FROM PLAYER WHERE id = ?"), Object[].class, new PlayerRowMapper(), 1)).thenReturn(mockPlayer);

        // // Test getPlayerByPlayerId method
        // Player player = playerDAO.getPlayerByPlayerId(1);

        // // Verify that correct SQL queries were executed
        // verify(jdbcTemplateMock).queryForObject(eq("SELECT COUNT(id) FROM PLAYER"), eq(Integer.class));
        // verify(jdbcTemplateMock).queryForObject(eq("SELECT * FROM PLAYER WHERE id = ?"), any(Object[].class), eq(new PlayerRowMapper()), eq(1));

        // // Assert that the method returns the expected Player object
        // assertNotNull(player);
        // // Add more assertions as needed
    }

}