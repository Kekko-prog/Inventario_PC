package it.unina.inventario.gui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class FinestraPrincipale extends JFrame {

    public FinestraPrincipale() {
        setTitle("Inventario Componenti PC");
        setSize(1400, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane schede = new JTabbedPane();

        PannelloFornitori pannelloFornitori = new PannelloFornitori();
        PannelloProcessori pannelloProcessori = new PannelloProcessori(pannelloFornitori);
        PannelloSchedeVideo pannelloSchedeVideo = new PannelloSchedeVideo(pannelloFornitori);
        PannelloMemorieRam pannelloMemorieRam = new PannelloMemorieRam(pannelloFornitori);

        schede.addTab("Processori", pannelloProcessori);
        schede.addTab("Schede Video", pannelloSchedeVideo);
        schede.addTab("Memorie RAM", pannelloMemorieRam);
        schede.addTab("Fornitori", pannelloFornitori);

        add(schede);
    }
}