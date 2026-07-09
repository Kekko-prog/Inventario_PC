package it.unina.inventario.model;

public class MemoriaRam extends Componente {

    private int capacitaGb;
    private String tipoMemoria;

    public MemoriaRam() {
        super();
    }

    public MemoriaRam(String nome, double prezzo, int quantita, int fornitoreId, int capacitaGb, String tipoMemoria) {
        super(nome, prezzo, quantita, fornitoreId);
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