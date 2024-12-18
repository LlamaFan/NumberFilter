package com.squid;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Filter {
    private int[][] imageArr;
    private BufferedImage image;

    public final int scale = 100;
    public double ratX;
    public double ratY;

    private double w;
    private double h;

    private final String brightest = "@%#*+=-:. ";

    public Filter() {
        try {
            image = ImageIO.read(new File("src/data/example3.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        initArr();

        convertImageToArray(image);
        printImage();
    }

    private void initArr() {
        w = image.getWidth();
        h = image.getHeight();

        if (w >= h) {
            ratX = scale;
            ratY = Math.round(scale * (h / w));
        } else {
            ratX = Math.round(scale * (w / h));
            ratY = scale;
        }

        imageArr = new int[(int) ratY][(int) ratX];
    }

    private void convertImageToArray(BufferedImage img) {
        for (int i = 0; i < imageArr.length; i++)
            for (int j = 0; j < imageArr[i].length; j++)
                imageArr[i][j] = grayScale(
                    img.getRGB((int) (w / ratX * j), (int) (h / ratY * i)));
    }

    private int grayScale(int pixel) {
        int a = (pixel >> 24) & 0xff;
        int r = (pixel >> 16) & 0xff;
        int g = (pixel >> 8) & 0xff;
        int b = pixel & 0xff;

        return (r + g + b) / 3 * (a / 255);
    }

    private char numberToChar(int num) {
        return brightest.charAt((int) (((double) num / 255) * (brightest.length() - 1)));
    }

    private void printImage() {
        for (int i = 0; i < imageArr.length; i++) {
            for (int j = 0; j < imageArr[i].length; j++)
                System.out.print(numberToChar(imageArr[i][j]) + "");

            System.out.println("");
        }
    }
}