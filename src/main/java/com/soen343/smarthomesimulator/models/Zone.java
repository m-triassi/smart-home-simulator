package com.soen343.smarthomesimulator.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "zones")
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Integer temperature = 21;

    @OneToMany(mappedBy = "zone", cascade = CascadeType.ALL)
    private List<Opening> openings;

    @ManyToOne
    @JoinColumn(name = "home_id")
    private Home home;

    public Zone() {
        this.name = "Generic Zone";
    }

    public Zone(String name, Home home) {
        this.name = name;
        this.home = home;
    }

    public Zone(String name) {
        this.name = name;
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

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public List<Opening> getOpenings() {
        return openings;
    }

    public void setOpenings(List<Opening> opening) {
        this.openings = opening;
    }
}
