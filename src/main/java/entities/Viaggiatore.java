package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Viaggiatore extends Utente {

    @Column(unique = true)
    private String codice_univoco_tessera;

    private List<Biglietto> lista_biglietti = new ArrayList<>();

    public Viaggiatore() {
    }

    public Viaggiatore(String nome, String cognome, String email, int eta) {
        super(nome, cognome, email, eta);
        this.codice_univoco_tessera = null;
    }


    //netodi viaggiatore


    public List<Biglietto> getLista_biglietti() {
        return lista_biglietti;
    }

    public void setLista_biglietti(List<Biglietto> lista_biglietti) {
        this.lista_biglietti = lista_biglietti;
    }

    public String getCodice_univoco_tessera() {
        return codice_univoco_tessera;
    }

    public void setCodice_univoco_tessera(String codice_univoco_tessera) {
        this.codice_univoco_tessera = codice_univoco_tessera;
    }


    @Override
    public String toString() {
        return "Viaggiatore{ \n" +
                super.toString() + "\n" +
                "codice_univoco_tessera='" + codice_univoco_tessera + "\n" +
                ", lista_biglietti=" + lista_biglietti + "\n" +
                "} \n";
    }
}
