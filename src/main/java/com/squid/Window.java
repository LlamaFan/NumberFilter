package com.squid;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Window extends JFrame {
    private ImagePanel ip;

    public Window(ImagePanel ip) {
        this.ip = ip;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Image as letters");
        setResizable(false);

        try {
            setIconImage(ImageIO.read(new File("src/data/icon.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        add(ip);
        pack();

        setVisible(true);

        this.ip.repaint();
    }
}
