package com.squid;

public class Main {
    private static ImagePanel ip;
    private static Filter filter;
    private static Window window;

    public static void main(String[] args) {
        filter = new Filter();
        ip = new ImagePanel(100);
        window = new Window(ip);

        ip.repaint();
    }
}