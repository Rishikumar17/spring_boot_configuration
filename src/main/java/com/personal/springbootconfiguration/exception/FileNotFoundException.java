/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.personal.springbootconfiguration.exception;

/**
 *
 * @author Rishi Kumar
 */
public class FileNotFoundException extends RuntimeException {
    
    public FileNotFoundException(String message) {
        super(message);
    }
    public FileNotFoundException(String message, Throwable cause) {
        super(message,cause);
    }
}
