package it.unina.inventario.model;

public class Fornitore {

    private int id;
    private String nome;
    private String telefono;
    private String email;

    public Fornitore() {
    }

    public Fornitore(String nome, String telefono, String email) {
        this.nome = nome;
        this.telefono = telefono;
        this.email = email;
    }

    //metodi getter e setter
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Usato per mostrare il nome del fornitore dentro le JComboBox
    @Override
    public String toString() {
        return nome;
    }
}