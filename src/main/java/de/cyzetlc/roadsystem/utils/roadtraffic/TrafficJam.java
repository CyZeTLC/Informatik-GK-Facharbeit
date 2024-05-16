package de.cyzetlc.roadsystem.utils.roadtraffic;

import de.cyzetlc.roadsystem.utils.roadtraffic.vehicles.Vehicle;
import lombok.Getter;

import java.util.LinkedList;

@Getter
public class TrafficJam {
    private final Intersection intersection;
    private final LinkedList<Vehicle> vehicles;

    public TrafficJam(Intersection intersection) {
        this.intersection = intersection;
        this.vehicles = new LinkedList<>();
    }
}
