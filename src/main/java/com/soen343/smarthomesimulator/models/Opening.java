package com.soen343.smarthomesimulator.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

// Windows, Doors, etc...
@Entity
@Table(name = "openings")
public class Opening {

    private static final int STATE_OPEN = 1;

    private static final int STATE_CLOSED = 0;

    private static final int STATE_LOCKED = -1;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String type;

    private Integer state = 0;

    @ManyToOne
    @JsonIgnoreProperties("openings")
    private Zone zone;
    
    private String connected_zone;

    public Opening() {
        this.type = "door";
    }

    public Opening(String type, Zone zone) {
        this.type = type;
        this.zone = zone;
    }

    public Opening(String type, Zone zone, String connected_zone) {
        this.type = type;
        this.zone = zone;
        this.connected_zone = connected_zone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        // TODO: State Validation - constrain to one of the enums
        this.state = state;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }
}
