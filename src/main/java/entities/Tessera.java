package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Tessera {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private LocalDate data_di_emissione;

    @Column(nullable = false)
    private LocalDate data_di_scadenza;

    @Column(nullable = false)
    private Long id_punto_di_emissione;

    @Column(nullable = false, unique = true)
    private String codice_univoco_tessera;

    @Column(nullable = false, unique = true)
    private Viaggiatore proprietario_tessera;

}
