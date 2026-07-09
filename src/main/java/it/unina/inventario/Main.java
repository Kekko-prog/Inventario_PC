package it.unina.inventario;

import javax.swing.SwingUtilities;

import it.unina.inventario.gui.FinestraPrincipale;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FinestraPrincipale finestra = new FinestraPrincipale();
            finestra.setVisible(true);
        });
    }
}