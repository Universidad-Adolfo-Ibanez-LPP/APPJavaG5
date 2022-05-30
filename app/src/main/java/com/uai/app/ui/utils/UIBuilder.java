package com.uai.app.ui.utils;

import com.uai.app.exceptions.NotMainPanelLoadedException;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class UIBuilder {

    public static void buildUI(Class jFrame){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UAIJFrame ui = (UAIJFrame) jFrame.getDeclaredConstructors()[0].newInstance(jFrame.getCanonicalName());
                    ui.showUI();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NotMainPanelLoadedException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public static void buildMainUI(Class jFrame){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UAIJFrame ui = (UAIJFrame) jFrame.getDeclaredConstructors()[0].newInstance(jFrame.getCanonicalName());
                    ui.showUI();
                    ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NotMainPanelLoadedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
