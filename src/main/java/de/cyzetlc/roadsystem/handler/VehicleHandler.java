package de.cyzetlc.roadsystem.handler;

import de.cyzetlc.roadsystem.RoadSystem;
import de.cyzetlc.roadsystem.utils.roadtraffic.Intersection;
import de.cyzetlc.roadsystem.utils.roadtraffic.vehicles.Car;
import de.cyzetlc.roadsystem.utils.roadtraffic.vehicles.Motorcycle;
import de.cyzetlc.roadsystem.utils.roadtraffic.vehicles.Vehicle;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Getter
public class VehicleHandler {
    private List<Vehicle> vehicles;

    public VehicleHandler() {
        this.vehicles = new LinkedList<>();
    }

    /**
     * The function generates a specified number of vehicles with a given percentage of cars and motorcycles.
     *
     * @param amount The `amount` parameter represents the total number of vehicles to be generated.
     * @param carPercentage The `carPercentage` parameter represents the percentage of cars you want to generate out of the
     * total number of vehicles. For example, if `amount` is 100 and `carPercentage` is 0.6, it means you want to generate
     * 60 cars and 40 motorcycles out of the
     */
    public void generateVehicles(int amount, double carPercentage) {
        List<Intersection> intersections = RoadSystem.getInstance().getRoadHandler().getIntersections();
        for (int i = 0; i < amount * carPercentage; i++) {
            Vehicle car = new Car();
            car.setLocation(intersections.get(new Random().nextInt(intersections.size())).getLocation().copy());
            car.update();
            vehicles.add(car);
        }

        for (int i = 0; i < amount - amount * carPercentage; i++) {
            Vehicle motorcycle = new Motorcycle();
            motorcycle.setLocation(intersections.get(new Random().nextInt(intersections.size())).getLocation().copy());
            motorcycle.update();
            vehicles.add(motorcycle);
        }
    }

    /**
     * The `getMotorcycles` function returns the count of motorcycles by subtracting the number of cars from the total
     * number of vehicles.
     *
     * @return The method `getMotorcycles` returns the difference between the total number of vehicles and the number of
     * cars in the list of vehicles.
     */
    public int getMotorcycles() {
        return this.vehicles.size() - this.getCars();
    }

    /**
     * This Java function counts the number of Car objects in a list of Vehicle objects.
     *
     * @return The method `getCars()` returns the total number of cars in the list of vehicles.
     */
    public int getCars() {
        int cars = 0;
        for (Vehicle vehicle : this.vehicles) {
            if (vehicle instanceof Car)
                cars++;
        }
        return cars;
    }
}
