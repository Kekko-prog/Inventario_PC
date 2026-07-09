package it.unina.inventario.dao;

import java.sql.SQLException;
import java.util.List;

import it.unina.inventario.model.MemoriaRam;

public interface MemoriaRamDAO {
    List<MemoriaRam> trovaTutti() throws SQLException;
    void inserisci(MemoriaRam memoriaRam) throws SQLException;
    void elimina(int id) throws SQLException;
}