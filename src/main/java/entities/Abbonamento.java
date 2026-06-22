package entities;

import Enum.TipoAbbonamento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Abbonamento extends Titolo_di_viaggio {
    @Column(nullable = false)
    private TipoAbbonamento tipo;

    @Column(nullable = false)
    private LocalDate data_di_scadenza;

    @Column(nullable = false)
    private Tessera tessera;

    public Abbonamento() {

    }

    public Abbonamento(Long id, Punto_di_emissione luogo_di_emissione, double prezzo, TipoAbbonamento tipo,
                       Tessera tessera) {
        super(id, luogo_di_emissione, prezzo);
        this.tipo = tipo;
        if (tipo == TipoAbbonamento.SETTIMANALE) {
            this.data_di_scadenza = getData_di_emissione().plusWeeks(1);
        } else {
            this.data_di_scadenza = getData_di_emissione().plusMonths(1);
        }
        this.tessera = tessera;
    }

    public LocalDate getData_di_scadenza() {
        return data_di_scadenza;
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
                ", data_di_scadenza=" + data_di_scadenza + "\n" +
                ", tessera=" + tessera + "\n" +
                "}  \n";
    }
}
