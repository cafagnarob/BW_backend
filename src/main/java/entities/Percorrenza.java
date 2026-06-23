package entities;

import jakarta.persistence.*;

@Entity
public class Percorrenza {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @Column(name = "id_mezzo", nullable = false)
    private Mezzo mezzo;

    @ManyToOne
    @Column(name = "id_tratta", nullable = false)
    private Tratta tratta;

    @Column(name = "tempo_di_percorrenza_effettivo", nullable = false)
    private int tempoPercorrenzaEffettivo;


    public Percorrenza() {
    }

    public Percorrenza(Mezzo mezzo, Tratta tratta, int tempoPercorrenzaEffettivo) {
        this.mezzo = mezzo;
        this.tratta = tratta;
        this.tempoPercorrenzaEffettivo = tempoPercorrenzaEffettivo;
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

    public void setTempoDiPercorrenzaEffettivo(int tempoPercorrenzaEffettivo) {
        this.tempoPercorrenzaEffettivo = tempoPercorrenzaEffettivo;
    }


    @Override
    public String toString() {
        return "In servizio{ \n" +
                "id=" + id + "\n" +
                ", mezzo= " + mezzo + "\n" +
                ", tratta: " + tratta + "\n" +
                ", tempo effettivo: " + tempoPercorrenzaEffettivo + " min" +
                "} \n";
    }

}
