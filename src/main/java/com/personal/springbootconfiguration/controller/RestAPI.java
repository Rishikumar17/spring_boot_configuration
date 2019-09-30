package com.personal.springbootconfiguration.controller;

import com.personal.springbootconfiguration.component.UserComponent;
import io.jsonwebtoken.Claims;
import javax.servlet.http.HttpServletRequest;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Rishi Kumar
 */
@RestController
@RequestMapping("/api")
public class RestAPI {
    @Autowired
    UserComponent userComponent;
    @Autowired
    Environment env;
    
    @PostMapping("/getData")
    public JSONObject getdata(HttpServletRequest req) {
        
        Claims claims=(Claims) req.getAttribute("claims");
        JSONObject joObject= new JSONObject();
        joObject.put("id", claims.get("id"));
        joObject.put("username", claims.get("username"));
        joObject.put("Subject", claims.getSubject());
        joObject.put("iat", claims.getIssuedAt());
        return joObject;
    }

}
