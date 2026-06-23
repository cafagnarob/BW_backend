package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Biglietto extends TitoloDiViaggio {
    @Column(nullable = false)
    private boolean vidimato;
    @ManyToOne
    @JoinColumn(name = "id_mezzo")
    private Mezzo mezzo;

    public Biglietto() {
    }

    public Biglietto(Long id, PuntoDiEmissione luogo_di_emissione, double prezzo) {
        super(id, luogo_di_emissione, prezzo);
        this.vidimato = false;


    }

    public boolean isVidimato() {
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
