package entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Titolo_di_viaggio {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private LocalDate data_di_emissione;

    @Column(nullable = false)
    private Punto_di_emissione luogo_di_emissione;

    @Column(nullable = false, unique = true)
    private String codice_univoco_titolo_di_viaggio;
    // TV + id (TV 1...TV 2.... ecc)

    @Column(nullable = false)
    private double prezzo;

    public Titolo_di_viaggio() {
    }

    public Titolo_di_viaggio(Long id, Punto_di_emissione luogo_di_emissione, double prezzo) {
        this.data_di_emissione = LocalDate.now();
        this.luogo_di_emissione = luogo_di_emissione;
        this.codice_univoco_titolo_di_viaggio = "TV" + id;
        this.prezzo = prezzo;
    }

    public LocalDate getData_di_emissione() {
        return data_di_emissione;
    }

    public Punto_di_emissione getLuogo_di_emissione() {
        return luogo_di_emissione;
    }

    public Long getId() {
        return id;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public String getCodice_univoco_titolo_di_viaggio() {
        return codice_univoco_titolo_di_viaggio;
    }


}

