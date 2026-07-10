package it.unina.inventario.eccezioni;

public class DatabaseException extends Exception {
    public DatabaseException(String messaggio) {
        super(messaggio);
    }
}