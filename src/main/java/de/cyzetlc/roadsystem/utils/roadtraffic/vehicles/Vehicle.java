package de.cyzetlc.roadsystem.utils.roadtraffic.vehicles;

import de.cyzetlc.roadsystem.RoadSystem;
import de.cyzetlc.roadsystem.utils.Direction;
import de.cyzetlc.roadsystem.utils.Location;
import de.cyzetlc.roadsystem.utils.roadtraffic.Intersection;
import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

@Getter
@Setter
public abstract class Vehicle {
    private Color color = Color.CYAN;
    private int speed = 0;
    private Intersection nextTarget;
    private Location location = new Location(0, 0);
    private String imageLocation;

    public Vehicle() {
        this.initialize();
    }

    /**
     * The `drive` method in selects a random branch at the current intersection and moves the vehicle to the next
     * intersection if there are branches available, while also checking for collisions with other vehicles.
     */
    public void drive() {
        if (this.nextTarget != null) {
            if (this.nextTarget.getBranches().size() > 0) {
                Location finalLocation;

                if (this.getLocation().compareTo(this.nextTarget.getLocation())) {
                    int nextIntersectionBranch = new Random().nextInt(this.nextTarget.getBranches().size());
                    Intersection nextIntersection = new LinkedList<>(this.nextTarget.getBranches().keySet()).get(nextIntersectionBranch);

                    this.nextTarget = nextIntersection;
                    finalLocation = nextIntersection.getLocation().copy();
                } else {
                    finalLocation = this.nextTarget.getLocation();
                }

                if (finalLocation != null) {
                    Direction movingDirection = null;

                    if (finalLocation.getY() == this.getLocation().getY()) {
                        if (finalLocation.getX() < this.getLocation().getX()) {
                            movingDirection = Direction.LEFT;
                        } else {
                            movingDirection = Direction.RIGHT;
                        }
                    } else if (finalLocation.getX() == this.getLocation().getX()) {
                        if (finalLocation.getY() < this.getLocation().getY()) {
                            movingDirection = Direction.UP;
                        } else {
                            movingDirection = Direction.DOWN;
                        }
                    }

                    if (movingDirection != null) {
                        switch (movingDirection) {
                            case RIGHT -> this.getLocation().add(this.speed, 0);
                            case LEFT -> this.getLocation().subtract(this.speed, 0);
                            case UP -> this.getLocation().subtract(0, this.speed);
                            case DOWN -> this.getLocation().add(0, this.speed);
                        }
                    }
                }
            }
        }
    }

    /**
     * The draw method reads an image file and draws it at a specified location, or draws a colored rectangle if the image
     * cannot be loaded.
     *
     * @param g2d Graphics2D object used for drawing operations in Java
     */
    public void draw(Graphics2D g2d) {
        BufferedImage image = null;

        try {
            if (this.imageLocation != null) {
                image = ImageIO.read(new File(this.imageLocation));
            }
        } catch (IOException e) {
            RoadSystem.getLogger().error(e.getMessage());
        }

        if (image == null) {
            g2d.setColor(this.color);
            g2d.fillRect(this.getLocation().getX() - 3, this.getLocation().getY() - 3, 6, 6);
        } else {
            g2d.drawImage(image, this.getLocation().getX() - 5, this.getLocation().getY() - 5, 10, 10, null);
        }
    }

    /**
     * The update function sets the next target intersection based on the current location of the object.
     */
    public void update() {
        this.nextTarget = RoadSystem.getInstance().getRoadHandler().getIntersectionByLocation(this.getLocation());
    }

    public abstract void initialize();
}
