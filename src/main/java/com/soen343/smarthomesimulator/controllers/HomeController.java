package com.soen343.smarthomesimulator.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jdk.jfr.StackTrace;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;

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

    /**
     * Modify the parameters of a home / the context of the simulation
     * @param id ID of the house to modify (Required)
     * @param temperature Temperature of the enviornment outside of the home
     * @param date the date and time the simulation is currently at
     * @param startTime Unix Epoch when the simulation was turned on
     * @param multiplier the speed at which time was moving
     * @param securityLevel the security level that the home should be set to
     * @param autoMode whether appliances like lights should automatically turn on when a user enters a zone in the home
     * @param simulationState State of the sumulation, 0/1 for off/on
     * @return JSONObject
     */
    @PostMapping("/home/update")
    public JSONObject update(@RequestParam(value = "id") Long id,
                             @RequestParam(value = "temperature", required = false) Integer temperature,
                             @RequestParam(value = "date", required = false) Timestamp date,
                             @RequestParam(value = "start_time", required = false) Long startTime,
                             @RequestParam(value = "multiplier", required = false) Integer multiplier,
                             @RequestParam(value = "security_level", required = false) String securityLevel,
                             @RequestParam(value = "auto_mode", required = false) Integer autoMode,
                             @RequestParam(value = "simulation_state", required = false) Integer simulationState) {
        Home home = homeService.findById(id);

        if (temperature != null) {
            home.setOutside_temp(temperature);
        }

        if (date != null) {
            home.setDate(date);
        }

        if (startTime != null) {
            long stopTime = System.currentTimeMillis();
            long incrementedEpoch = 18000000 + home.getDate().getTime() + (stopTime - startTime) * multiplier;
            String formatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(incrementedEpoch));
            Timestamp incrementedDate = Timestamp.valueOf(formatted);
            home.setDate(incrementedDate);
            this.response.put("date", home.getDate().toString());
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

    /**
     * Helper function that checks no users are present in the home as well as locking all the doors in the home
     * @param home Home to have its alarm armed
     * @param securityLevel level the home is being set too
     * @return boolean, on fail (a user is still present) returns false
     */
    private boolean armAlarm(Home home, String securityLevel) {
        List<User> users = userService.findAll();
        int state;

        if (securityLevel.equals(Home.SECURITY_ARMED)) {
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
