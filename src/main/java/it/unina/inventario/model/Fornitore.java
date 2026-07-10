package it.unina.inventario.model;

import it.unina.inventario.eccezioni.ValidazioneException;

public class Fornitore {

    private int id;
    private String nome;
    private String telefono;
    private String email;

    public Fornitore() {
    }

    public Fornitore(String nome, String telefono, String email) throws ValidazioneException {
        if (nome == null || nome.trim().isBlank()) {
            throw new ValidazioneException("Il nome del fornitore è obbligatorio.");
        }
        if (telefono == null || telefono.trim().isBlank()) {
            throw new ValidazioneException("Il numero di telefono è obbligatorio.");
        }
        if (email == null || email.trim().isBlank()) {
            throw new ValidazioneException("L'indirizzo email è obbligatorio.");
        }
        if (!email.contains("@") || !email.contains(".")) {
            throw new ValidazioneException("L'email deve contenere almeno una '@' e un punto (es. info@fornitore.com).");
        }
        
        
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