package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "rivenditore_autorizzato")
public class Rivenditore extends PuntoDiEmissione {


    // costruttore vuoto
    public Rivenditore() {
        super();
    }


    //costruttore pieno

    public Rivenditore(String indirizzo) {
        super(indirizzo);

    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Rivenditore{");
        sb.append('}');
        return sb.toString();
    }
}
