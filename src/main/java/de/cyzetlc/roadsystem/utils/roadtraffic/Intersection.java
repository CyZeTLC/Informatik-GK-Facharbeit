package de.cyzetlc.roadsystem.utils.roadtraffic;

import de.cyzetlc.roadsystem.utils.Location;
import lombok.Getter;

import java.util.HashMap;

@Getter
public class Intersection {
    private final HashMap<Intersection, Integer> branches;
    private final Location location;
    private final String name;

    public Intersection(String name, HashMap<Intersection, Integer> branches, Location location) {
        this.name = name;
        this.branches = branches;
        this.location = location;
    }

    /**
        @param location the location of the intersection on the gui
     */
    public Intersection(String name, Location location) {
        this.name = name;
        this.branches = new HashMap<>();
        this.location = location;
    }

    /**
     * The addBranch method adds an intersection and its distance from the current location to a map of branches.
     *
     * @param intersection An intersection object representing a point where two or more roads meet.
     */
    public void addBranch(Intersection intersection) {
        this.branches.put(intersection, this.location.distance(intersection.getLocation()));
    }

    /**
     * The function `addBranch` adds an intersection and its corresponding distance to a map of branches.
     *
     * @param intersection Intersection is an object representing a point where two or more roads meet or cross. It
     * typically contains information such as the coordinates of the intersection and the roads that intersect at that
     * point.
     * @param distance Distance is an integer value representing the distance from the current intersection to the branch
     * intersection.
     */
    public void addBranch(Intersection intersection, int distance) {
        this.branches.put(intersection, distance);
    }
}
