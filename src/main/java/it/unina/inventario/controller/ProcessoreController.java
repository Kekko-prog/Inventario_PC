package it.unina.inventario.controller;

import java.sql.SQLException;
import java.util.List;

import it.unina.inventario.dao.ProcessoreDAO;
import it.unina.inventario.dao.impl.ProcessoreDAOImpl;
import it.unina.inventario.eccezioni.DatabaseException;
import it.unina.inventario.model.Processore;

public class ProcessoreController {

    private final ProcessoreDAO processoreDAO = new ProcessoreDAOImpl();

    public List<Processore> elencaProcessori() throws DatabaseException {
        try {
            return processoreDAO.trovaTutti();     
        } catch (SQLException e) {
            System.err.println("Errore:" + e.getMessage());

            throw new DatabaseException("Impossibile caricare l'elenco dei processori.");
        }
    }

    public void aggiungiProcessore(Processore processore) throws DatabaseException {
        try {
            processoreDAO.inserisci(processore);
        } catch (SQLException e) {
            System.err.println("Errore:" + e.getMessage());

            throw new DatabaseException("Impossibile salvare il processore. Verificare la connessione al database.");
        }
    }

    // public void aggiungiProcessore(Processore processore) throws SQLException {
    //     processoreDAO.inserisci(processore);
    // }

    public void eliminaProcessore(int id) throws DatabaseException {
        try{
            processoreDAO.elimina(id);
        } catch (SQLException e) {
            System.err.println("Errore tecnico: " + e.getMessage());

            throw new DatabaseException("Impossibile eliminare il processore selezionato.");
        }
        
    }
}