package it.unina.inventario.dao;

import java.sql.SQLException;
import java.util.List;

import it.unina.inventario.model.SchedaVideo;

public interface SchedaVideoDAO {
    List<SchedaVideo> trovaTutti() throws SQLException;
    void inserisci(SchedaVideo schedaVideo) throws SQLException;
    void elimina(int id) throws SQLException;
}