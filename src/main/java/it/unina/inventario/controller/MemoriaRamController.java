package it.unina.inventario.controller;

import java.sql.SQLException;
import java.util.List;

import it.unina.inventario.dao.MemoriaRamDAO;
import it.unina.inventario.dao.impl.MemoriaRamDAOImpl;
import it.unina.inventario.model.MemoriaRam;

public class MemoriaRamController {

    private final MemoriaRamDAO memoriaRamDAO = new MemoriaRamDAOImpl();

    public List<MemoriaRam> elencaMemorieRam() throws SQLException {
        return memoriaRamDAO.trovaTutti();
    }

    public void aggiungiMemoriaRam(MemoriaRam memoria) throws SQLException {
        memoriaRamDAO.inserisci(memoria);
    }

    public void eliminaMemoriaRam(int id) throws SQLException {
        memoriaRamDAO.elimina(id);
    }
}