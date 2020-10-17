package com.soen343.smarthomesimulator.controllers;

import com.soen343.smarthomesimulator.models.Home;
import com.soen343.smarthomesimulator.models.User;
import com.soen343.smarthomesimulator.models.Zone;
import com.soen343.smarthomesimulator.services.HomeService;
import com.soen343.smarthomesimulator.services.UserService;
import com.soen343.smarthomesimulator.services.ZoneService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    HomeService homeService;

    @Autowired
    ZoneService zoneService;

    private Map<String, String> response;

    public UserController() {
        this.response = new HashMap<String, String>();
        this.response.put("success", "true");
    }

    @GetMapping("/users")
    public List<User> index() {
        return userService.findAll();
    }

    @PostMapping("/user/update")
    public JSONObject update(@RequestParam(value = "id") Long id,
                             @RequestParam(value = "home_id", required = false) Long homeId,
                             @RequestParam(value = "zone_id", required = false) Long zoneId) {
        // TODO: Update password, name, role

        User user = userService.findById(id);
        if (homeId != null && homeService.exists(homeId)) {
            Home home = homeService.findById(homeId);
            user.setHome(home);
        } else if (homeId != null) {
            this.response.put("home", "Home supplied does not exist");
        }

        if (zoneId != null && zoneService.exists(zoneId)) {
            Zone zone = zoneService.findById(zoneId);
            user.setZone(zone);
        } else if (zoneId != null) {
            this.response.put("zone", "Zone supplied does not exist");
        }

        userService.save(user);

        return new JSONObject(this.response);
    }

    @GetMapping("/user")
    public User show(@RequestParam(value = "id") Long id) {
        return userService.findById(id);
    }

    @PostMapping("/user/store")
    public JSONObject store(@RequestParam(value = "name") String name,
                        @RequestParam(value = "email") String email,
                        @RequestParam(value = "password") String password) {

        // TODO: Input Validation

        if (userService.save(new User(name, email, password)) != null) {
            return new JSONObject(this.response);
        }

        this.response.put("success", "false");
        return new JSONObject(this.response);
    }
}