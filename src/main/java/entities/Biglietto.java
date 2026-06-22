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
    @JoinColumn(name = "viaggiatore_id")
    private Viaggiatore viaggiatore;

    public Biglietto() {
    }

    public Biglietto(Long id, PuntoDiEmissione luogo_di_emissione, double prezzo) {
        super(id, luogo_di_emissione, prezzo);
        this.vidimato = false;
        this.viaggiatore = null;

    }

    public boolean isVidimato() {
        return vidimato;
    }

    public void setVidimato(boolean vidimato) {
        this.vidimato = vidimato;
    }

    public Viaggiatore getViaggiatore() {
        return viaggiatore;
    }

    public void setViaggiatore(Viaggiatore viaggiatore) {
        this.viaggiatore = viaggiatore;
    }

    @Override
    public String toString() {
        return "Biglietto{ \n"
                + super.toString() + "\n" +
                "vidimato=" + vidimato + "\n" +
                "} \n";
    }
}
