package it.unina.inventario.controller;

import java.sql.SQLException;
import java.util.List;

import it.unina.inventario.dao.MemoriaRamDAO;
import it.unina.inventario.dao.impl.MemoriaRamDAOImpl;
import it.unina.inventario.eccezioni.DatabaseException;
import it.unina.inventario.model.MemoriaRam;

public class MemoriaRamController {

    private final MemoriaRamDAO memoriaRamDAO = new MemoriaRamDAOImpl();

    public List<MemoriaRam> elencaMemorieRam() throws DatabaseException {
        try {
            return memoriaRamDAO.trovaTutti();
        } catch (SQLException e) {
            System.err.println("Errore: " + e.getMessage());

            throw new DatabaseException("Impossibile caricare l'elenco delle memorie RAM.");
        }
    }

    public void aggiungiMemoriaRam(MemoriaRam memoria) throws DatabaseException {
        try {
            memoriaRamDAO.inserisci(memoria);
        } catch (SQLException e) {
            System.err.println("Errore: " + e.getMessage());

            throw new DatabaseException("Impossibile salvare la memoria RAM. Verificare la connessione al database.");
        }
    }

    public void eliminaMemoriaRam(int id) throws DatabaseException {
        try {
            memoriaRamDAO.elimina(id);
        } catch (SQLException e) {
            System.err.println("Errore: " + e.getMessage());

            throw new DatabaseException("Ipossibile eliminare il processore selezionato.");
        }
    }
}