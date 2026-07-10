package it.unina.inventario.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import it.unina.inventario.controller.SchedaVideoController;
import it.unina.inventario.eccezioni.DatabaseException;
import it.unina.inventario.eccezioni.ValidazioneException;
import it.unina.inventario.model.Fornitore;
import it.unina.inventario.model.SchedaVideo;

public class PannelloSchedeVideo extends JPanel {

    private final SchedaVideoController controller = new SchedaVideoController();
    private final PannelloFornitori pannelloFornitori;

    private JTable tabella;
    private DefaultTableModel modelloTabella;

    private JTextField campoNome;
    private JTextField campoPrezzo;
    private JTextField campoQuantita;
    private JTextField campoMemoriaVram;
    private JTextField campoChipset;
    private JComboBox<Fornitore> comboFornitore;

    public PannelloSchedeVideo(PannelloFornitori pannelloFornitori) {
        this.pannelloFornitori = pannelloFornitori;
        setLayout(new BorderLayout());
        creaTabella();
        creaFormInserimento();
        aggiornaTabella();
    }

    private void creaTabella() {
        modelloTabella = new DefaultTableModel(
                new Object[]{"ID", "Nome", "Prezzo", "Quantita", "VRAM (GB)", "Chipset", "Fornitore"}, 0) {
            @Override
            public boolean isCellEditable(int riga, int colonna) {
                return false;
            }
        };
        tabella = new JTable(modelloTabella);
        add(new JScrollPane(tabella), BorderLayout.CENTER);
    }

    private void creaFormInserimento() {
        JPanel pannelloForm = new JPanel(new GridLayout(0, 2, 5, 5));
        pannelloForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        campoNome = new JTextField();
        campoPrezzo = new JTextField();
        campoQuantita = new JTextField();
        campoMemoriaVram = new JTextField();
        campoChipset = new JTextField();
        comboFornitore = new JComboBox<>();

        pannelloForm.add(new JLabel("Nome:"));
        pannelloForm.add(campoNome);
        pannelloForm.add(new JLabel("Prezzo:"));
        pannelloForm.add(campoPrezzo);
        pannelloForm.add(new JLabel("Quantita:"));
        pannelloForm.add(campoQuantita);
        pannelloForm.add(new JLabel("VRAM (GB):"));
        pannelloForm.add(campoMemoriaVram);
        pannelloForm.add(new JLabel("Chipset:"));
        pannelloForm.add(campoChipset);
        pannelloForm.add(new JLabel("Fornitore:"));
        pannelloForm.add(comboFornitore);

        JButton bottoneSalva = new JButton("Salva");
        bottoneSalva.addActionListener(e -> salvaSchedaVideo());

        JButton bottoneElimina = new JButton("Elimina selezionato");
        bottoneElimina.addActionListener(e -> eliminaSchedaSelezionata());

        JButton bottoneAggiorna = new JButton("Aggiorna elenco");
        bottoneAggiorna.addActionListener(e -> aggiornaTabella());

        JPanel pannelloBottoni = new JPanel();
        pannelloBottoni.add(bottoneSalva);
        pannelloBottoni.add(bottoneElimina);
        pannelloBottoni.add(bottoneAggiorna);

        JPanel pannelloSud = new JPanel(new BorderLayout());
        pannelloSud.add(pannelloForm, BorderLayout.CENTER);
        pannelloSud.add(pannelloBottoni, BorderLayout.SOUTH);

        add(pannelloSud, BorderLayout.SOUTH);
    }

    private void salvaSchedaVideo() {
        try {
            String nome = campoNome.getText().trim();
            double prezzo = Double.parseDouble(campoPrezzo.getText().trim());
            int quantita = Integer.parseInt(campoQuantita.getText().trim());
            int memoriaVram = Integer.parseInt(campoMemoriaVram.getText().trim());
            String chipset = campoChipset.getText().trim();

            Fornitore fornitoreScelto = (Fornitore) comboFornitore.getSelectedItem();
            if (fornitoreScelto == null) {
                JOptionPane.showMessageDialog(this, "Seleziona un fornitore.");
                return;
            }

            SchedaVideo scheda = new SchedaVideo(nome, prezzo, quantita,
                    fornitoreScelto.getId(), memoriaVram, chipset);

            controller.aggiungiSchedaVideo(scheda);
            svuotaCampi();
            aggiornaTabella();

        } catch (NumberFormatException errore) {
            JOptionPane.showMessageDialog(this, "Controlla i valori numerici inseriti (prezzo, quantita, VRAM).",
                    "Dati non validi", JOptionPane.ERROR_MESSAGE);
        } catch (ValidazioneException errore) {
            JOptionPane.showMessageDialog(this, errore.getMessage(),
                    "Attenzione", JOptionPane.WARNING_MESSAGE);
        } catch (DatabaseException errore) {
            JOptionPane.showMessageDialog(this, errore.getMessage(),
                    "Errore di Sistema", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminaSchedaSelezionata() {
        int rigaSelezionata = tabella.getSelectedRow();
        if (rigaSelezionata == -1) {
            JOptionPane.showMessageDialog(this, "Seleziona prima una scheda video dalla tabella.");
            return;
        }
        int id = (int) modelloTabella.getValueAt(rigaSelezionata, 0);

        try {
            controller.eliminaSchedaVideo(id);
            aggiornaTabella();
        } catch (DatabaseException errore) {
            JOptionPane.showMessageDialog(this, errore.getMessage(),
                    "Errore di Sistema", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void svuotaCampi() {
        campoNome.setText("");
        campoPrezzo.setText("");
        campoQuantita.setText("");
        campoMemoriaVram.setText("");
        campoChipset.setText("");
    }

    public void aggiornaTabella() {
        try {
            modelloTabella.setRowCount(0);
            List<SchedaVideo> schede = controller.elencaSchedeVideo();
            for (SchedaVideo s : schede) {
                modelloTabella.addRow(new Object[]{
                        s.getId(), s.getNome(), s.getPrezzo(), s.getQuantita(),
                        s.getMemoriaVram(), s.getChipset(), s.getNomeFornitore()
                });
            }

            comboFornitore.removeAllItems();
            for (Fornitore f : pannelloFornitori.ottieniListaFornitori()) {
                comboFornitore.addItem(f);
            }

        } catch (DatabaseException errore) {
            JOptionPane.showMessageDialog(this, errore.getMessage(),
                    "Errore di Sistema", JOptionPane.ERROR_MESSAGE);
        }
    }
}