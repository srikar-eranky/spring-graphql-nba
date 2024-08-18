package com.stti.nba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.stti.nba.entity.UserInput;
import com.stti.nba.repository.UserDAO;

@Controller
public class UserController {
    
    @Autowired
    UserDAO userDAO;

    @MutationMapping(name = "createUser")
    public int createUser(@Argument UserInput userInput) {
        return userDAO.createUser(userInput);
    }
}
