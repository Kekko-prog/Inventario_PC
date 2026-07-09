package it.unina.inventario.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnessioneDatabase {
    
    private static final String URL = "jdbc:postgresql://localhost:5432/inventario_pc";

    private static final String UTENTE = "postgres";
    private static final String PASSWORD = "postgres";

    public static Connection ottieniConnessione() throws SQLException {
        Connection connessione = DriverManager.getConnection(URL, UTENTE, PASSWORD);
        System.out.println("Connessione al database stabilita con successo!");
        return connessione;
    }
}




//public static Connection ottieniConnessione() throws SQLException {
  //      return DriverManager.getConnection(URL, UTENTE, PASSWORD);
//}