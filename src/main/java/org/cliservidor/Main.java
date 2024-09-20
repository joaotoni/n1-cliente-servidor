package org.cliservidor;

import javax.swing.SwingUtilities;
import org.cliservidor.novo.MenuInterface;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MenuInterface().setVisible(true);
            }
        });
    }
}
