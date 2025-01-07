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
    public boolean gray;

    private static final String brightest = "@%#*+=-:. ";
    private static final String brightest2 = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/|()1{}[]?-_+~<>i!lI;:,^`'.";

    public Filter(String path) {
        setNewImage(path);

        renew(false, true);
        printImage();
    }

    public void renew(boolean inverted, boolean stdTF) {
        initArr();
        convertImageToArray(image);
        if (stdTF) stdDeviation();
        overrideBackground(inverted);
    }

    // Initiates the array and sets a certain ratio
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

    // Manages teh process of setting every pixel
    // Also makes the image smaller, if the size is smaller than the set scale
    private void convertImageToArray(BufferedImage img) {
        for (int i = 0; i < imgArr.length; i++)
            for (int j = 0; j < imgArr[i].length; j++)
                imgArr[i][j] = avgColor(img, (int) (w / ratX * j), (int) (h / ratY * i));
    }

    // Outputs the average color in a specific field
    // The results are much better than without
    private int avgColor(BufferedImage img, int x, int y) {
        int color = 0;
        int lenX = (int) Math.round(img.getWidth() / ratX);
        int lenY = (int) Math.round(img.getHeight() / ratY);

        for (int i = 0; i < lenY; i++)
            for (int j = 0; j < lenX; j++)
                color += grayScale(img.getRGB(x + j, y + i));

        return color / (lenX * lenY);
    }

    // Outputs the gray scale for a pixel
    private int grayScale(int pixel) {
        int a = (pixel >> 24) & 0xff;
        int r = (pixel >> 16) & 0xff;
        int g = (pixel >> 8) & 0xff;
        int b = pixel & 0xff;

        return (r + g + b) / 3 * (a / 255);
    }

    // Calculates the standard deviation of the pixels
    // Used to remove large portions of the image, if they do not fit the other
    public void stdDeviation() {
        average();

        for (int i = 0; i < imgArr.length; i++)
            for (int j = 0; j < imgArr[i].length; j++)
                deviation += (int) Math.pow(imgArr[i][j] - avgCol, 2);

        deviation = (int) Math.sqrt(deviation / (ratX * ratY));
    }

    // This calculates the average pixel gray scale
    private void average() {
        for (int i = 0; i < imgArr.length; i++)
            for (int j = 0; j < imgArr[i].length - 1; j++)
                avgCol += imgArr[i][j];

        avgCol /= (int) (ratX * ratY);
    }

    // In case someone wants to use a custom deviation
    public void setDeviation(int dev) {
        deviation = dev;
    }

    // When we want to remove the background for example, this method overrides the values of the clean image
    public void overrideBackground(boolean inverted) {
        for (int i = 0; i < ratY; i++)
            for (int j = 0; j < ratX; j++)
                if (invert(inverted, i, j))
                    imgArr[i][j] = 0;
    }

    // Sometimes it's needed to invert the parts that are cut out, which is what this method does
    // More elegant this way than in one method or multiple with different inputs
    private boolean invert(boolean inverted, int i, int j) {
        return (inverted) ? (imgArr[i][j] > avgCol - deviation && imgArr[i][j] < avgCol + deviation)
            : imgArr[i][j] < avgCol - deviation || imgArr[i][j] > avgCol + deviation;
    }

    // Simply converts a number to the corresponding letter
    public static char numberToChar(int num) {
        return brightest2.charAt((int) (((double) num / 255) * (brightest2.length() - 1)));
    }

    // Same as before, just with a smaller range of characters
    public static char numberToCharSmall(int num) {
        return brightest.charAt((int) (((double) num / 255) * (brightest.length() - 1)));
    }

    // Outputs the image in the command line
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

    // Sets the new image
    public void setNewImage(String path) {
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setGray(boolean g) {
        gray = g;
    }
}