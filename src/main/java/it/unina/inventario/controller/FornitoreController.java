package it.unina.inventario.controller;

import java.sql.SQLException;
import java.util.List;

import it.unina.inventario.dao.FornitoreDAO;
import it.unina.inventario.dao.impl.FornitoreDAOImpl;
import it.unina.inventario.model.Fornitore;

public class FornitoreController {

    private final FornitoreDAO fornitoreDAO = new FornitoreDAOImpl();

    public List<Fornitore> elencaFornitori() throws SQLException {
        return fornitoreDAO.trovaTutti();
    }

    public void aggiungiFornitore(Fornitore fornitore) throws SQLException {
        fornitoreDAO.inserisci(fornitore);
    }

    public void eliminaFornitore(int id) throws SQLException {
        fornitoreDAO.elimina(id);
    }
}