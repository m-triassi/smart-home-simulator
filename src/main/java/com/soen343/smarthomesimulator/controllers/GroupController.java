package com.soen343.smarthomesimulator.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.soen343.smarthomesimulator.models.Group;
import com.soen343.smarthomesimulator.models.Home;
import com.soen343.smarthomesimulator.models.Zone;
import com.soen343.smarthomesimulator.services.GroupService;
import com.soen343.smarthomesimulator.services.HomeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONObject;


@RestController
public class GroupController {
    @Autowired
    GroupService groupService;

    @Autowired
    HomeService homeService;

    private Map<String, String> response;

    public GroupController() {
        this.response = new HashMap<String, String>();
        this.response.put("success", "true");
    }

    @PostMapping("group/create")
    public JSONObject create(@RequestParam(value = "name") String name,
                             @RequestParam(value = "home_id") Long homeId,
                             @RequestParam(value = "temperature") Integer temperature,
                             @RequestParam(value = "winter") Integer temperatureWinter,
                             @RequestParam(value = "summer") Integer temperatureSummer,
                             @RequestParam(value = "heating") Boolean heating){

        Home home;

        if(homeService.exists(homeId)){
            home = homeService.findById(homeId);
        } else{
            this.response.put("home", "Home supplied does not exist");
            return new JSONObject(this.response);
        }

        if(groupService.save(new Group(name, home, temperature, temperatureWinter, temperatureSummer, heating)) != null){
            this.response.put("success", "true");
            return new JSONObject(this.response);
        }

        // maybe throw error instead of response and return JSONObject
        this.response.put("success", "false");
        return new JSONObject(this.response);
    }

    /**
     * Update a group
     * @param groupId Group entity ID 
     * @param homeId Home entity ID which Group belongs to
     * @param temperature Default temperature
     * @param temperatureWinter Default winter temperature
     * @param temperatureSummer Default Summer temperature
     * @param heating True if heating flase if cooling
     * @return JSONObject
     */
    @PostMapping("group/update")
    public JSONObject update(@RequestParam(value = "group_id") Long groupId,
                             @RequestParam(value = "home_id", required = false) Long homeId,
                             @RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "temperature", required = false) Integer temperature,
                             @RequestParam(value = "winter", required = false) Integer temperatureWinter,
                             @RequestParam(value = "summer", required = false) Integer temperatureSummer,
                             @RequestParam(value = "heating", required = false) Boolean heating){
        Group group = groupService.findById(groupId);

        if(name != null){
            group.setName(name);
        }

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

        if(homeId != null && homeService.exists(homeId)){
            Home home = homeService.findById(homeId);
            group.setHome(home);
        } else if (homeId != null) {
            this.response.put("home", "Home supplied does not exist");
        }

        groupService.save(group);
        return new JSONObject(this.response);
    }

    /**
     * List all zones in a group
     * @param groupId Group entity ID
     * @return List<Zone>
     */
    @GetMapping("group/zones")
    public List<Zone> list(@RequestParam(value = "group_id") Long groupId){
        return groupService.findByGroup(groupId);
    }
}
