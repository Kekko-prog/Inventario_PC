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

import it.unina.inventario.controller.ProcessoreController;
import it.unina.inventario.model.Fornitore;
import it.unina.inventario.model.Processore;

public class PannelloProcessori extends JPanel {

    private final ProcessoreController controller = new ProcessoreController();
    private final PannelloFornitori pannelloFornitori;

    private final DefaultTableModel modelloTabella;
    private final JTable tabella;

    private final JTextField campoNome = new JTextField();
    private final JTextField campoPrezzo = new JTextField();
    private final JTextField campoQuantita = new JTextField();
    private final JTextField campoNumeroCore = new JTextField();
    private final JTextField campoSocket = new JTextField();
    private final JComboBox<Fornitore> comboFornitore = new JComboBox<>();

    public PannelloProcessori(PannelloFornitori pannelloFornitori) {
        this.pannelloFornitori = pannelloFornitori;
        setLayout(new BorderLayout());

        modelloTabella = new DefaultTableModel(
                new Object[]{"ID", "Nome", "Prezzo", "Quantita", "Core", "Socket", "Fornitore"}, 0);
        tabella = new JTable(modelloTabella);
        add(new JScrollPane(tabella), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));
        form.add(new JLabel("Nome:"));
        form.add(campoNome);
        form.add(new JLabel("Prezzo:"));
        form.add(campoPrezzo);
        form.add(new JLabel("Quantita:"));
        form.add(campoQuantita);
        form.add(new JLabel("Numero core:"));
        form.add(campoNumeroCore);
        form.add(new JLabel("Socket:"));
        form.add(campoSocket);
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

            Processore processore = new Processore(
                    campoNome.getText().trim(),
                    Double.parseDouble(campoPrezzo.getText().trim()),
                    Integer.parseInt(campoQuantita.getText().trim()),
                    fornitoreScelto.getId(),
                    Integer.parseInt(campoNumeroCore.getText().trim()),
                    campoSocket.getText().trim());

            controller.aggiungiProcessore(processore);

            campoNome.setText("");
            campoPrezzo.setText("");
            campoQuantita.setText("");
            campoNumeroCore.setText("");
            campoSocket.setText("");

            aggiornaTabella();

        } catch (NumberFormatException errore) {
            JOptionPane.showMessageDialog(this, "Controlla i valori numerici (prezzo, quantita, core).",
                    "Dati non validi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception errore) {
            JOptionPane.showMessageDialog(this, errore.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void elimina() {
        int riga = tabella.getSelectedRow();
        if (riga == -1) {
            JOptionPane.showMessageDialog(this, "Seleziona prima un processore dalla tabella.");
            return;
        }

        try {
            int id = (int) modelloTabella.getValueAt(riga, 0);
            controller.eliminaProcessore(id);
            aggiornaTabella();

        } catch (Exception errore) {
            JOptionPane.showMessageDialog(this, errore.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void aggiornaTabella() {
        try {
            modelloTabella.setRowCount(0);
            for (Processore p : controller.elencaProcessori()) {
                modelloTabella.addRow(new Object[]{
                        p.getId(), p.getNome(), p.getPrezzo(), p.getQuantita(),
                        p.getNumeroCore(), p.getSocket(), p.getNomeFornitore()});
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