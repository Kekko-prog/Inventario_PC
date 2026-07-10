package it.unina.inventario.model;

import it.unina.inventario.eccezioni.ValidazioneException;

public class MemoriaRam extends Componente {

    private int capacitaGb;
    private String tipoMemoria;

    public MemoriaRam() {
        super();
    }

    public MemoriaRam(String nome, double prezzo, int quantita, int fornitoreId, int capacitaGb, String tipoMemoria) throws ValidazioneException {
        super(nome, prezzo, quantita, fornitoreId);
        
        // Controlli specifici per il Processore
        if (capacitaGb <= 0) {
            throw new ValidazioneException("La capacità in GB deve essere maggiore di zero (es. 16, 32, 64).");
        }
        if (tipoMemoria == null || tipoMemoria.trim().isBlank()) {
            throw new ValidazioneException("La capacità in GB deve essere maggiore di zero (es. 16, 32, 64).");
        }

        this.capacitaGb = capacitaGb;
        this.tipoMemoria = tipoMemoria;
    }

    // metodi getter e setter
    public int getCapacitaGb() {
        return capacitaGb;
    }

    public void setCapacitaGb(int capacitaGb) {
        this.capacitaGb = capacitaGb;
    }

    public String getTipoMemoria() {
        return tipoMemoria;
    }

    public void setTipoMemoria(String tipoMemoria) {
        this.tipoMemoria = tipoMemoria;
    }
}