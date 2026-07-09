package it.unina.inventario.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.unina.inventario.dao.FornitoreDAO;
import it.unina.inventario.db.ConnessioneDatabase;
import it.unina.inventario.model.Fornitore;

public class FornitoreDAOImpl implements FornitoreDAO {

    @Override
    public List<Fornitore> trovaTutti() throws SQLException {
        List<Fornitore> lista = new ArrayList<>();
        String sql = "SELECT id, nome, telefono, email FROM fornitore ORDER BY nome";

        try (Connection connessione = ConnessioneDatabase.ottieniConnessione();
             Statement comando = connessione.createStatement();
             ResultSet risultato = comando.executeQuery(sql)) {

            while (risultato.next()) {
                Fornitore fornitore = new Fornitore();
                fornitore.setId(risultato.getInt("id"));
                fornitore.setNome(risultato.getString("nome"));
                fornitore.setTelefono(risultato.getString("telefono"));
                fornitore.setEmail(risultato.getString("email"));
                lista.add(fornitore);
            }
        }
        return lista;
    }

    @Override
    public void inserisci(Fornitore fornitore) throws SQLException {
        String sql = "INSERT INTO fornitore (nome, telefono, email) VALUES (?, ?, ?)";

        try (Connection connessione = ConnessioneDatabase.ottieniConnessione();
             PreparedStatement comando = connessione.prepareStatement(sql)) {

            comando.setString(1, fornitore.getNome());
            comando.setString(2, fornitore.getTelefono());
            comando.setString(3, fornitore.getEmail());
            comando.executeUpdate();
        }
    }

    @Override
    public void elimina(int id) throws SQLException {
        String sql = "DELETE FROM fornitore WHERE id = ?";

        try (Connection connessione = ConnessioneDatabase.ottieniConnessione();
             PreparedStatement comando = connessione.prepareStatement(sql)) {

            comando.setInt(1, id);
            comando.executeUpdate();
        }
    }
}