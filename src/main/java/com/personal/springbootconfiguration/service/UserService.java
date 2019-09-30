/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.personal.springbootconfiguration.service;

import com.personal.springbootconfiguration.dao.AbstractDAO;
import com.personal.springbootconfiguration.model.Users;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rishi Kumar
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UserService extends AbstractDAO<Users>{
   
   public Users userRegister(Users users){
       return saveOrUpdate(users);
   }

    public Users userLogin(String username, String password) {
        return findOne("Users.findByUserNamePassword",getParametersObjectArray(username,password));
    }
    
}
