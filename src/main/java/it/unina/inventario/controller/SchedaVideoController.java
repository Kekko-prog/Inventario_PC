package it.unina.inventario.controller;

import java.sql.SQLException;
import java.util.List;

import it.unina.inventario.dao.SchedaVideoDAO;
import it.unina.inventario.dao.impl.SchedaVideoDAOImpl;
import it.unina.inventario.eccezioni.DatabaseException;
import it.unina.inventario.model.SchedaVideo;

public class SchedaVideoController {

    private final SchedaVideoDAO schedaVideoDAO = new SchedaVideoDAOImpl();

    public List<SchedaVideo> elencaSchedeVideo() throws DatabaseException {
        try {
             return schedaVideoDAO.trovaTutti();    
        } catch (SQLException e) {
            System.err.println("Errore:" + e.getMessage());

            throw new DatabaseException("Impossibile caricare l'elenco delle schede video.");
        }
    }

    public void aggiungiSchedaVideo(SchedaVideo scheda) throws DatabaseException {
        try {
            schedaVideoDAO.inserisci(scheda);    
        } catch (SQLException e) {
            System.err.println("Errore:" + e.getMessage());

            throw new DatabaseException("Impossibile salvare la scheda video. Verificare la connessione al database.");
        }
    }

    public void eliminaSchedaVideo(int id) throws DatabaseException {
        try {
            schedaVideoDAO.elimina(id);    
        } catch (SQLException e) {
            System.err.println("Errore:" + e.getMessage());

            throw new DatabaseException("Impossibile eleiminare la scheda video selezionata.");
        }
    }
}




        




