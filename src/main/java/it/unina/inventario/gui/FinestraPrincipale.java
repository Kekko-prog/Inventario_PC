package it.unina.inventario.gui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class FinestraPrincipale extends JFrame {

    public FinestraPrincipale() {
        setTitle("Inventario Componenti PC");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        PannelloFornitori pannelloFornitori = new PannelloFornitori();
        PannelloProcessori pannelloProcessori = new PannelloProcessori(pannelloFornitori);
        PannelloSchedeVideo pannelloSchedeVideo = new PannelloSchedeVideo(pannelloFornitori);
        PannelloMemorieRam pannelloMemorieRam = new PannelloMemorieRam(pannelloFornitori);

        JTabbedPane schede = new JTabbedPane();
        schede.addTab("Processori", pannelloProcessori);
        schede.addTab("Schede Video", pannelloSchedeVideo);
        schede.addTab("Memorie RAM", pannelloMemorieRam);
        schede.addTab("Fornitori", pannelloFornitori);

        add(schede);
    }
}