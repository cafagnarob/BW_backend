package entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Percorrenza {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_mezzo", nullable = false)
    private Mezzo mezzo;

    @ManyToOne
    @JoinColumn(name = "id_tratta", nullable = false)
    private Tratta tratta;

    @Column(name = "tempo_di_percorrenza_effettivo", nullable = false)
    private int tempoPercorrenzaEffettivo;

    @Column(nullable = false)
    private LocalDate data;


    public Percorrenza() {

    }


    public Percorrenza(LocalDate data, Mezzo mezzo, Tratta tratta) {
        this.mezzo = mezzo;
        this.tratta = tratta;
        this.tempoPercorrenzaEffettivo = Integer.parseInt(null);
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public Tratta getTratta() {
        return tratta;
    }

    public int getTempoPercorrenzaEffettivo() {
        return tempoPercorrenzaEffettivo;
    }

    public void setTempoPercorrenzaEffettivo(int tempoPercorrenzaEffettivo) {
        this.tempoPercorrenzaEffettivo = tempoPercorrenzaEffettivo;
    }

    public LocalDate getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Percorrenza{ \n" +
                "id=" + id + "\n" +
                ", mezzo=" + mezzo + "\n" +
                ", tratta=" + tratta + "\n" +
                ", tempoPercorrenzaEffettivo=" + tempoPercorrenzaEffettivo + "\n" +
                ", data=" + data + "\n" +
                "} \n";
    }
}
