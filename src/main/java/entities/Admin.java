package entities;

import jakarta.persistence.Entity;

@Entity
public class Admin extends Utente {

    public Admin() {
    }

    public Admin(String nome, String cognome, String email, int eta) {
        super(nome, cognome, email, eta);
    }

    // metodi di admin
}
