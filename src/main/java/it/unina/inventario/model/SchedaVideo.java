package it.unina.inventario.model;

public class SchedaVideo extends Componente{

    private int memoriaVram;
    private String chipset;

    public SchedaVideo() {
        super();
    }

    public SchedaVideo(String nome, double prezzo, int quantita, int fornitoreId, int memoriaVram, String chipset) {
        super(nome, prezzo, quantita, fornitoreId);
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
