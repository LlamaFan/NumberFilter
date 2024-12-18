package com.squid;

import javax.swing.*;

public class Window extends JFrame {
    private ImagePanel ip;

    public Window(ImagePanel ip) {
        this.ip = ip;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Terrain Generation");
        setResizable(false);

        add(ip);
        pack();

        setVisible(true);

        this.ip.repaint();
    }
}
