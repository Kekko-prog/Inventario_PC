//vecchia gui non so quale scegliere questa la ritengo piu complicata della nuova che sta sotto

//package it.unina.inventario.gui;

// import javax.swing.JFrame;
// import javax.swing.JTabbedPane;

// public class FinestraPrincipale extends JFrame {

//     public FinestraPrincipale() {
//         setTitle("Inventario Componenti PC");
//         setSize(1400, 520);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLocationRelativeTo(null);

//         JTabbedPane schede = new JTabbedPane();

//         PannelloFornitori pannelloFornitori = new PannelloFornitori();
//         PannelloProcessori pannelloProcessori = new PannelloProcessori(pannelloFornitori);
//         PannelloSchedeVideo pannelloSchedeVideo = new PannelloSchedeVideo(pannelloFornitori);
//         PannelloMemorieRam pannelloMemorieRam = new PannelloMemorieRam(pannelloFornitori);

//         schede.addTab("Processori", pannelloProcessori);
//         schede.addTab("Schede Video", pannelloSchedeVideo);
//         schede.addTab("Memorie RAM", pannelloMemorieRam);
//         schede.addTab("Fornitori", pannelloFornitori);

//         add(schede);
//     }
// }

package it.unina.inventario.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FinestraPrincipale extends JFrame {

    private CardLayout cardLayout;
    private JPanel pannelloCentrale;

    public FinestraPrincipale() {
        setTitle("Inventario Componenti PC");
        setSize(1100, 650); // Leggermente più largo per far spazio alla sidebar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la finestra nello schermo

        // 1. Inizializza i tuoi pannelli (il Model/Controller rimane intatto)
        PannelloFornitori pannelloFornitori = new PannelloFornitori();
        PannelloProcessori pannelloProcessori = new PannelloProcessori(pannelloFornitori);
        PannelloSchedeVideo pannelloSchedeVideo = new PannelloSchedeVideo(pannelloFornitori);
        PannelloMemorieRam pannelloMemorieRam = new PannelloMemorieRam(pannelloFornitori);

        // 2. Crea la Sidebar (Menu Laterale)
        JPanel sidebar = new JPanel();
        // GridLayout: 4 righe, 1 colonna. I bottoni avranno tutti la stessa dimensione
        sidebar.setLayout(new GridLayout(5, 1, 5, 10)); 
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        sidebar.setPreferredSize(new Dimension(180, 0)); // Fissa la larghezza del menu

        // Crea i bottoni di navigazione
        JButton btnProcessori = new JButton("Processori");
        JButton btnSchedeVideo = new JButton("Schede Video");
        JButton btnRam = new JButton("Memorie RAM");
        JButton btnFornitori = new JButton("Fornitori");

        // Aggiungi i bottoni alla sidebar
        sidebar.add(btnProcessori);
        sidebar.add(btnSchedeVideo);
        sidebar.add(btnRam);
        sidebar.add(btnFornitori);

        // 3. Crea il Pannello Centrale con il CardLayout
        cardLayout = new CardLayout();
        pannelloCentrale = new JPanel(cardLayout);

        // Aggiungi i pannelli al "mazzo di carte", associando a ognuno una stringa identificativa
        pannelloCentrale.add(pannelloProcessori, "PROCESSORI");
        pannelloCentrale.add(pannelloSchedeVideo, "SCHEDE_VIDEO");
        pannelloCentrale.add(pannelloMemorieRam, "MEMORIE_RAM");
        pannelloCentrale.add(pannelloFornitori, "FORNITORI");

        // 4. Collega i bottoni al CardLayout (Action Listeners)
        btnProcessori.addActionListener(e -> cardLayout.show(pannelloCentrale, "PROCESSORI"));
        btnSchedeVideo.addActionListener(e -> cardLayout.show(pannelloCentrale, "SCHEDE_VIDEO"));
        btnRam.addActionListener(e -> cardLayout.show(pannelloCentrale, "MEMORIE_RAM"));
        btnFornitori.addActionListener(e -> cardLayout.show(pannelloCentrale, "FORNITORI"));

        // 5. Assembla la finestra principale
        setLayout(new BorderLayout());
        add(sidebar, BorderLayout.WEST); // Sidebar a sinistra
        add(pannelloCentrale, BorderLayout.CENTER); // Contenuto al centro
    }
}