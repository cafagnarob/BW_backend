package entities;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "punto_di_emissione")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PuntoDiEmissione {
    @Column(nullable = false)
    public String indirizzo;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
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


    public String getIndirizzo() {
        return indirizzo;
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
