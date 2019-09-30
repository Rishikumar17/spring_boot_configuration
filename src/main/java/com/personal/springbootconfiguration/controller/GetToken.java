/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.personal.springbootconfiguration.controller;

import com.personal.springbootconfiguration.component.UserComponent;
import com.personal.springbootconfiguration.dto.userDTO;
import com.personal.springbootconfiguration.model.Users;
import com.personal.springbootconfiguration.security.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import javax.validation.Valid;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Rishi Kumar
 */
@RestController
@RequestMapping("/user")
public class GetToken {

    @Autowired
    UserComponent userComponent;

    @PostMapping("/login")
    public JSONObject getToken(@RequestParam String email, @RequestParam String password) {
        Users user = userComponent.login(email, password);
        JSONObject jobject = new JSONObject();
        if (user != null) {
            String username=user.getUserName();
            Integer userid=user.getUserId();
            
            String jwtToken = Jwts.builder()
                    .setSubject("JWT_Token")
                    .claim("username", username)
                    .claim("id", userid)
                    .setIssuedAt(new Date())
                    .signWith(SignatureAlgorithm.HS256, SecurityConstants.SECRET).compact();
            jobject.put("Status", "1");
            jobject.put("data", jwtToken);
                 
        } else {
            jobject.put("Status", "0");
            jobject.put("data", "User Name or password not correct");
        }
        return jobject;
    }
    
    @GetMapping("/Register")
    public void register(String username,String password){
        Users users = new Users();
        users.setUserName(username);
        users.setUserPassword(password);
        users.setIsDeleted(Boolean.FALSE);
        userComponent.register(users);
    }
}
