package it.unina.inventario.controller;

import java.sql.SQLException;
import java.util.List;

import it.unina.inventario.dao.FornitoreDAO;
import it.unina.inventario.dao.impl.FornitoreDAOImpl;
import it.unina.inventario.eccezioni.DatabaseException;
import it.unina.inventario.model.Fornitore;

public class FornitoreController {

    private final FornitoreDAO fornitoreDAO = new FornitoreDAOImpl();

    public List<Fornitore> elencaFornitori() throws DatabaseException {
        try {
            return fornitoreDAO.trovaTutti();
        } catch (SQLException e) {
            System.err.println("Errore: " + e.getMessage());

            throw new DatabaseException("Impossibile caricare l'elenco dei fornitori.");
        }
    }

    public void aggiungiFornitore(Fornitore fornitore) throws DatabaseException {
        try {
            fornitoreDAO.inserisci(fornitore);
        } catch (SQLException e) {
            System.err.println("Errore: " + e.getMessage());

            throw  new DatabaseException("Impossibile salvare il fornitore. Verificare la connessione al database");
        }
    }

    public void eliminaFornitore(int id) throws DatabaseException {
        try {
            fornitoreDAO.elimina(id);
        } catch (SQLException e) {
            System.err.println("Errore: " + e.getMessage());

            throw new DatabaseException("Impossibile eliminare il fornitore selezionato");
        }
    }
}