package it.unina.inventario.dao;

import java.sql.SQLException;
import java.util.List;

import it.unina.inventario.model.Fornitore;

public interface FornitoreDAO {
    List<Fornitore> trovaTutti() throws SQLException;
    void inserisci(Fornitore fornitore) throws SQLException;
    void elimina(int id) throws SQLException;
}