package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

@Entity
public class Biglietto extends TitoloDiViaggio {

    @Column(name = "orario_inizio_corsa")
    private LocalDate orarioInizioCorsa;

    @Column(name = "orario_fine_corsa")
    private LocalDate orarioFineCorsa;

    @Column(name = "orario_vidimazione")
    private LocalDate orarioVidimazione;

    @ManyToOne
    @JoinColumn(name = "id_mezzo")
    private Mezzo mezzo;

    public Biglietto() {
    }

    public Biglietto(LocalDate dataDiEmissione, PuntoDiEmissione luogo_di_emissione, double prezzo) {
        super(dataDiEmissione, luogo_di_emissione, prezzo);

    }

    @Override
    public String toString() {
        return "Biglietto{ " +
                "id= " + getId() +
                ", emesso il: " + getDataDiEmissione() +
                ", prezzo=" + getPrezzo() +
                ", vidimato il: " + (orarioVidimazione != null ? orarioVidimazione : "Non ancora usato") +
                " }";
    }
}
