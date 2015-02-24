/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vkmessenger;

import javafx.stage.Stage;

/**
 *
 * @author odour
 */
public class Window {

    private static Stage window = null;
    private static Stage optionsWindow = null;
    private static Stage autorizeWindow = null;

    public static void setWindow(Stage stg) {
        window = stg;
    }

    public static Stage getWindow() {
        return window;
    }

    public static void showWindow() {
        window.show();
    }

    public static void setSettingsWindow(Stage stg) {
        optionsWindow = stg;
    }

    public static Stage getSettingsWindow() {
        return optionsWindow;
    }

    public static void showSettingsWindow() {
        optionsWindow.show();
    }
    
    public static void setAutorizeWindow(Stage stg) {
        autorizeWindow = stg;
    }

    public static Stage getAutorizeWindow() {
        return autorizeWindow;
    }

    public static void showAutorizeWindow() {
        autorizeWindow.show();
    }

}
