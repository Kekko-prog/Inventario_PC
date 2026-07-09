package it.unina.inventario.model;

public class Processore extends Componente {

    private int numeroCore;
    private String socket;

    public Processore() {
        super();
    }

    public Processore(String nome, double prezzo, int quantita, int fornitoreId, int numeroCore, String socket) {
        super(nome, prezzo, quantita, fornitoreId);
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
