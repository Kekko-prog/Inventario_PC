package it.unina.inventario.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.unina.inventario.dao.ProcessoreDAO;
import it.unina.inventario.db.ConnessioneDatabase;
import it.unina.inventario.model.Processore;

public class ProcessoreDAOImpl implements ProcessoreDAO {

    @Override
    public List<Processore> trovaTutti() throws SQLException {
        List<Processore> lista = new ArrayList<>();

        String sql = "SELECT c.id, c.nome, c.prezzo, c.quantita, c.fornitore_id, "
                   + "       p.numero_core, p.socket, f.nome AS nome_fornitore "
                   + "FROM componente c "
                   + "JOIN processore p ON p.componente_id = c.id "
                   + "LEFT JOIN fornitore f ON f.id = c.fornitore_id "
                   + "ORDER BY c.id";

        try (Connection connessione = ConnessioneDatabase.ottieniConnessione();
             Statement comando = connessione.createStatement();
             ResultSet risultato = comando.executeQuery(sql)) {

            while (risultato.next()) {
                Processore processore = new Processore();
                processore.setId(risultato.getInt("id"));
                processore.setNome(risultato.getString("nome"));
                processore.setPrezzo(risultato.getDouble("prezzo"));
                processore.setQuantita(risultato.getInt("quantita"));
                processore.setFornitoreId(risultato.getInt("fornitore_id"));
                processore.setNomeFornitore(risultato.getString("nome_fornitore"));
                processore.setNumeroCore(risultato.getInt("numero_core"));
                processore.setSocket(risultato.getString("socket"));
                lista.add(processore);
            }
        }
        return lista;
    }

    @Override
    public void inserisci(Processore processore) throws SQLException {
        String sqlComponente = "INSERT INTO componente (nome, prezzo, quantita, tipo, fornitore_id) "
                              + "VALUES (?, ?, ?, 'PROCESSORE', ?)";
        String sqlProcessore = "INSERT INTO processore (componente_id, numero_core, socket) VALUES (?, ?, ?)";

        try (Connection connessione = ConnessioneDatabase.ottieniConnessione()) {

            connessione.setAutoCommit(false);
            try {
                int nuovoId;
                try (PreparedStatement comando = connessione.prepareStatement(sqlComponente,
                        Statement.RETURN_GENERATED_KEYS)) {
                    comando.setString(1, processore.getNome());
                    comando.setDouble(2, processore.getPrezzo());
                    comando.setInt(3, processore.getQuantita());
                    comando.setInt(4, processore.getFornitoreId());
                    comando.executeUpdate();

                    try (ResultSet chiavi = comando.getGeneratedKeys()) {
                        chiavi.next();
                        nuovoId = chiavi.getInt(1);
                    }
                }

                try (PreparedStatement comando = connessione.prepareStatement(sqlProcessore)) {
                    comando.setInt(1, nuovoId);
                    comando.setInt(2, processore.getNumeroCore());
                    comando.setString(3, processore.getSocket());
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
        // La riga in "processore" viene rimossa in automatico grazie a ON DELETE CASCADE
        String sql = "DELETE FROM componente WHERE id = ?";

        try (Connection connessione = ConnessioneDatabase.ottieniConnessione();
             PreparedStatement comando = connessione.prepareStatement(sql)) {

            comando.setInt(1, id);
            comando.executeUpdate();
        }
    }
}