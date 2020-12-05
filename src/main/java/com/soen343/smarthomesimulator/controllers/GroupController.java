package com.soen343.smarthomesimulator.controllers;

import java.util.HashMap;
import java.util.Map;

import com.soen343.smarthomesimulator.models.Group;
import com.soen343.smarthomesimulator.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import antlr.collections.List;
import net.minidev.json.JSONObject;


@RestController
public class GroupController {
    @Autowired
    GroupService groupService;

    private Map<String, String> response;

    public GroupController() {
        this.response = new HashMap<String, String>();
        this.response.put("success", "true");
    }

    @PostMapping("group/update")
    public JSONObject update(@RequestParam(value = "group_id") Long groupId,
                             @RequestParam(value = "temperature", required = false) Integer temperature,
                             @RequestParam(value = "temperatureWinter", required = false) Integer temperatureWinter,
                             @RequestParam(value = "temperatureSummer", required = false) Integer temperatureSummer,
                             @RequestParam(value = "heating", required = false) Boolean heating){
        Group group = groupService.findById(groupId);

        if(temperature != null){
            group.setTemperature(temperature);
        }

        if(temperatureWinter != null){
            group.setTemperatureWinter(temperatureWinter);
        }

        if(temperatureSummer != null){
            group.setTemperatureSummer(temperatureSummer);
        }

        if(heating != null){
            group.setHeating(heating);
        }

        return new JSONObject(this.response);
    }


}
