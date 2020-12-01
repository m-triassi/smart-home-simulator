package com.soen343.smarthomesimulator.observers;

import com.soen343.smarthomesimulator.models.Appliance;
import com.soen343.smarthomesimulator.models.Home;
import com.soen343.smarthomesimulator.models.Zone;
import com.soen343.smarthomesimulator.services.ApplianceService;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

@Component
public class UserObserver implements PropertyChangeListener {

    @Autowired
    private ApplianceService applianceService;

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Zone zone = (Zone) evt.getNewValue();
        System.out.println(zone.getId());
        if (zone.home.getSecurityLevel().equals(Home.SECURITY_ARMED) && zone.getId() != 0) {
            new Exception("Alarm has been triggered. Please leave the home and disable the alarm.");
        } else if (zone.home.getAutoMode() == 1) {
            for (Appliance appliance: zone.getAppliances()) {
                if (appliance != null && appliance.getType().equals("light") && appliance.getState() != 1) {
                    appliance.setState(1);
                    applianceService.save(appliance);
                }
            }
        }
    }
}
