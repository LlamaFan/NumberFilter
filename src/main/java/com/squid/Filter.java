package com.squid;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Filter {
    public int[][] imgArr;
    private BufferedImage image;

    public final int scale = 75;
    public double ratX;
    public double ratY;

    private double w;
    private double h;

    private int avgCol;
    private int deviation;

    private static final String brightest = "@%#*+=-:. ";
    private static final String brightest2 = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/|()1{}[]?-_+~<>i!lI;:,^`'.";

    public Filter(String path) {
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        initArr();
        convertImageToArray(image);
        stdDeviation();
        overrideBackground();
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

        imgArr = new int[(int) ratY][(int) ratX];
    }

    private void convertImageToArray(BufferedImage img) {
        for (int i = 0; i < imgArr.length; i++)
            for (int j = 0; j < imgArr[i].length; j++)
                imgArr[i][j] = avgColor(img, (int) (w / ratX * j), (int) (h / ratY * i));
    }

    private int avgColor(BufferedImage img, int x, int y) {
        int color = 0;
        int lenX = (int) Math.round(img.getWidth() / ratX);
        int lenY = (int) Math.round(img.getHeight() / ratY);

        for (int i = 0; i < lenY; i++)
            for (int j = 0; j < lenX; j++)
                color += grayScale(img.getRGB(x + j, y + i));

        return color / (lenX * lenY);
    }

    private int grayScale(int pixel) {
        int a = (pixel >> 24) & 0xff;
        int r = (pixel >> 16) & 0xff;
        int g = (pixel >> 8) & 0xff;
        int b = pixel & 0xff;

        return (r + g + b) / 3 * (a / 255);
    }

    private void stdDeviation() {
        for (int i = 0; i < imgArr.length; i++)
            for (int j = 0; j < imgArr[i].length - 1; j++) {
                avgCol += imgArr[i][j];
                System.out.println(imgArr[i][j]);
            }
        avgCol /= (int) (ratX * ratY);

        for (int i = 0; i < imgArr.length; i++)
            for (int j = 0; j < imgArr[i].length; j++)
                deviation += (int) Math.pow(imgArr[i][j] - avgCol, 2);
        deviation = (int) Math.sqrt(deviation / (ratX * ratY));

        System.out.println("Dev: " + deviation);
        System.out.println("Avg: " + avgCol);
    }

    private void overrideBackground() {
        for (int i = 0; i < ratY; i++)
            for (int j = 0; j < ratX; j++)
                if (imgArr[i][j] > avgCol - deviation && imgArr[i][j] < avgCol + deviation)
                    imgArr[i][j] = 0;
    }

    public static char numberToChar(int num) {
        return brightest2.charAt((int) (((double) num / 255) * (brightest2.length() - 1)));
    }

    private void printImage() {
        for (int i = 0; i < imgArr.length; i++) {
            for (int j = 0; j < imgArr[i].length; j++)
                if (imgArr[i][j] > 0)
                    System.out.print(numberToChar(imgArr[i][j]) + "");
                else
                    System.out.print(" ");

            System.out.println();
        }
    }
}