package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Biglietto extends TitoloDiViaggio {

    @Column(name = "orario_vidimazione")
    private LocalDateTime orarioVidimazione;

    @ManyToOne
    @JoinColumn(name = "id_mezzo")
    private Mezzo mezzo;

    protected Biglietto() {
    }

    public Biglietto(LocalDate dataDiEmissione, PuntoDiEmissione luogo_di_emissione, double prezzo, LocalDateTime orarioVidimazione,
                     Mezzo mezzo) {
        super(dataDiEmissione, luogo_di_emissione, prezzo);
        this.orarioVidimazione = orarioVidimazione;
        this.mezzo = mezzo;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }


    public LocalDateTime getOrarioVidimazione() {
        return orarioVidimazione;
    }

    public void setOrarioVidimazione(LocalDateTime orarioVidimazione) {
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
