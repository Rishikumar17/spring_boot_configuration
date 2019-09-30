/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.personal.springbootconfiguration.component;

import com.personal.springbootconfiguration.model.Users;
import com.personal.springbootconfiguration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Rishi Kumar
 */
@Component
public class UserComponent {

    @Autowired
    UserService userService;

    public Users register(Users user) {
        return userService.userRegister(user);
    }

    public Users login(String username, String password) {
        return userService.userLogin(username, password);
    }

}
