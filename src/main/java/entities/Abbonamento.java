package entities;

import Enum.TipoAbbonamento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

@Entity
public class Abbonamento extends TitoloDiViaggio {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoAbbonamento tipo;

    @Column(nullable = false)
    private LocalDate dataDiScadenza;

    @Column(nullable = false)
    private Tessera tessera;

    public Abbonamento() {

    }

    public Abbonamento(Long id, PuntoDiEmissione luogoDiEmissione, double prezzo, TipoAbbonamento tipo,
                       Tessera tessera) {
        super(id, luogoDiEmissione, prezzo);
        this.tipo = tipo;
        if (tipo == TipoAbbonamento.SETTIMANALE) {
            this.dataDiScadenza = getDataDiEmissione().plusWeeks(1);
        } else {
            this.dataDiScadenza = getDataDiEmissione().plusMonths(1);
        }
        this.tessera = tessera;
    }

    public LocalDate getDataDiScadenza() {
        return dataDiScadenza;
    }

    public Tessera getTessera() {
        return tessera;
    }

    public TipoAbbonamento getTipo() {
        return tipo;
    }

    public void setTipo(TipoAbbonamento tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Abbonamento{ \n" +
                super.toString() +
                "tipo=" + tipo + "\n" +
                ", data_di_scadenza=" + dataDiScadenza + "\n" +
                ", tessera=" + tessera + "\n" +
                "}  \n";
    }
}
