package entities;

import Enum.StatoDistributore;
import jakarta.persistence.*;

@Entity
@Table(name = "distributore")
public class Distributore extends PuntoDiEmissione {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatoDistributore stato;

    public Distributore() {
        super();
    }

    public Distributore(StatoDistributore stato, String indirizzo) {
        super(indirizzo);
        this.stato = stato;

    }

    //getter e setter
    public StatoDistributore getStato() {
        return stato;
    }

    public void setStato(StatoDistributore stato) {
        this.stato = stato;
    }


    @Override
    public String toString() {
        return "Distributore{ \n"
                + super.toString() + "\n" +
                "stato='" + stato + "\n" +
                "} \n";
    }
}
