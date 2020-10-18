package com.soen343.smarthomesimulator.controllers;

import com.soen343.smarthomesimulator.models.Home;
import com.soen343.smarthomesimulator.models.User;
import com.soen343.smarthomesimulator.models.Zone;
import com.soen343.smarthomesimulator.services.HomeService;
import com.soen343.smarthomesimulator.services.UserService;
import com.soen343.smarthomesimulator.services.ZoneService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/user/login")
    public User getUser(@RequestParam(value = "email") String email,
                        @RequestParam(value = "password") String password) {

        //TODO: instead of returning the user create, implement persistence for user login
        return userService.findUserByCredentials(email, password);
    }

    @GetMapping("/user/current")
    public User currentUser() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass());

        // case when no user is logged in, the principal is set to string 'anonymousUser' by default;
        // if a user is logged in, cast the principal to UserDetailsModel
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass() == String.class)
            return null;
        else{
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            System.out.println(currentUser.getUsername());
            return currentUser;
        }
    }

    @PostMapping(value = "/user/store")
    public JSONObject store(@RequestParam(value = "name") String name,
                        @RequestParam(value = "email") String email,
                        @RequestParam(value = "password") String password,
                        @RequestParam(value = "isParent") Boolean isParent,
                        @RequestParam(value = "isChild") Boolean isChild,
                        @RequestParam(value = "isGuest") Boolean isGuest) {

        // TODO: Input Validation

        password = this.passwordEncoder().encode(password);
        String role;
        if(isParent){
            role = "ROLE_PARENT";
        }
        else if(isChild){
            role = "ROLE_CHILD";
        }
        else{
            role="ROLE_USER";
        }

        if (userService.save(new User(name, email, password, role)) != null) {
            return new JSONObject(this.response);
        }

        this.response.put("success", "false");
        return new JSONObject(this.response);
    }


    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}