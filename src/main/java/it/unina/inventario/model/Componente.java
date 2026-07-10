package it.unina.inventario.model;

import it.unina.inventario.eccezioni.ValidazioneException;
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

    public Componente(String nome, double prezzo, int quantita, int fornitoreId) throws ValidazioneException {
        
        //Controlli per i componenti che verranno ereditati dalle sottoclassi Processore, SchedaVideo e MemoriaRam
        if (nome == null || nome.trim().isEmpty()){
            throw new ValidazioneException("Il nome non puo' essere vuoto.");
        }
        if (prezzo <= 0) {
            throw new ValidazioneException("Il prezzo deve essere maggiore di zero.");
        }
        if (quantita < 0) {
            throw new ValidazioneException("La quantita' non puo' essere negativa.");
        }
        if (fornitoreId == 0) {
            throw new ValidazioneException("Selezionare un id fornitore valido");
        }



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