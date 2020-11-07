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

    public static final String SECURITY_OFF = "off";

    public static final String SECURITY_AWAY = "away";

    public Home() {
        this.name = "Generic Home";
        this.date = new Timestamp(System.currentTimeMillis());
        this.outside_temp = 21;
    }

    public Home(String name) {
        this.name = name;
        this.date = new Timestamp(System.currentTimeMillis());
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

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(String securityLevel) {
        this.securityLevel = securityLevel;
    }
}
