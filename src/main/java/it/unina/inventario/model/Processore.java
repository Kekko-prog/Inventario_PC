package it.unina.inventario.model;

import it.unina.inventario.eccezioni.ValidazioneException;

public class Processore extends Componente {

    private int numeroCore;
    private String socket;

    public Processore() {
        super();
    }

    public Processore(String nome, double prezzo, int quantita, int fornitoreId, int numeroCore, String socket) throws ValidazioneException {
        super(nome, prezzo, quantita, fornitoreId);
        
        // Controlli specifici per il Processore
        if (numeroCore <= 0) {
            throw new ValidazioneException("Il numero di core deve essere maggiore di zero.");
        }
        if (socket == null || socket.trim().isEmpty()) {
            throw new ValidazioneException("Il socket del processore è obbligatorio.");
        }
        
        this.numeroCore = numeroCore;
        this.socket = socket;
    }

    
    public int getNumeroCore() {
        return numeroCore;
    }

    public void setNumeroCore(int numeroCore) {
        this.numeroCore = numeroCore;
    }

    public String getSocket() {
        return socket;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }
}