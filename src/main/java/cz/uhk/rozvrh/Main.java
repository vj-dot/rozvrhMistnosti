package cz.uhk.rozvrh;

import cz.uhk.rozvrh.gui.MainWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            new MainWindow().setVisible(true);
        });
    }
}
