package com.soen343.smarthomesimulator.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "homes")
public class Home {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Integer outside_temp;

    private Timestamp date;

    @Column(name = "security_level")
    private String securityLevel;

    @Column(name = "auto_mode")
    private int autoMode;

    @Column(name = "simulation_state")
    private int simulationState;

    public static final String SECURITY_OFF = "off";

    public static final String SECURITY_ARMED = "armed";

    public Home() {
        this.name = "Generic Home";
        this.date = new Timestamp(System.currentTimeMillis());
        this.outside_temp = 21;
    }

    public Home(String name) {
        this.name = name;
        this.date = new Timestamp(System.currentTimeMillis() - 18000000);
        this.outside_temp = 21;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOutside_temp() {
        return outside_temp;
    }

    public void setOutside_temp(Integer outside_temp) {
        this.outside_temp = outside_temp;
    }

    public Timestamp getDate() {
        return date;
    }

    // remove 1.8e+7 milliseconds from date which is 5hours; when displaying the time in javascript it uses UTC and adds 5hours
    public void setDate(Timestamp date) {
        date.setTime(date.getTime() - 18000000);
        this.date = date;
    }

    public String getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(String securityLevel) {
        this.securityLevel = securityLevel;
    }

    public int getAutoMode() {
        return autoMode;
    }

    public void setAutoMode(int autoMode) {
        this.autoMode = autoMode;
    }

    public int getSimulationState() {
        return simulationState;
    }

    public void setSimulationState(int simulationState) {
        this.simulationState = simulationState;
    }
}
