package de.cyzetlc.roadsystem.utils.roadtraffic.vehicles;

import java.awt.*;

public class Motorcycle extends Vehicle {
    @Override
    public void initialize() {
        this.setSpeed(50);
        this.setColor(Color.GREEN);
    }
}
