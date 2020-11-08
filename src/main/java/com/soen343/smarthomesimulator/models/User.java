
package com.soen343.smarthomesimulator.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.soen343.smarthomesimulator.observers.UserObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.Collections;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    private String name;

    private String role;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ManyToOne
    @JoinColumn(name = "home_id")
    private Home home;

    // If zone is null, user is outside the home they belong to
    @ManyToOne
    @JoinColumn(name = "zone_id")
    private Zone zone;

    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    public static final String ROLE_PARENT = "ROLE_PARENT";

    public static final String ROLE_USER = "ROLE_USER";

    public static final String ROLE_CHILD = "ROLE_CHILD";

    private static final String ROLE_STRANGER = "ROLE_STRANGER";

    private transient PropertyChangeSupport support;

    public User() {
        this.registerObserver();
    }

    public User(Long id, String name, String email, String password) {
        this.registerObserver();

        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = ROLE_USER;
    }

    public User(String name, String email, String password) {
        this.registerObserver();

        this.email = email;
        this.name = name;
        this.password = password;
        this.role = ROLE_USER;
    }

    public User(String name, String email, String password, String role) {
        this.registerObserver();

        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public User(User user) {
        this.registerObserver();

        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.id = user.getId();
        this.role = user.getRole();
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public Zone getZone() {
        if (zone == null) {
            Zone outside = new Zone("Outside");
            outside.setAppliances(Collections.emptyList());
            outside.setId(Long.valueOf(0));
            return outside;
        }

        return zone;
    }

    public void setZone(Zone zone) {
        this.support.firePropertyChange("zone", this.zone, zone);
        this.zone = zone;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private void registerObserver() {
        this.support = new PropertyChangeSupport(this);
    }
}