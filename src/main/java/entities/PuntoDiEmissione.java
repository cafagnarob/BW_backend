package entities;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "punto_di_emissione")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PuntoDiEmissione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String indirizzo;
    @OneToMany(mappedBy = "luogoDiEmissione", cascade = CascadeType.ALL)
    private List<TitoloDiViaggio> titoliEmessi;

    //costruttore vuoto
    public PuntoDiEmissione() {
    }

    //costruttore pieno
    public PuntoDiEmissione(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    //getter e setter
//
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    @Override
    public String toString() {
        return "PuntoDiEmissione{" +
                "id=" + id +
                ", indirizzo='" + indirizzo + '\'' +
                '}';
    }
}
