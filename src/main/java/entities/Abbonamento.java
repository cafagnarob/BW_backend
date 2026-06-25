package entities;

import Enum.TipoAbbonamento;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Abbonamento extends TitoloDiViaggio {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoAbbonamento tipo;

    @Column(nullable = false)
    private LocalDate dataDiScadenza;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Tessera tessera;

    public Abbonamento() {

    }

    public Abbonamento(LocalDate dataDiEmissione, PuntoDiEmissione luogoDiEmissione, TipoAbbonamento tipo,
                       Tessera tessera) {
        super(dataDiEmissione, luogoDiEmissione, (tipo == TipoAbbonamento.SETTIMANALE) ? 10.00 : 35.00);
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
    public  void setDataDiScadenza(LocalDate dataDiScadenza) {
        this.dataDiScadenza = dataDiScadenza;
    }

    public Tessera getTessera() {
        return tessera;
    }

    public TipoAbbonamento getTipo() {
        return tipo;
    }
    

    @Override
    public String toString() {
        return "Abbonamento{ \n" +
                "tipo=" + tipo + "\n" +
                ", prezzo=" + getPrezzo() + "\n" +
                ", data_di_scadenza=" + dataDiScadenza + "\n" +
                ", tessera=" + tessera + "\n" +
                "}  \n";
    }
}
