package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "rivenditore_autorizzato")
public class Rivenditore extends PuntoDiEmissione {

public String nome;
public double prezzoBiglietto;
    // costruttore vuoto
    public Rivenditore() {
        super();
    }


    //costruttore pieno

    public Rivenditore(String indirizzo) {
        super(indirizzo);
        this.prezzoBiglietto = 1.50;

    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getIndirizzo() {
        return super.getIndirizzo();
    }
    public void setIndirizzo(String indirizzo) {
        super.setIndirizzo(indirizzo);
    }

    public double getPrezzoBiglietto() {
        return prezzoBiglietto;
    }
    public void setPrezzoBiglietto(double prezzoBiglietto) {
        this.prezzoBiglietto = prezzoBiglietto;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Rivenditore{");
        sb.append("nome='").append(nome).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
