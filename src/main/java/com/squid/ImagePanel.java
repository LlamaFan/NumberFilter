package com.squid;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
    private final int size = 500;
    private int squaresX;
    private int squaresY;
    private final int length = size / squaresX;

    public int[][] field;

    public ImagePanel(int squaresX, int squaresY) {
        this.squaresX = squaresX;
        this.squaresY = squaresY;
        field = new int[squaresX][squaresY];

        setPreferredSize(new Dimension(size, size));
        setBackground(Color.BLACK);
    }

    // Repaints the GUI

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (int i = 0; i < squaresX; i++)
            for (int j = 0; j < squaresY; j++) {
                if (field[i][j] == 0) {
                    g2.setColor(Color.black);
                } else {
                    g2.setColor(Color.white);
                }

                g2.fillRect(i * length, j * length, length, length);
            }

        g.setColor(Color.darkGray);
        drawLine(g);
        g.dispose();
    }

    private void drawLine(Graphics g) {
        for (int i = 0; i < squaresX; i++) {
            g.drawLine(i * length, 0, i * length, size);
            g.drawLine(0, i * length, size, i * length);
        }
    }
}
