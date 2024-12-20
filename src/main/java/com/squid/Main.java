package com.squid;

import java.util.Scanner;

public class Main {
    private static ImagePanel ip;
    private static Filter filter;
    private static Window window;

    private static boolean running = true;

    public static void main(String[] args) {
        createNew("src/data/example.jpg");

        while (running) {
            scanForCommand();
        }
    }

    private static void scanForCommand() {
        Scanner sn = new Scanner(System.in);

        switch (sn.nextLine()) {
            case "stop" -> exitProgram();
            case "new image" -> newImage(sn);
        }
    }

    private static void exitProgram() {
        window.dispose();
        System.out.println("You stopped the program");
        System.exit(0);
    }

    private static void newImage(Scanner sn) {
        if (window != null) {
            window.dispose();
        }

        try {
            System.out.println("Enter the path to the image");
            createNew(sn.nextLine());
        } catch (Exception e) {
            System.out.println("Wrong path or image");
        }
    }

    private static void createNew(String path) {
        filter = new Filter(path);
        ip = new ImagePanel((int) filter.ratX, (int) filter.ratY, filter.imageArr);
        window = new Window(ip);

        ip.repaint();
    }
}