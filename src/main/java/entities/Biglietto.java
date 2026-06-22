package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Biglietto extends Titolo_di_viaggio {
    @Column(nullable = false)
    private boolean vidimato;

    public Biglietto() {
    }

    public Biglietto(Long id, Punto_di_emissione luogo_di_emissione, double prezzo, boolean vidimato) {
        super(id, luogo_di_emissione, prezzo);
        this.vidimato = false;

    }

    public boolean getVidimato() {
        return vidimato;
    }

    public void setVidimato(boolean vidimato) {
        this.vidimato = vidimato;
    }

    @Override
    public String toString() {
        return "Biglietto{ \n"
                + super.toString() + "\n" +
                "vidimato=" + vidimato + "\n" +
                "} \n";
    }
}
