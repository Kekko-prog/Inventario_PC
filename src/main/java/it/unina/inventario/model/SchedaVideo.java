package it.unina.inventario.model;

import it.unina.inventario.eccezioni.ValidazioneException;

public class SchedaVideo extends Componente{

    private int memoriaVram;
    private String chipset;

    public SchedaVideo() {
        super();
    }

    public SchedaVideo(String nome, double prezzo, int quantita, int fornitoreId, int memoriaVram, String chipset) throws ValidazioneException {
        super(nome, prezzo, quantita, fornitoreId);
    
        if (memoriaVram <= 0) {
            throw new ValidazioneException("La quantità di VRAM deve essere maggiore di zero (es. 12 per una RTX 5070).");
        }
        if (chipset == null || chipset.trim().isBlank()) {
            throw new ValidazioneException("Il chipset della scheda video è obbligatorio.");
        }
        
        
        this.memoriaVram = memoriaVram;
        this.chipset = chipset;
    }

    public int getMemoriaVram() {
        return memoriaVram;
    }

    public void setMemoriaVram(int memoriaVram) {
        this.memoriaVram = memoriaVram;
    }

    public String getChipset() {
        return chipset;
    }

    public void setChipset(String chipset) {
        this.chipset = chipset;
    }
}
