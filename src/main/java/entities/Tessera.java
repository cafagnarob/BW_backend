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
    private Punto_di_emissione luogo_di_emissione;

    @Column(nullable = false, unique = true)
    private String codice_univoco_tessera;
    // Tess + id

    @Column(nullable = false, unique = true)
    private Viaggiatore proprietario_tessera;

    public Tessera() {
    }

    public Tessera(LocalDate data_di_emissione, LocalDate data_di_scadenza,
                   Punto_di_emissione luogo_di_emissione, Viaggiatore proprietario_tessera) {
        this.data_di_emissione = data_di_emissione;
        this.data_di_scadenza = data_di_emissione.plusYears(1);
        this.luogo_di_emissione = luogo_di_emissione;
        this.codice_univoco_tessera = "TESS_" + id;
        this.proprietario_tessera = proprietario_tessera;
    }

    public Long getId() {
        return id;
    }

    public String getCodice_univoco_tessera() {
        return codice_univoco_tessera;
    }

    public LocalDate getData_di_emissione() {
        return data_di_emissione;
    }

    public void setData_di_emissione(LocalDate data_di_emissione) {
        this.data_di_emissione = data_di_emissione;
    }

    public LocalDate getData_di_scadenza() {
        return data_di_scadenza;
    }

    public Punto_di_emissione getLuogo_di_emissione() {
        return luogo_di_emissione;
    }

    public Viaggiatore getProprietario_tessera() {
        return proprietario_tessera;
    }


    @Override
    public String toString() {
        return "Tessera{ \n" +
                "id=" + id + "\n" +
                ", data_di_emissione=" + data_di_emissione + "\n" +
                ", data_di_scadenza=" + data_di_scadenza + "\n" +
                ", luogo_di_emissione=" + luogo_di_emissione + "\n" +
                ", codice_univoco_tessera='" + codice_univoco_tessera + "\n" +
                ", proprietario_tessera=" + proprietario_tessera + "\n" +
                "} \n ";
    }
}
