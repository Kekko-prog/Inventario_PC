package it.unina.inventario.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
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

    private JTable tabella;
    private DefaultTableModel modelloTabella;

    private JTextField campoNome;
    private JTextField campoTelefono;
    private JTextField campoEmail;

    public PannelloFornitori() {
        setLayout(new BorderLayout());
        creaTabella();
        creaFormInserimento();
        aggiornaTabella();
    }

    private void creaTabella() {
        modelloTabella = new DefaultTableModel(
                new Object[]{"ID", "Nome", "Telefono", "Email"}, 0) {
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
        campoTelefono = new JTextField();
        campoEmail = new JTextField();

        pannelloForm.add(new JLabel("Nome:"));
        pannelloForm.add(campoNome);
        pannelloForm.add(new JLabel("Telefono:"));
        pannelloForm.add(campoTelefono);
        pannelloForm.add(new JLabel("Email:"));
        pannelloForm.add(campoEmail);

        JButton bottoneSalva = new JButton("Salva");
        bottoneSalva.addActionListener(e -> salvaFornitore());

        JButton bottoneElimina = new JButton("Elimina selezionato");
        bottoneElimina.addActionListener(e -> eliminaFornitoreSelezionato());

        JPanel pannelloBottoni = new JPanel();
        pannelloBottoni.add(bottoneSalva);
        pannelloBottoni.add(bottoneElimina);

        JPanel pannelloSud = new JPanel(new BorderLayout());
        pannelloSud.add(pannelloForm, BorderLayout.CENTER);
        pannelloSud.add(pannelloBottoni, BorderLayout.SOUTH);

        add(pannelloSud, BorderLayout.SOUTH);
    }

    private void salvaFornitore() {
        try {
            String nome = campoNome.getText().trim();
            String telefono = campoTelefono.getText().trim();
            String email = campoEmail.getText().trim();

            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Il nome del fornitore e' obbligatorio.");
                return;
            }

            Fornitore fornitore = new Fornitore(nome, telefono, email);
            controller.aggiungiFornitore(fornitore);

            svuotaCampi();
            aggiornaTabella();

        } catch (SQLException errore) {
            JOptionPane.showMessageDialog(this, "Errore database: " + errore.getMessage(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminaFornitoreSelezionato() {
        int rigaSelezionata = tabella.getSelectedRow();
        if (rigaSelezionata == -1) {
            JOptionPane.showMessageDialog(this, "Seleziona prima un fornitore dalla tabella.");
            return;
        }
        int id = (int) modelloTabella.getValueAt(rigaSelezionata, 0);

        try {
            controller.eliminaFornitore(id);
            aggiornaTabella();
        } catch (SQLException errore) {
            JOptionPane.showMessageDialog(this, "Errore database: " + errore.getMessage(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void svuotaCampi() {
        campoNome.setText("");
        campoTelefono.setText("");
        campoEmail.setText("");
    }

    public void aggiornaTabella() {
        try {
            modelloTabella.setRowCount(0);
            List<Fornitore> fornitori = controller.elencaFornitori();
            for (Fornitore f : fornitori) {
                modelloTabella.addRow(new Object[]{
                        f.getId(), f.getNome(), f.getTelefono(), f.getEmail()
                });
            }
        } catch (SQLException errore) {
            JOptionPane.showMessageDialog(this, "Errore database: " + errore.getMessage(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<Fornitore> ottieniListaFornitori() {
        try {
            return controller.elencaFornitori();
        } catch (SQLException errore) {
            JOptionPane.showMessageDialog(this, "Errore database: " + errore.getMessage(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
            return List.of();
        }
    }
}