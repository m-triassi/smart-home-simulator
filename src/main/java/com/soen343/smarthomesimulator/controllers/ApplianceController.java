package com.soen343.smarthomesimulator.controllers;


import com.soen343.smarthomesimulator.models.Appliance;
import com.soen343.smarthomesimulator.models.Home;
import com.soen343.smarthomesimulator.models.Zone;
import com.soen343.smarthomesimulator.services.ApplianceService;
import com.soen343.smarthomesimulator.services.HomeService;
import com.soen343.smarthomesimulator.services.ZoneService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ApplianceController {

    @Autowired
    ApplianceService applianceService;

    @Autowired
    HomeService homeService;

    @Autowired
    ZoneService zoneService;

    private Map<String, String> response;

    public ApplianceController() {
        this.response = new HashMap<String, String>();
        this.response.put("success", "true");
    }

    /**
     * @param homeId The Home who's appliances should be returned
     * @param zoneId The Zone who's appliances should be returned
     * @return
     */
    @GetMapping("/appliances")
    public List<Appliance> index(@RequestParam(value = "home_id", required = false) Long homeId,
                                 @RequestParam(value = "zone_id", required = false) Long zoneId) {
        if (zoneId != null) {
            return applianceService.findByZone(zoneId);
        }

        if (homeId != null) {
            return applianceService.findByHome(homeId);
        }

        return Collections.emptyList();
    }

    /**
     * Create an appliance by passing in the name, type of appliance, as well as, optionally, the home or zone id it
     * should belong to. If just a zone is passed the setter should resolve the home automatically.
     *
     * @param request JSON request body parsed into a map, can contain: type, name, home_id, zone_id
     * @return JSONObject
     */
    @PostMapping("/appliances/store")
    public JSONObject store(@RequestBody Map<String, String> request) {
        Appliance appliance = new Appliance(request.get("type"), request.get("name"));

        if (request.get("home_id") != null) {
            Long homeId = Long.parseLong(request.get("home_id"));
            Home home = homeService.findById(homeId);
            appliance.setHome(home);
        }

        if (request.get("zone_id") != null) {
            Long zoneId = Long.parseLong(request.get("zone_id"));
            Zone zone = zoneService.findById(zoneId);
            appliance.setZone(zone);
        }

        applianceService.save(appliance);
        return new JSONObject(this.response);
    }

    @PostMapping("/appliances/update")
    public JSONObject update(@RequestParam(value = "appliance_id") Long applianceId,
                             @RequestParam(value = "state", required = false) Integer state,
                             @RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "zone_id", required = false) Long zoneId) {

        Appliance appliance = applianceService.findById(applianceId);

        if (state != null) {
            appliance.setState(state);
        }

        if (name != null) {
            appliance.setName(name);
        }

        if (zoneId != null) {
            Zone zone = zoneService.findById(zoneId);
            appliance.setZone(zone);
        }

        applianceService.save(appliance);
        return new JSONObject(this.response);
    }
}
