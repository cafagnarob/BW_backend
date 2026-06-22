package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Viaggiatore extends Utente {

    @Column(unique = true)
    private Tessera tessera;


    private List<Biglietto> lista_biglietti = new ArrayList<>();

    public Viaggiatore() {
    }

    public Viaggiatore(String nome, String cognome, String email, int eta) {
        super(nome, cognome, email, eta);
        this.tessera = null;
    }


    //netodi viaggiatore


    public List<Biglietto> getLista_biglietti() {
        return lista_biglietti;
    }

    public void setLista_biglietti(List<Biglietto> lista_biglietti) {
        this.lista_biglietti = lista_biglietti;
    }

    public Tessera getTessera() {
        return tessera;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }

    @Override
    public String toString() {
        return "Viaggiatore{ \n"
                + super.toString() +
                "tessera=" + tessera + "\n" +
                ", lista_biglietti=" + lista_biglietti + "\n" +
                "} \n ";
    }
}

