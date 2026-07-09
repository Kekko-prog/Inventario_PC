package it.unina.inventario.controller;

import java.sql.SQLException;
import java.util.List;

import it.unina.inventario.dao.ProcessoreDAO;
import it.unina.inventario.dao.impl.ProcessoreDAOImpl;
import it.unina.inventario.model.Processore;

public class ProcessoreController {

    private final ProcessoreDAO processoreDAO = new ProcessoreDAOImpl();

    public List<Processore> elencaProcessori() throws SQLException {
        return processoreDAO.trovaTutti();
    }

    public void aggiungiProcessore(Processore processore) throws SQLException {
        processoreDAO.inserisci(processore);
    }

    public void eliminaProcessore(int id) throws SQLException {
        processoreDAO.elimina(id);
    }
}