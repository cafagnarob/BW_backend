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

    private double prezzoBiglietto;

    //costruttore vuoto
    public PuntoDiEmissione() {
    }

    //costruttore pieno
    public PuntoDiEmissione(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    //getter e setter

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public double getPrezzoBiglietto() {
        return prezzoBiglietto;
    }
    public void setPrezzoBiglietto(double prezzoBiglietto) {
        this.prezzoBiglietto = prezzoBiglietto;
    }

    public String getIndirizzo() {
        return indirizzo;
    }
    public  void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
}

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PuntoDiEmissione{");
        sb.append("id=").append(id);
        sb.append(", indirizzo='").append(indirizzo).append('\'');
        sb.append(", titoliEmessi=").append(titoliEmessi);
        sb.append('}');
        return sb.toString();
    }
}
