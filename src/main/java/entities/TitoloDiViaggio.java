package entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class TitoloDiViaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dataDiEmissione;

    @Column(nullable = false)
    private PuntoDiEmissione luogoDiEmissione;

    @Column(nullable = false, unique = true)
    private String codiceUnivocoTitoloDiViaggio;
    // TV + id (TV 1...TV 2.... ecc)

    @Column(nullable = false)
    private double prezzo;

    public TitoloDiViaggio() {
    }

    public TitoloDiViaggio(Long id, PuntoDiEmissione luogoDiEmissione, double prezzo) {
        this.dataDiEmissione = LocalDate.now();
        this.luogoDiEmissione = luogoDiEmissione;
        this.codiceUnivocoTitoloDiViaggio = "TV" + id;
        this.prezzo = prezzo;
    }

    public LocalDate getDataDiEmissione() {
        return dataDiEmissione;
    }

    public PuntoDiEmissione getLuogoDiEmissione() {
        return luogoDiEmissione;
    }

    public Long getId() {
        return id;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public String getCodiceUnivocoTitoloDiViaggio() {
        return codiceUnivocoTitoloDiViaggio;
    }

}

