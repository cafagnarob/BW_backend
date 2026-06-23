package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Biglietto extends TitoloDiViaggio {

    @Column(name = "orario_inizio_corsa")
    private LocalTime orarioInizioCorsa;

    @Column(name = "orario_fine_corsa")
    private LocalTime orarioFineCorsa;

    @Column(name = "orario_vidimazione")
    private LocalTime orarioVidimazione;

    @ManyToOne
    @JoinColumn(name = "id_mezzo")
    private Mezzo mezzo;

    public Biglietto() {
    }

    public Biglietto(LocalDate dataDiEmissione, PuntoDiEmissione luogo_di_emissione, double prezzo,
                     LocalTime orarioInizioCorsa, LocalTime OrarioFineCorsa, LocalTime orarioVidimazione, Mezzo mezzo) {
        super(dataDiEmissione, luogo_di_emissione, prezzo);
        this.orarioInizioCorsa = null;
        this.orarioFineCorsa = null;
        this.orarioVidimazione = null;
        this.mezzo = null;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    public LocalTime getOrarioFineCorsa() {
        return orarioFineCorsa;
    }

    public void setOrarioFineCorsa(LocalTime orarioFineCorsa) {
        this.orarioFineCorsa = orarioFineCorsa;
    }

    public LocalTime getOrarioInizioCorsa() {
        return orarioInizioCorsa;
    }

    public void setOrarioInizioCorsa(LocalTime orarioInizioCorsa) {
        this.orarioInizioCorsa = orarioInizioCorsa;
    }

    public LocalTime getOrarioVidimazione() {
        return orarioVidimazione;
    }

    public void setOrarioVidimazione(LocalTime orarioVidimazione) {
        this.orarioVidimazione = orarioVidimazione;
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
