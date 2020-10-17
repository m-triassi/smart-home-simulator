package com.soen343.smarthomesimulator;

import com.soen343.smarthomesimulator.repositories.UserRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class SmartHomeSimulatorApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SmartHomeSimulatorApplication.class, args);
    }

}
