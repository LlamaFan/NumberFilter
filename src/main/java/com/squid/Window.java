package com.squid;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Window extends JFrame {
    public Window(ImagePanel ip) {
        setWindow();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(ip);
        pack();

        setVisible(true);
        ip.repaint();
    }

    public Window(SettingPanel sp) {
        setWindow();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(sp);
        pack();

        setVisible(true);
        sp.repaint();
    }

    private void setWindow() {
        setTitle("Image as letters");
        setResizable(false);

        try {
            setIconImage(ImageIO.read(new File("src/data/icon.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
