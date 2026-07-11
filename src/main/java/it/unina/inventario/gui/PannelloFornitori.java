package it.unina.inventario.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import it.unina.inventario.controller.FornitoreController;
import it.unina.inventario.model.Fornitore;

public class PannelloFornitori extends JPanel {

    private final FornitoreController controller = new FornitoreController();

    private final DefaultTableModel modelloTabella;
    private final JTable tabella;

    private final JTextField campoNome = new JTextField();
    private final JTextField campoTelefono = new JTextField();
    private final JTextField campoEmail = new JTextField();

    public PannelloFornitori() {
        setLayout(new BorderLayout());

        modelloTabella = new DefaultTableModel(new Object[]{"ID", "Nome", "Telefono", "Email"}, 0);
        tabella = new JTable(modelloTabella);
        add(new JScrollPane(tabella), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));
        form.add(new JLabel("Nome:"));
        form.add(campoNome);
        form.add(new JLabel("Telefono:"));
        form.add(campoTelefono);
        form.add(new JLabel("Email:"));
        form.add(campoEmail);

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
            Fornitore fornitore = new Fornitore(
                    campoNome.getText().trim(),
                    campoTelefono.getText().trim(),
                    campoEmail.getText().trim());

            controller.aggiungiFornitore(fornitore);

            campoNome.setText("");
            campoTelefono.setText("");
            campoEmail.setText("");

            aggiornaTabella();

        } catch (Exception errore) {
            JOptionPane.showMessageDialog(this, errore.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void elimina() {
        int riga = tabella.getSelectedRow();
        if (riga == -1) {
            JOptionPane.showMessageDialog(this, "Seleziona prima un fornitore dalla tabella.");
            return;
        }

        try {
            int id = (int) modelloTabella.getValueAt(riga, 0);
            controller.eliminaFornitore(id);
            aggiornaTabella();

        } catch (Exception errore) {
            JOptionPane.showMessageDialog(this, errore.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void aggiornaTabella() {
        try {
            modelloTabella.setRowCount(0);
            for (Fornitore f : controller.elencaFornitori()) {
                modelloTabella.addRow(new Object[]{f.getId(), f.getNome(), f.getTelefono(), f.getEmail()});
            }
        } catch (Exception errore) {
            JOptionPane.showMessageDialog(this, errore.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<Fornitore> ottieniListaFornitori() {
        try {
            return controller.elencaFornitori();
        } catch (Exception errore) {
            return List.of();
        }
    }
}