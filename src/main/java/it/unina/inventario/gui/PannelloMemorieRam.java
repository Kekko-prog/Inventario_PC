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

import it.unina.inventario.controller.MemoriaRamController;
import it.unina.inventario.model.Fornitore;
import it.unina.inventario.model.MemoriaRam;

public class PannelloMemorieRam extends JPanel {

    private final MemoriaRamController controller = new MemoriaRamController();
    private final PannelloFornitori pannelloFornitori;

    private final DefaultTableModel modelloTabella;
    private final JTable tabella;

    private final JTextField campoNome = new JTextField();
    private final JTextField campoPrezzo = new JTextField();
    private final JTextField campoQuantita = new JTextField();
    private final JTextField campoCapacita = new JTextField();
    private final JTextField campoTipoMemoria = new JTextField();
    private final JComboBox<Fornitore> comboFornitore = new JComboBox<>();

    public PannelloMemorieRam(PannelloFornitori pannelloFornitori) {
        this.pannelloFornitori = pannelloFornitori;
        setLayout(new BorderLayout());

        modelloTabella = new DefaultTableModel(
                new Object[]{"ID", "Nome", "Prezzo", "Quantita", "Capacita (GB)", "Tipo", "Fornitore"}, 0);
        tabella = new JTable(modelloTabella);
        add(new JScrollPane(tabella), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));
        form.add(new JLabel("Nome:"));
        form.add(campoNome);
        form.add(new JLabel("Prezzo:"));
        form.add(campoPrezzo);
        form.add(new JLabel("Quantita:"));
        form.add(campoQuantita);
        form.add(new JLabel("Capacita (GB):"));
        form.add(campoCapacita);
        form.add(new JLabel("Tipo (DDR4/DDR5):"));
        form.add(campoTipoMemoria);
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

            MemoriaRam memoria = new MemoriaRam(
                    campoNome.getText().trim(),
                    Double.parseDouble(campoPrezzo.getText().trim()),
                    Integer.parseInt(campoQuantita.getText().trim()),
                    fornitoreScelto.getId(),
                    Integer.parseInt(campoCapacita.getText().trim()),
                    campoTipoMemoria.getText().trim());

            controller.aggiungiMemoriaRam(memoria);

            campoNome.setText("");
            campoPrezzo.setText("");
            campoQuantita.setText("");
            campoCapacita.setText("");
            campoTipoMemoria.setText("");

            aggiornaTabella();

        } catch (NumberFormatException errore) {
            JOptionPane.showMessageDialog(this, "Controlla i valori numerici (prezzo, quantita, capacita).",
                    "Dati non validi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception errore) {
            JOptionPane.showMessageDialog(this, errore.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void elimina() {
        int riga = tabella.getSelectedRow();
        if (riga == -1) {
            JOptionPane.showMessageDialog(this, "Seleziona prima una memoria RAM dalla tabella.");
            return;
        }

        try {
            int id = (int) modelloTabella.getValueAt(riga, 0);
            controller.eliminaMemoriaRam(id);
            aggiornaTabella();

        } catch (Exception errore) {
            JOptionPane.showMessageDialog(this, errore.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void aggiornaTabella() {
        try {
            modelloTabella.setRowCount(0);
            for (MemoriaRam m : controller.elencaMemorieRam()) {
                modelloTabella.addRow(new Object[]{
                        m.getId(), m.getNome(), m.getPrezzo(), m.getQuantita(),
                        m.getCapacitaGb(), m.getTipoMemoria(), m.getNomeFornitore()});
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