package it.unina.inventario.model;


/**
 * Classe astratta che rappresenta un generico componente hardware.
 * Le sottoclassi Processore, SchedaVideo e MemoriaRam ereditano da questa.
 */
public abstract class Componente {

    protected int id;
    protected String nome;
    protected double prezzo;
    protected int quantita;
    protected int fornitoreId;
    protected String nomeFornitore; // usato solo per la visualizzazione in tabella

    public Componente() {
    }

    public Componente(String nome, double prezzo, int quantita, int fornitoreId) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.quantita = quantita;
        this.fornitoreId = fornitoreId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public int getFornitoreId() {
        return fornitoreId;
    }

    public void setFornitoreId(int fornitoreId) {
        this.fornitoreId = fornitoreId;
    }

    public String getNomeFornitore() {
        return nomeFornitore;
    }

    public void setNomeFornitore(String nomeFornitore) {
        this.nomeFornitore = nomeFornitore;
    }
}