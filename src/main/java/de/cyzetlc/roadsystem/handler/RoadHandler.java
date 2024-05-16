package de.cyzetlc.roadsystem.handler;

import de.cyzetlc.roadsystem.RoadSystem;
import de.cyzetlc.roadsystem.utils.Direction;
import de.cyzetlc.roadsystem.utils.Runtime;
import de.cyzetlc.roadsystem.utils.roadtraffic.Intersection;
import de.cyzetlc.roadsystem.utils.Location;
import de.cyzetlc.roadsystem.utils.roadtraffic.TrafficJam;
import de.cyzetlc.roadsystem.utils.roadtraffic.vehicles.Vehicle;
import lombok.Getter;
import lombok.Setter;

import javax.sql.rowset.CachedRowSet;
import java.awt.*;
import java.sql.SQLException;
import java.util.LinkedList;

@Getter
public class RoadHandler {
    private final LinkedList<Intersection> intersections;
    private final LinkedList<TrafficJam> trafficJams;

    @Setter
    private int trafficJamesCount = 0;
    private int edges = 0;

    public RoadHandler() {
        this.intersections = new LinkedList<>();
        this.trafficJams = new LinkedList<>();

        if (RoadSystem.getInstance().isUseDatabase()) {
            this.loadIntersectionsFromDatabase();
        }
    }

