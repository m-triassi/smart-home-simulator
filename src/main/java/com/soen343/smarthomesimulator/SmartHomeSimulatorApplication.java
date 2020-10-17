package com.soen343.smarthomesimulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SmartHomeSimulatorApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SmartHomeSimulatorApplication.class, args);
    }

}
