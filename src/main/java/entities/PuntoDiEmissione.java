package entities;


import jakarta.persistence.*;

@Entity
@Table(name = "punto_di_emissione")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PuntoDiEmissione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OneToMany(mappedBy = "luogoDiEmissione")
    public long id;
    public String indirizzo;


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
        final StringBuilder sb = new StringBuilder("Punto_di_Emissione{");
        sb.append("id=").append(id);
        sb.append(", indirizzo='").append(indirizzo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
