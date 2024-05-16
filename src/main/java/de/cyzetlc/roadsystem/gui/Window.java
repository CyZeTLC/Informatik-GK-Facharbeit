package de.cyzetlc.roadsystem.gui;

import lombok.Getter;

import javax.swing.*;

@Getter
public class Window {
    private final JFrame window;
    private final GuiScreen screen;

    public Window() {
        this.screen = new GuiScreen();
        this.window = new JFrame("Java Graphen");
        this.window.setContentPane(this.screen);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setResizable(false);
        this.window.pack();
        this.window.setVisible(true);
    }
}
