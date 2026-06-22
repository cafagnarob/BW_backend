package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "distributore")
public class Distributore extends Punto_di_Emissione {
    @Enumerated(EnumType.STRING)
    private Stato_Distributore stato;

    public Distributore() {
        super();
    }

    public Distributore(Stato_Distributore stato, String indirizzo) {
        super(indirizzo);
        this.stato = stato;

    }

    //getter e setter
    public Stato_Distributore getStato() {
        return stato;
    }

    public void setStato(Stato_Distributore stato) {
        this.stato = stato;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Distributore{");
        sb.append("stato=").append(stato);
        sb.append('}');
        return sb.toString();
    }
}
