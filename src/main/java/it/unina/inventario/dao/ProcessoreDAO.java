package it.unina.inventario.dao;

import java.sql.SQLException;
import java.util.List;

import it.unina.inventario.model.Processore;

public interface ProcessoreDAO {
    List<Processore> trovaTutti() throws SQLException;
    void inserisci(Processore processore) throws SQLException;
    void elimina(int id) throws SQLException;
}