package com.soen343.smarthomesimulator.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import net.minidev.json.JSONObject;

@RestControllerAdvice
public class ActionExceptionHandler {
    
    @ExceptionHandler(UnauthorizedActionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public JSONObject handleActionExceptionHandler(UnauthorizedActionException ex){
        
        Map<String, String> response = new HashMap<String, String>();
        response.put("message", "You do not have the permission to perform that action");

        return new JSONObject(response);
    }
}
