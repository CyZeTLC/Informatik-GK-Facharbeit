package de.cyzetlc.roadsystem.gui;

import de.cyzetlc.roadsystem.RoadSystem;
import de.cyzetlc.roadsystem.utils.Runtime;
import de.cyzetlc.roadsystem.utils.roadtraffic.vehicles.Vehicle;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Getter @Setter
public class GuiScreen extends JPanel implements KeyListener {

    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int SCALE = 2;

    private boolean running;
    private ScheduledExecutorService runtime;

    private boolean showNode = false;
    private boolean showRoadLength = false;

    public GuiScreen() {
        super();
        setPreferredSize(
                new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setFocusable(true);
        requestFocus();
    }

    /**
     * The addNotify method initializes key listener, initializes the component, creates a new Runtime object, and starts
     * it asynchronously.
     */
    public void addNotify() {
        super.addNotify();
        this.addKeyListener(this);
        this.init();
        this.runtime = Executors.newScheduledThreadPool(1);
        this.runtime.scheduleAtFixedRate(new Runtime(this), 0, RoadSystem.getInstance().getTickSpeed(), TimeUnit.MILLISECONDS);

    }

    /**
     * The `init` function sets the `running` variable to `true`.
     */
    private void init() {
        running = true;
    }

    /**
     * The `draw` method creates a BufferedImage, fills it with colored rectangles, draws a road system, and then displays
     * the image on the screen.
     */
    public void draw() {
        BufferedImage bufferedImage = new BufferedImage(WIDTH * SCALE, HEIGHT * SCALE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect(0,0, WIDTH * SCALE - 125, HEIGHT * SCALE);

        g2d.setColor(Color.GRAY);
        g2d.fillRect(WIDTH * SCALE - 125, 0, 125, HEIGHT * SCALE);

        RoadSystem.getInstance().getRoadHandler().draw(g2d, WIDTH * SCALE, HEIGHT * SCALE, this.showNode, this.showRoadLength);
        g2d.dispose();

        Graphics2D g2dComponent = (Graphics2D) this.getGraphics();
        g2dComponent.drawImage(bufferedImage, null, 0, 0);
    }

    /**
     * The keyTyped function in Java is a method that is called when a key is typed.
     *
     * @param key The `key` parameter in the `keyTyped` method represents the key that was typed by the user. This
     * parameter is of type `KeyEvent`, which contains information about the key event, such as the key code, key char, and
     * modifiers.
     */
    public void keyTyped(KeyEvent key) {
    }

    /**
     * The function keyPressed handles key events to show or hide a node and toggle the display of road length.
     *
     * @param key The `key` parameter in the `keyPressed` method represents the KeyEvent object that contains information
     * about the key that was pressed by the user. The `KeyEvent` class provides methods like `getKeyCode()` which returns
     * the integer keyCode for the key that was pressed.
     */
    public void keyPressed(KeyEvent key) {
        if (key.getKeyCode() == 116) {
            this.showNode = true;
        }

        if (key.getKeyCode() == 117) {
            this.showRoadLength = !this.showRoadLength;
        }
    }

    /**
     * The keyReleased function sets the showNode variable to false if the released key has the keycode 116.
     *
     * @param key The `key` parameter in the `keyReleased` method represents the `KeyEvent` object that contains
     * information about the key event that occurred, such as the key code of the key that was released. In this specific
     * code snippet, the method checks if the key code of the released key is equal to
     */
    public void keyReleased(KeyEvent key) {
        if (key.getKeyCode() == 116) {
            this.showNode = false;
        }
    }

}
