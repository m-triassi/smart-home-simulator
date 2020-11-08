package com.soen343.smarthomesimulator.controllers;

import com.soen343.smarthomesimulator.models.Home;
import com.soen343.smarthomesimulator.services.HomeService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jdk.jfr.StackTrace;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;
/**
 * Class HomeController
 * 
 * Controller for the Home component. Contains the endpoints that perform logic on the home entity.
 */
@RestController
public class HomeController {

    @Autowired
    HomeService homeService;

    private Map<String, String> response;

    public HomeController() {
        this.response = new HashMap<String, String>();
        this.response.put("success", "true");
    }

    /**
     * POST endpoint to <code>/home/store</code>
     * 
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
                             @RequestParam(value = "dateToBeIncremented", required = false) String dateToBeIncremented) {
        Home home = homeService.findById(id);

        if (temperature != null) {
            home.setOutside_temp(temperature);
        }

        if (date != null) {
            home.setDate(Timestamp.valueOf(date));
        }

        if (dateToBeIncremented != "") {
            long inc = 1000;
            dateToBeIncremented = dateToBeIncremented.replace("T", " ");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date sdfToDate = null;
            try{
                sdfToDate = sdf.parse(dateToBeIncremented);
                long millis = sdfToDate.getTime();
                Timestamp incrementedDate = new Timestamp(millis + inc);
                home.setDate(incrementedDate);
            }catch(Exception e){
                System.err.print(e.toString());
            }
        }        

        homeService.save(home);
        return new JSONObject(this.response);
    }

}