    /**
     * The `loadIntersectionsFromDatabase` function retrieves intersection data from a database and establishes connections
     * between intersections based on branch information.
     */
    private void loadIntersectionsFromDatabase() {
        try {
            CachedRowSet rs = RoadSystem.getInstance().getQueryHandler().createBuilder(
                    "SELECT * FROM `intersections`"
            ).executeQuerySync();

            while (rs.next()) {
                Intersection tmp = new Intersection(rs.getString("name"), new Location(rs.getInt("loc_x"), rs.getInt("loc_y"), true));
                this.intersections.add(tmp);
            }

            rs = RoadSystem.getInstance().getQueryHandler().createBuilder(
                    "SELECT * FROM `branches`"
            ).executeQuerySync();

            while (rs.next()) {
                Intersection one = this.getIntersectionByName(rs.getString("intersection_one"));
                Intersection two = this.getIntersectionByName(rs.getString("intersection_two"));

                if (!one.getBranches().containsKey(two)) {
                    one.addBranch(two);
                }

                if (!two.getBranches().containsKey(one)) {
                    two.addBranch(one);
                }
                this.edges++;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * This function searches for an intersection based on a given location and returns the intersection if found.
     *
     * @param location The `getIntersectionByLocation` method you provided is used to find an `Intersection` object based
     * on a given `Location` object. However, there is a potential issue with the comparison in the `if` statement.
     * @return The method `getIntersectionByLocation` returns an `Intersection` object based on the provided `Location`
     * parameter. If an `Intersection` with the specified `Location` is found in the list of intersections, that
     * `Intersection` object is returned. If no matching `Intersection` is found, the method returns `null`.
     */
    public Intersection getIntersectionByLocation(Location location) {
        for (Intersection intersection : this.intersections) {
            if (intersection.getLocation().getX() == location.getX() && intersection.getLocation().getY() == location.getY()) {
                return intersection;
            }
        }
        return null;
    }

    /**
     * The function `getIntersectionByName` searches for an intersection by name in a list and returns the intersection if
     * found, otherwise returns null.
     *
     * @param name The `getIntersectionByName` method takes a `String` parameter `name`, which represents the name of an
     * intersection. The method iterates through a list of intersections and returns the intersection object that has a
     * name matching the provided `name` (case-insensitive). If no intersection with the specified name
     * @return The method `getIntersectionByName` returns an `Intersection` object with the specified name, if found in the
     * list of intersections. If no intersection with the given name is found, it returns `null`.
     */
    public Intersection getIntersectionByName(String name) {
        for (Intersection intersection : this.intersections) {
            if (intersection.getName().equalsIgnoreCase(name)) {
                return intersection;
            }
        }
        return null;
    }

    /**
     * This Java function retrieves a TrafficJam object based on a specified Intersection object.
     *
     * @param intersection An intersection object representing a road intersection.
     * @return The method `getTrafficJamByIntersection` returns a `TrafficJam` object that corresponds to the provided
     * `Intersection` object. If a matching `TrafficJam` is found in the list of traffic jams, it is returned. Otherwise,
     * `null` is returned.
     */
    public TrafficJam getTrafficJamByIntersection(Intersection intersection) {
        for (TrafficJam trafficJam : this.getTrafficJams()) {
            if (trafficJam.getIntersection().equals(intersection)) {
                return trafficJam;
            }
        }
        return null;
    }

    /**
     * This Java function returns the TrafficJam object associated with a given Vehicle object, if it is found within the
     * list of TrafficJam objects.
     *
     * @param vehicle The `getTrafficJamOfVehicle` method takes a `Vehicle` object as a parameter and returns the
     * `TrafficJam` object that contains the specified vehicle. It iterates through the list of traffic jams in the current
     * context and checks if the vehicles in each traffic jam contain the specified vehicle. If
     * @return The method `getTrafficJamOfVehicle` returns a `TrafficJam` object that contains the specified `Vehicle`. If
     * no `TrafficJam` contains the specified `Vehicle`, it returns `null`.
     */
    public TrafficJam getTrafficJamOfVehicle(Vehicle vehicle) {
        for (TrafficJam trafficJam : this.getTrafficJams()) {
            if (trafficJam.getVehicles().contains(vehicle)) {
                return trafficJam;
            }
        }
        return null;
    }

    /**
     * This Java function draws intersections and connections on a graphics object, with options to display nodes and
     * various statistics.
     *
     * @param g The parameter `g` is of type `Graphics2D` and is used for drawing 2D graphics. It provides methods for
     * drawing shapes, text, and images onto a graphics context.
     * @param width The `width` parameter in the `draw` method represents the width of the drawing area or canvas where the
     * intersections and other information will be displayed. It is used to position the text information on the right side
     * of the canvas.
     * @param height The `height` parameter in the `draw` method represents the height of the drawing area or canvas where
     * you are rendering the intersections, nodes, and other information. This parameter is used to position elements
     * correctly within the specified height of the canvas.
     * @param drawNode The `drawNode` parameter in the `draw` method is a boolean flag that determines whether to draw the
     * nodes (intersections) on the graphics context `g`. If `drawNode` is `true`, the method will draw a red rectangle
     * representing each intersection. If `drawNode` is
     */
    public void draw(Graphics2D g, int width, int height, boolean drawNode, boolean showRoadLength) {
        for (Intersection intersection : this.intersections) {
            if (drawNode) {
                g.setColor(Color.RED);
                g.fillRect(intersection.getLocation().getX()-7, intersection.getLocation().getY()-7, 14, 14);
            }

            for (Intersection connection : intersection.getBranches().keySet()) {
                g.setColor(Color.BLACK);
                g.setStroke(new BasicStroke(5));
                g.drawLine(intersection.getLocation().getX(), intersection.getLocation().getY(), connection.getLocation().getX(), connection.getLocation().getY());

                if (showRoadLength) {
                    int length = intersection.getBranches().get(connection);
                    int x = Math.min(intersection.getLocation().getX(), connection.getLocation().getX());
                    int y = Math.min(intersection.getLocation().getY(), connection.getLocation().getX());
                    Direction direction = null;

                    if (connection.getLocation().getX() == intersection.getLocation().getX()) {
                        direction = Direction.VERTICAL;
                    } else if (connection.getLocation().getY() == intersection.getLocation().getY()) {
                        direction = Direction.HORIZONTAL;
                    }

                    g.setColor(Color.LIGHT_GRAY);

                    if (direction == Direction.HORIZONTAL) {
                        g.drawString(length + "m", x + length/2 - g.getFontMetrics().stringWidth(length + "m") / 2, intersection.getLocation().getY() - 5);
                    } else if (direction == Direction.VERTICAL) {
                        g.drawString(length + "m", x + 5, y + length/2);
                    }
                }
            }
        }

        g.setColor(Color.BLACK);
        g.drawString("Knoten: " + this.intersections.size(), width - 120, 5 + g.getFontMetrics().getHeight());
        g.drawString("Kanten: " + edges, width - 120, 10 + g.getFontMetrics().getHeight()*2);
        g.drawString("Entities: " + RoadSystem.getInstance().getVehicleHandler().getVehicles().size(), width - 120, 15 + g.getFontMetrics().getHeight()*3);
        g.drawString("⨠ Cars: " + RoadSystem.getInstance().getVehicleHandler().getCars(), width - 115, 20 + g.getFontMetrics().getHeight()*4);
        g.drawString("⨠ Motorcycles: " + RoadSystem.getInstance().getVehicleHandler().getMotorcycles(), width - 115, 25 + g.getFontMetrics().getHeight()*5);
        g.drawString("Ticks: " + Runtime.getCarChanges(), width - 120, 30 + g.getFontMetrics().getHeight()*6);
        g.drawString("Aktive Staus: " + this.trafficJams.size(), width - 120, 35 + g.getFontMetrics().getHeight()*7);
        g.drawString("Staus: " + this.trafficJamesCount, width - 120, 40 + g.getFontMetrics().getHeight()*8);
    }
}
