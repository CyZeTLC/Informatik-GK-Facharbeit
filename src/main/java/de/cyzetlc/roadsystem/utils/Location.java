package de.cyzetlc.roadsystem.utils;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Location {
    private int x;
    private int y;
    private boolean fixed;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
        this.fixed = false;
    }

    public Location(int x, int y, boolean fixed) {
        this.x = x;
        this.y = y;
        this.fixed = fixed;
    }

    /**
     * The `add` function in Java increments the x and y coordinates of a Location object by the specified values and
     * returns the updated object.
     *
     * @param x The parameter `x` represents the amount by which the x-coordinate of the location is to be increased.
     * @param y The parameter `y` in the `add` method represents the amount by which the current y-coordinate of the
     * Location object is to be incremented.
     * @return The `Location` object is being returned.
     */
    public Location add(int x, int y) {
        if (!this.fixed) {
            this.x += x;
            this.y += y;
        }
        return this;
    }

    /**
     * The `subtract` function in Java subtracts the given x and y values from the current x and y coordinates of a
     * Location object and returns the updated Location object.
     *
     * @param x The parameter `x` represents the value that will be subtracted from the `x` coordinate of the `Location`
     * object.
     * @param y The `subtract` method in the code snippet subtracts the given `x` and `y` values from the current `x` and
     * `y` coordinates of a `Location` object.
     * @return The method is returning the updated Location object after subtracting the values of x and y from its current
     * x and y coordinates.
     */
    public Location subtract(int x, int y) {
        if (!this.fixed) {
            this.x -= x;
            this.y -= y;
        }
        return this;
    }

    /**
     * The distance method calculates the distance between two Location objects using their x and y coordinates.
     *
     * @param location The `distance` method calculates the distance between the current location (this.x, this.y) and the
     * location passed as a parameter. The parameter `location` is an object of type `Location` which has `getX()` and
     * `getY()` methods to retrieve the x and y coordinates of that location
     * @return The method is returning the distance between the current location (this.x, this.y) and the location passed
     * as a parameter.
     */
    public int distance(Location location) {
        return (int) Math.sqrt((location.getY() - this.y) * (location.getY() - this.y) + (location.getX() - this.x) * (location.getX() - this.x));
    }

    /**
     * The `copy` function creates and returns a new `Location` object with the same `x` and `y` coordinates as the
     * original object.
     *
     * @return A new `Location` object is being returned, with the same `x` and `y` coordinates as the original `Location`
     * object.
     */
    public Location copy() {
        return new Location(this.x, this.y);
    }

    /**
     * The `compareTo` function in Java compares the x and y coordinates of two Location objects for equality.
     *
     * @param compareTo The `compareTo` parameter in the `compareTo` method is of type `Location`, which represents a
     * location with `x` and `y` coordinates. The method compares the `x` and `y` coordinates of the current `Location`
     * object (referred to as `this`) with the
     * @return The `compareTo` method is returning a boolean value that indicates whether the x and y coordinates of the
     * current `Location` object are equal to the x and y coordinates of the `compareTo` object.
     */
    public boolean compareTo(Location compareTo) {
        return this.x == compareTo.getX() && this.y == compareTo.getY();
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                ", fixed=" + fixed +
                '}';
    }
}
