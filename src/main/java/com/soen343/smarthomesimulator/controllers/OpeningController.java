package com.soen343.smarthomesimulator.controllers;

import com.soen343.smarthomesimulator.models.Opening;
import com.soen343.smarthomesimulator.models.User;
import com.soen343.smarthomesimulator.services.OpeningService;
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
public class OpeningController {
    @Autowired
    OpeningService openingService;

    private Map<String, String> response;

    public OpeningController() {
        this.response = new HashMap<String, String>();
        this.response.put("success", "true");
    }

    /**
     *
     * @param zoneId
     * @param type
     * @return List
     */
    @GetMapping("/openings")
    public List<Opening> index(@RequestParam(value = "zone_id") Long zoneId,
                               @RequestParam(value = "type", required = false) String type) {
        if (type != null) {
            return openingService.findByZoneAndType(zoneId, type);
        } else {
            return openingService.findByZone(zoneId);
        }
    }

    /**
     * POST endpoint to <code>/openings/update</code>
     * <p>
     * Sets the state of a particular opening during the simulation.
     *
     * @param id    The id of the opening.
     * @param state The state to set the opening to.
     * @return The response status of the operation.
     */
    @PostMapping("/openings/update")
    public JSONObject update(@RequestParam(value = "id") Long id, @RequestParam(value = "state") Integer state) {
        Opening opening = openingService.findById(id);
        User current = new UserController().currentUser();

        if (current != null && (current.getZone().getId().equals(opening.getZone().getId()) || current.getRole().equals(User.ROLE_ADMIN))) {
            this.response.put("success", "");
            this.response.put("message", "You do not have permission to perform that action");
            return new JSONObject(this.response);
        }

        opening.setState(state);
        openingService.save(opening);
        return new JSONObject(this.response);
    }
}
