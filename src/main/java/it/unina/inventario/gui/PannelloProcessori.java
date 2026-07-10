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

import it.unina.inventario.controller.ProcessoreController;
import it.unina.inventario.eccezioni.DatabaseException;
import it.unina.inventario.eccezioni.ValidazioneException;
import it.unina.inventario.model.Fornitore;
import it.unina.inventario.model.Processore;

public class PannelloProcessori extends JPanel {

    private final ProcessoreController controller = new ProcessoreController();
    private final PannelloFornitori pannelloFornitori;

    private JTable tabella;
    private DefaultTableModel modelloTabella;

    private JTextField campoNome;
    private JTextField campoPrezzo;
    private JTextField campoQuantita;
    private JTextField campoNumeroCore;
    private JTextField campoSocket;
    private JComboBox<Fornitore> comboFornitore;

    public PannelloProcessori(PannelloFornitori pannelloFornitori) {
        this.pannelloFornitori = pannelloFornitori;
        setLayout(new BorderLayout());
        creaTabella();
        creaFormInserimento();
        aggiornaTabella();
    }

    private void creaTabella() {
        modelloTabella = new DefaultTableModel(
                new Object[]{"ID", "Nome", "Prezzo", "Quantita", "Core", "Socket", "Fornitore"}, 0) {
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
        campoNumeroCore = new JTextField();
        campoSocket = new JTextField();
        comboFornitore = new JComboBox<>();

        pannelloForm.add(new JLabel("Nome:"));
        pannelloForm.add(campoNome);
        pannelloForm.add(new JLabel("Prezzo:"));
        pannelloForm.add(campoPrezzo);
        pannelloForm.add(new JLabel("Quantita:"));
        pannelloForm.add(campoQuantita);
        pannelloForm.add(new JLabel("Numero core:"));
        pannelloForm.add(campoNumeroCore);
        pannelloForm.add(new JLabel("Socket:"));
        pannelloForm.add(campoSocket);
        pannelloForm.add(new JLabel("Fornitore:"));
        pannelloForm.add(comboFornitore);

        JButton bottoneSalva = new JButton("Salva");
        bottoneSalva.addActionListener(e -> salvaProcessore());

        JButton bottoneElimina = new JButton("Elimina selezionato");
        bottoneElimina.addActionListener(e -> eliminaProcessoreSelezionato());

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

    private void salvaProcessore() {
        try {
            String nome = campoNome.getText().trim();
            double prezzo = Double.parseDouble(campoPrezzo.getText().trim());
            int quantita = Integer.parseInt(campoQuantita.getText().trim());
            int numeroCore = Integer.parseInt(campoNumeroCore.getText().trim());
            String socket = campoSocket.getText().trim();

            Fornitore fornitoreScelto = (Fornitore) comboFornitore.getSelectedItem();
            if (fornitoreScelto == null) {
                JOptionPane.showMessageDialog(this, "Seleziona un fornitore.");
                return;
            }

            Processore processore = new Processore(nome, prezzo, quantita,
                    fornitoreScelto.getId(), numeroCore, socket);

            controller.aggiungiProcessore(processore);
            svuotaCampi();
            aggiornaTabella();

        } catch (NumberFormatException errore) {
            JOptionPane.showMessageDialog(this, "Controlla i valori numerici inseriti (prezzo, quantita, core).",
                    "Dati non validi", JOptionPane.ERROR_MESSAGE);
        } catch (ValidazioneException errore) {
            JOptionPane.showMessageDialog(this, errore.getMessage(),
                    "Attenzione", JOptionPane.WARNING_MESSAGE);
        } catch (DatabaseException errore) {
            JOptionPane.showMessageDialog(this, errore.getMessage(),
                    "Errore di Sistema", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminaProcessoreSelezionato() {
        int rigaSelezionata = tabella.getSelectedRow();
        if (rigaSelezionata == -1) {
            JOptionPane.showMessageDialog(this, "Seleziona prima un processore dalla tabella.");
            return;
        }
        int id = (int) modelloTabella.getValueAt(rigaSelezionata, 0);

        try {
            controller.eliminaProcessore(id);
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
        campoNumeroCore.setText("");
        campoSocket.setText("");
    }

    public void aggiornaTabella() {
        try {
            modelloTabella.setRowCount(0);
            List<Processore> processori = controller.elencaProcessori();
            for (Processore p : processori) {
                modelloTabella.addRow(new Object[]{
                        p.getId(), p.getNome(), p.getPrezzo(), p.getQuantita(),
                        p.getNumeroCore(), p.getSocket(), p.getNomeFornitore()
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