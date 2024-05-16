package de.cyzetlc.roadsystem.utils;

import de.cyzetlc.roadsystem.RoadSystem;
import de.cyzetlc.roadsystem.gui.GuiScreen;
import de.cyzetlc.roadsystem.utils.roadtraffic.TrafficJam;
import de.cyzetlc.roadsystem.utils.roadtraffic.vehicles.Vehicle;
import lombok.Getter;

import java.awt.*;
import java.util.Iterator;

public class Runtime implements Runnable {
    private final GuiScreen screen;

    @Getter
    private static int carChanges = 0;

    public Runtime(GuiScreen screen) {
        this.screen = screen;
    }

    @Override
    public void run() {
        try {
            this.screen.draw();

            for (Vehicle vehicle : RoadSystem.getInstance().getVehicleHandler().getVehicles()) {
                TrafficJam tJam = RoadSystem.getInstance().getRoadHandler().getTrafficJamOfVehicle(vehicle);
                if (tJam != null) {
                    if (tJam.getVehicles().getFirst().equals(vehicle)) {
                        vehicle.drive();
                    }
                } else {
                    vehicle.drive();
                }

                for (Vehicle otherVehicle : RoadSystem.getInstance().getVehicleHandler().getVehicles()) {
                    if (vehicle != otherVehicle) {
                        if (vehicle.getNextTarget().equals(otherVehicle.getNextTarget())) {
                            TrafficJam trafficJam = RoadSystem.getInstance().getRoadHandler().getTrafficJamByIntersection(vehicle.getNextTarget());
                            if (trafficJam != null) {
                                trafficJam.getVehicles().add(vehicle);
                            } else {
                                trafficJam = new TrafficJam(vehicle.getNextTarget());
                                trafficJam.getVehicles().add(vehicle);

                                RoadSystem.getInstance().getRoadHandler().getTrafficJams().add(trafficJam);
                                RoadSystem.getInstance().getRoadHandler().setTrafficJamesCount(RoadSystem.getInstance().getRoadHandler().getTrafficJamesCount() + 1);
                            }
                            break;
                        }
                    }
                }
                vehicle.draw((Graphics2D) this.screen.getGraphics());
            }

            for (Iterator<TrafficJam> iterator = RoadSystem.getInstance().getRoadHandler().getTrafficJams().iterator(); iterator.hasNext(); ) {
                TrafficJam trafficJam = iterator.next();
                if (!trafficJam.getVehicles().isEmpty()) {
                    trafficJam.getVehicles().remove(0);
                }

                if (trafficJam.getVehicles().isEmpty()) {
                    iterator.remove();
                }
            }

            carChanges++;
        } catch (Exception e) {
            RoadSystem.getLogger().error("Stopping Runtime: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
