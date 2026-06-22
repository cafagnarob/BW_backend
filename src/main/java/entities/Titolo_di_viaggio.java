package entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Titolo_di_viaggio {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private LocalDate data_di_emissione;

    @Column(nullable = false)
    private Punto_di_emissione luogo_di_emissione;

    @Column(nullable = false, unique = true)
    private String codice_univoco_titolo_di_viaggio;

    @Column(nullable = false)
    private double prezzo;

    public Titolo_di_viaggio() {
    }

    public Titolo_di_viaggio(Long id, LocalDate data_di_emissione, Punto_di_emissione luogo_di_emissione
            , String codice_univoco_titolo_di_viaggio, double prezzo) {
        this.data_di_emissione = LocalDate.now();
        this.luogo_di_emissione = luogo_di_emissione;
    }
}
