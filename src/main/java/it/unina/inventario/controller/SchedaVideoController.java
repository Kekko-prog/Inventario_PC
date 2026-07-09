package it.unina.inventario.controller;

import java.sql.SQLException;
import java.util.List;

import it.unina.inventario.dao.SchedaVideoDAO;
import it.unina.inventario.dao.impl.SchedaVideoDAOImpl;
import it.unina.inventario.model.SchedaVideo;

public class SchedaVideoController {

    private final SchedaVideoDAO schedaVideoDAO = new SchedaVideoDAOImpl();

    public List<SchedaVideo> elencaSchedeVideo() throws SQLException {
        return schedaVideoDAO.trovaTutti();
    }

    public void aggiungiSchedaVideo(SchedaVideo scheda) throws SQLException {
        schedaVideoDAO.inserisci(scheda);
    }

    public void eliminaSchedaVideo(int id) throws SQLException {
        schedaVideoDAO.elimina(id);
    }
}
