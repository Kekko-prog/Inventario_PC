package it.unina.inventario.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.unina.inventario.dao.SchedaVideoDAO;
import it.unina.inventario.db.ConnessioneDatabase;
import it.unina.inventario.model.SchedaVideo;

public class SchedaVideoDAOImpl implements SchedaVideoDAO {

    @Override
    public List<SchedaVideo> trovaTutti() throws SQLException {
        List<SchedaVideo> lista = new ArrayList<>();

        String sql = "SELECT c.id, c.nome, c.prezzo, c.quantita, c.fornitore_id, "
                   + "       s.memoria_vram, s.chipset, f.nome AS nome_fornitore "
                   + "FROM componente c "
                   + "JOIN scheda_video s ON s.componente_id = c.id "
                   + "LEFT JOIN fornitore f ON f.id = c.fornitore_id "
                   + "ORDER BY c.id";

        try (Connection connessione = ConnessioneDatabase.ottieniConnessione();
             Statement comando = connessione.createStatement();
             ResultSet risultato = comando.executeQuery(sql)) {

            while (risultato.next()) {
                SchedaVideo scheda = new SchedaVideo();
                scheda.setId(risultato.getInt("id"));
                scheda.setNome(risultato.getString("nome"));
                scheda.setPrezzo(risultato.getDouble("prezzo"));
                scheda.setQuantita(risultato.getInt("quantita"));
                scheda.setFornitoreId(risultato.getInt("fornitore_id"));
                scheda.setNomeFornitore(risultato.getString("nome_fornitore"));
                scheda.setMemoriaVram(risultato.getInt("memoria_vram"));
                scheda.setChipset(risultato.getString("chipset"));
                lista.add(scheda);
            }
        }
        return lista;
    }

    @Override
    public void inserisci(SchedaVideo scheda) throws SQLException {
        String sqlComponente = "INSERT INTO componente (nome, prezzo, quantita, tipo, fornitore_id) "
                              + "VALUES (?, ?, ?, 'SCHEDA_VIDEO', ?)";
        String sqlScheda = "INSERT INTO scheda_video (componente_id, memoria_vram, chipset) VALUES (?, ?, ?)";

        try (Connection connessione = ConnessioneDatabase.ottieniConnessione()) {

            connessione.setAutoCommit(false);
            try {
                int nuovoId;
                try (PreparedStatement comando = connessione.prepareStatement(sqlComponente,
                        Statement.RETURN_GENERATED_KEYS)) {
                    comando.setString(1, scheda.getNome());
                    comando.setDouble(2, scheda.getPrezzo());
                    comando.setInt(3, scheda.getQuantita());
                    comando.setInt(4, scheda.getFornitoreId());
                    comando.executeUpdate();

                    try (ResultSet chiavi = comando.getGeneratedKeys()) {
                        chiavi.next();
                        nuovoId = chiavi.getInt(1);
                    }
                }

                try (PreparedStatement comando = connessione.prepareStatement(sqlScheda)) {
                    comando.setInt(1, nuovoId);
                    comando.setInt(2, scheda.getMemoriaVram());
                    comando.setString(3, scheda.getChipset());
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