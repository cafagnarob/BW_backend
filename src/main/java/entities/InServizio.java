package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class InServizio {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "id_mezzo", nullable = false)
    private Mezzo mezzo;

    @Column(name = "id_tratta", nullable = false)
    private Tratta tratta;

    @Column(name = "tempo_di_percorrenza_effettivo", nullable = false)
    private int tempoPercorrenzaEffettivo;

    @Column(name = "num_volte")
    private int numVolte;

    public InServizio() {
    }

    public InServizio(Mezzo mezzo, Tratta tratta, int numVolte) {
        this.mezzo = mezzo;
        this.tratta = tratta;
        this.tempoPercorrenzaEffettivo = // getTempodipercorrenzamedia * numVolte
                this.numVolte = numVolte;
    }

    public long getId() {
        return id;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    public Tratta getTratta() {
        return tratta;
    }

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
    }

    public int getTempoPercorrenzaEffettivo() {
        return tempoPercorrenzaEffettivo;
    }

    public int getNumVolte() {
        return numVolte;
    }

    public void setNumVolte(int numVolte) {
        this.numVolte = numVolte;
    }

    @Override
    public String toString() {
        return "In servizio{ \n" +
                "id=" + id + "\n" +
                ", mezzo= " + mezzo + "\n" +
                ", tratta: " + tratta + "\n" +
                ", numero di volte= " + numVolte + "\n" +
                "} \n";
    }

}
