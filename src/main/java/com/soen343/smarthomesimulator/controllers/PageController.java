package com.soen343.smarthomesimulator.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/signup")
    public String signup(){
        return "signup";
    }

    @RequestMapping("/edit/profile")
    public String editProfile(){
        return "editProfile";
    }

    @RequestMapping("/edit/home")
    public String editHome(){
        return "editHome";
    }

}
