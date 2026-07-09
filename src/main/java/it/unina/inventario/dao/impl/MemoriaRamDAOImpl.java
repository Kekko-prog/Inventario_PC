package it.unina.inventario.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.unina.inventario.dao.MemoriaRamDAO;
import it.unina.inventario.db.ConnessioneDatabase;
import it.unina.inventario.model.MemoriaRam;

public class MemoriaRamDAOImpl implements MemoriaRamDAO {

    @Override
    public List<MemoriaRam> trovaTutti() throws SQLException {
        List<MemoriaRam> lista = new ArrayList<>();

        String sql = "SELECT c.id, c.nome, c.prezzo, c.quantita, c.fornitore_id, "
                   + "       m.capacita_gb, m.tipo_memoria, f.nome AS nome_fornitore "
                   + "FROM componente c "
                   + "JOIN memoria_ram m ON m.componente_id = c.id "
                   + "LEFT JOIN fornitore f ON f.id = c.fornitore_id "
                   + "ORDER BY c.id";

        try (Connection connessione = ConnessioneDatabase.ottieniConnessione();
             Statement comando = connessione.createStatement();
             ResultSet risultato = comando.executeQuery(sql)) {

            while (risultato.next()) {
                MemoriaRam memoria = new MemoriaRam();
                memoria.setId(risultato.getInt("id"));
                memoria.setNome(risultato.getString("nome"));
                memoria.setPrezzo(risultato.getDouble("prezzo"));
                memoria.setQuantita(risultato.getInt("quantita"));
                memoria.setFornitoreId(risultato.getInt("fornitore_id"));
                memoria.setNomeFornitore(risultato.getString("nome_fornitore"));
                memoria.setCapacitaGb(risultato.getInt("capacita_gb"));
                memoria.setTipoMemoria(risultato.getString("tipo_memoria"));
                lista.add(memoria);
            }
        }
        return lista;
    }

    @Override
    public void inserisci(MemoriaRam memoria) throws SQLException {
        String sqlComponente = "INSERT INTO componente (nome, prezzo, quantita, tipo, fornitore_id) "
                              + "VALUES (?, ?, ?, 'MEMORIA_RAM', ?)";
        String sqlMemoria = "INSERT INTO memoria_ram (componente_id, capacita_gb, tipo_memoria) VALUES (?, ?, ?)";

        try (Connection connessione = ConnessioneDatabase.ottieniConnessione()) {

            connessione.setAutoCommit(false);
            try {
                int nuovoId;
                try (PreparedStatement comando = connessione.prepareStatement(sqlComponente,
                        Statement.RETURN_GENERATED_KEYS)) {
                    comando.setString(1, memoria.getNome());
                    comando.setDouble(2, memoria.getPrezzo());
                    comando.setInt(3, memoria.getQuantita());
                    comando.setInt(4, memoria.getFornitoreId());
                    comando.executeUpdate();

                    try (ResultSet chiavi = comando.getGeneratedKeys()) {
                        chiavi.next();
                        nuovoId = chiavi.getInt(1);
                    }
                }

                try (PreparedStatement comando = connessione.prepareStatement(sqlMemoria)) {
                    comando.setInt(1, nuovoId);
                    comando.setInt(2, memoria.getCapacitaGb());
                    comando.setString(3, memoria.getTipoMemoria());
                    comando.executeUpdate();
                }

                connessione.commit();
            } catch (SQLException e) {
                connessione.rollback();
                throw e;
            }
        }
    }

    @Override
    public void elimina(int id) throws SQLException {
        String sql = "DELETE FROM componente WHERE id = ?";

        try (Connection connessione = ConnessioneDatabase.ottieniConnessione();
             PreparedStatement comando = connessione.prepareStatement(sql)) {

            comando.setInt(1, id);
            comando.executeUpdate();
        }
    }
}