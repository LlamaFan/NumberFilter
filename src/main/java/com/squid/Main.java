package com.squid;

public class Main {
    private static Filter filter;
    private static ImagePanel ip;
    private static SettingPanel sp;
    private static Window window;
    private static Window windowSP;

    private static boolean running = true;

    public static void main(String[] args) {
        //createNew("src/data/test3.jpg");
        sp = new SettingPanel();
        windowSP = new Window(sp);

        /*while (running) {
            scanForCommand();
        }*/
    }
}