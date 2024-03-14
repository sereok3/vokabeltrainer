package net.tfobz.vokabeltrainer;

import javax.swing.SwingUtilities;

import net.tfobz.vokabeltrainer.GUI.FensterStartSeite;

public class Main {
    protected static boolean darkmode = false;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FensterStartSeite startSeite = new FensterStartSeite();
            startSeite.setVisible(true);
        });
    }
}
