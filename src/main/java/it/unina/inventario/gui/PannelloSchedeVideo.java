package it.unina.inventario.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

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
import it.unina.inventario.model.Fornitore;
import it.unina.inventario.model.SchedaVideo;

public class PannelloSchedeVideo extends JPanel {

    private final SchedaVideoController controller = new SchedaVideoController();
    private final PannelloFornitori pannelloFornitori;

    private final DefaultTableModel modelloTabella;
    private final JTable tabella;

    private final JTextField campoNome = new JTextField();
    private final JTextField campoPrezzo = new JTextField();
    private final JTextField campoQuantita = new JTextField();
    private final JTextField campoMemoriaVram = new JTextField();
    private final JTextField campoChipset = new JTextField();
    private final JComboBox<Fornitore> comboFornitore = new JComboBox<>();

    public PannelloSchedeVideo(PannelloFornitori pannelloFornitori) {
        this.pannelloFornitori = pannelloFornitori;
        setLayout(new BorderLayout());

        modelloTabella = new DefaultTableModel(
                new Object[]{"ID", "Nome", "Prezzo", "Quantita", "VRAM (GB)", "Chipset", "Fornitore"}, 0);
        tabella = new JTable(modelloTabella);
        add(new JScrollPane(tabella), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));
        form.add(new JLabel("Nome:"));
        form.add(campoNome);
        form.add(new JLabel("Prezzo:"));
        form.add(campoPrezzo);
        form.add(new JLabel("Quantita:"));
        form.add(campoQuantita);
        form.add(new JLabel("VRAM (GB):"));
        form.add(campoMemoriaVram);
        form.add(new JLabel("Chipset:"));
        form.add(campoChipset);
        form.add(new JLabel("Fornitore:"));
        form.add(comboFornitore);

        JButton bottoneSalva = new JButton("Salva");
        bottoneSalva.addActionListener(e -> salva());

        JButton bottoneElimina = new JButton("Elimina selezionato");
        bottoneElimina.addActionListener(e -> elimina());

        form.add(bottoneSalva);
        form.add(bottoneElimina);

        add(form, BorderLayout.SOUTH);

        aggiornaTabella();
    }

    private void salva() {
        try {
            Fornitore fornitoreScelto = (Fornitore) comboFornitore.getSelectedItem();
            if (fornitoreScelto == null) {
                JOptionPane.showMessageDialog(this, "Seleziona un fornitore.");
                return;
            }

            SchedaVideo scheda = new SchedaVideo(
                    campoNome.getText().trim(),
                    Double.parseDouble(campoPrezzo.getText().trim()),
                    Integer.parseInt(campoQuantita.getText().trim()),
                    fornitoreScelto.getId(),
                    Integer.parseInt(campoMemoriaVram.getText().trim()),
                    campoChipset.getText().trim());

            controller.aggiungiSchedaVideo(scheda);

            campoNome.setText("");
            campoPrezzo.setText("");
            campoQuantita.setText("");
            campoMemoriaVram.setText("");
            campoChipset.setText("");

            aggiornaTabella();

        } catch (NumberFormatException errore) {
            JOptionPane.showMessageDialog(this, "Controlla i valori numerici (prezzo, quantita, VRAM).",
                    "Dati non validi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception errore) {
            JOptionPane.showMessageDialog(this, errore.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void elimina() {
        int riga = tabella.getSelectedRow();
        if (riga == -1) {
            JOptionPane.showMessageDialog(this, "Seleziona prima una scheda video dalla tabella.");
            return;
        }

        try {
            int id = (int) modelloTabella.getValueAt(riga, 0);
            controller.eliminaSchedaVideo(id);
            aggiornaTabella();

        } catch (Exception errore) {
            JOptionPane.showMessageDialog(this, errore.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void aggiornaTabella() {
        try {
            modelloTabella.setRowCount(0);
            for (SchedaVideo s : controller.elencaSchedeVideo()) {
                modelloTabella.addRow(new Object[]{
                        s.getId(), s.getNome(), s.getPrezzo(), s.getQuantita(),
                        s.getMemoriaVram(), s.getChipset(), s.getNomeFornitore()});
            }

            comboFornitore.removeAllItems();
            for (Fornitore f : pannelloFornitori.ottieniListaFornitori()) {
                comboFornitore.addItem(f);
            }

        } catch (Exception errore) {
            JOptionPane.showMessageDialog(this, errore.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}