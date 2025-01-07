package com.squid;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
    private final int length = 10;
    private Filter f;

    public ImagePanel(Filter filter) {
        f = filter;

        setPreferredSize(new Dimension((int) (f.ratX * length), (int) (f.ratY * length)));
        setBackground(Color.BLACK);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if ((f.gray))
            paintGray((Graphics2D) g);
        else
            paintSign((Graphics2D) g);

        g.setColor(Color.darkGray);
        //drawLine(g);
        g.dispose();
    }

    private void paintSign(Graphics2D g2) {
        g2.setFont(new Font("Basic Latin", Font.PLAIN, length)); // I don't know how else to adjust the size
        g2.setColor(Color.white);

        for (int i = 0; i < f.imgArr.length; i++)
            for (int j = 0; j < f.imgArr[i].length; j++)
                if (f.imgArr[i][j] > 0)
                    g2.drawString(String.valueOf(Filter.numberToChar(f.imgArr[i][j])), j * length, i * length);
    }

    private void paintGray(Graphics2D g2) {
        for (int i = 0; i < f.imgArr.length; i++)
            for (int j = 0; j < f.imgArr[i].length; j++)
                if (f.imgArr[i][j] > 0) {
                    int col = f.imgArr[i][j];
                    g2.setColor(new Color(col, col, col));
                    g2.fillRect(j * length, i * length, length, length);
                }
    }

    private void drawLine(Graphics g) {
        for (int i = 0; i < f.imgArr[0].length; i++)
            g.drawLine(i * length, 0, i * length, (int) (f.ratY * length));

        for (int i = 0; i < f.imgArr.length; i++)
            g.drawLine(0, i * length, (int) (f.ratX * length), i * length);
    }
}
