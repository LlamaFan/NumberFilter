package com.squid;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {
    private int squaresX;
    private int squaresY;
    private final int length = 10;

    public int[][] field;

    public ImagePanel(int squaresX, int squaresY, int[][] image) {
        this.squaresX = squaresX;
        this.squaresY = squaresY;
        field = image;

        setPreferredSize(new Dimension(squaresX * length, squaresY * length));
        setBackground(Color.BLACK);
    }

    // Repaints the GUI

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setFont(new Font("Basic Latin", Font.PLAIN, length)); // I don't know how else to adjust the size
        g2.setColor(Color.white);

        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j] != 25) {
                    //g2.setColor(Color.decode(String.valueOf(field[i][j]))); // Int to hex, but the int won't be translated, so it's blue
                    g2.drawString(String.valueOf(Filter.numberToChar(field[i][j])), j * length, i * length);
                }
            }

        g.setColor(Color.darkGray);
        //drawLine(g);
        g.dispose();
    }

    private void drawLine(Graphics g) {
        for (int i = 0; i < field[0].length; i++)
            g.drawLine(i * length, 0, i * length, squaresY * length);

        for (int i = 0; i < field.length; i++)
            g.drawLine(0, i * length, squaresX * length, i * length);
    }

    private void createCharImage(char let) {
        BufferedImage bi = new BufferedImage(length, length, BufferedImage.TYPE_INT_ARGB);
        //cr
    }
}
