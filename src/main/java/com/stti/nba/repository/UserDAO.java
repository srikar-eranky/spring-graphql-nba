package com.stti.nba.repository;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stti.nba.entity.UserInput;
import com.stti.nba.errors.dataexceptions.UserAlreadyExistsException;

@Repository
public class UserDAO {

    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int createUser(UserInput userInput) {
       String username = userInput.getUsername();
       String password = userInput.getPassword();

       try {
            return jdbcTemplate.update("INSERT INTO USER (username, password) VALUES (?, ?)", username, password); 
        } catch (Exception e) {
            throw new UserAlreadyExistsException("user already exists");
        }
    }
}
