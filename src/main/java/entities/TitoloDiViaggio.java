package entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class TitoloDiViaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_di_emissione", nullable = false)
    private LocalDate dataDiEmissione;

    @Column(nullable = false)
    private double prezzo;

    @ManyToOne
    @JoinColumn(name = "id_luogo_di_emissione", nullable = false)
    private PuntoDiEmissione luogoDiEmissione;


    public TitoloDiViaggio() {
    }

    public TitoloDiViaggio(LocalDate dataDiEmissione, PuntoDiEmissione luogoDiEmissione, double prezzo) {
        this.dataDiEmissione = LocalDate.now();
        this.luogoDiEmissione = luogoDiEmissione;
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


}

