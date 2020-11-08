package com.soen343.smarthomesimulator.controllers;

import com.soen343.smarthomesimulator.models.Home;
import com.soen343.smarthomesimulator.models.Opening;
import com.soen343.smarthomesimulator.models.User;
import com.soen343.smarthomesimulator.models.Zone;
import com.soen343.smarthomesimulator.services.HomeService;
import com.soen343.smarthomesimulator.services.OpeningService;
import com.soen343.smarthomesimulator.services.UserService;
import com.soen343.smarthomesimulator.services.ZoneService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class HomeController
 * <p>
 * Controller for the Home component. Contains the endpoints that perform logic on the home entity.
 */
@RestController
public class HomeController {

    @Autowired
    HomeService homeService;

    @Autowired
    UserService userService;

    @Autowired
    OpeningService openingService;

    @Autowired
    ZoneService zoneService;

    private Map<String, String> response;

    public HomeController() {
        this.response = new HashMap<String, String>();
        this.response.put("success", "true");
    }

    /**
     * POST endpoint to <code>/home/store</code>
     * <p>
     * This endpoint creates a new Home entity and saves it to the database.
     *
     * @param name Defines the name of the home being saved.
     * @return Response containing the operation's status.
     */
    @PostMapping("/home/store")
    public JSONObject store(@RequestParam(value = "name") String name) {
        homeService.save(new Home(name));
        return new JSONObject(this.response);
    }

    @PostMapping("/home/update")
    public JSONObject update(@RequestParam(value = "id") Long id,
                             @RequestParam(value = "temperature", required = false) Integer temperature,
                             @RequestParam(value = "date", required = false) String date,
                             @RequestParam(value = "security_level", required = false) String securityLevel,
                             @RequestParam(value = "auto_mode", required = false) Integer autoMode,
                             @RequestParam(value = "simulation_state", required = false) Integer simulationState) {
        Home home = homeService.findById(id);

        if (temperature != null) {
            home.setOutside_temp(temperature);
        }

        if (date != null) {
            home.setDate(Timestamp.valueOf(date));
        }

        if (securityLevel != null) {
            boolean armed = this.armAlarm(home, securityLevel);
            if (!armed) {
                this.response.put("message", "Alarm cannot be engaged because there are still users present in the home.");
            }
        }
        if (autoMode != null) {
            home.setAutoMode(autoMode);
        }

        if (simulationState != null) {
            home.setSimulationState(simulationState);
        }

        homeService.save(home);
        return new JSONObject(this.response);
    }

    private boolean armAlarm(Home home, String securityLevel) {
        List<User> users = userService.findAll();
        int state;

        if (securityLevel.equals("armed")) {
            state = -1;
        } else {
            state = 0;
        }

        for (User user : users) {
            if (user.getHome().getId().equals(home.getId()) && user.getZone().getId() != 0) {
                return false;
            }
        }

        for (Zone zone : zoneService.findByHome(home.getId())) {
            for (Opening opening : openingService.findByZone(zone.getId())) {
                opening.setState(state);
                openingService.save(opening);
            }
        }

        home.setSecurityLevel(securityLevel);
        homeService.save(home);
        return true;
    }

}
