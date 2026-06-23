package entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Tessera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dataDiEmissione;

    @Column(nullable = false)
    private LocalDate dataDiScadenza;

    @ManyToOne
    @Column(nullable = false)
    private PuntoDiEmissione luogoDiEmissione;

    @OneToOne
    @Column(name = "id_utente", nullable = false, unique = true)
    private Utente idUtente;

    public Tessera() {
    }

    public Tessera(LocalDate dataDiEmissione, LocalDate dataDiScadenza,
                   PuntoDiEmissione luogoDiEmissione, Utente idUtente) {
        this.dataDiEmissione = dataDiEmissione;
        this.dataDiScadenza = dataDiEmissione.plusYears(1);
        this.luogoDiEmissione = luogoDiEmissione;
        this.idUtente = idUtente;
    }

    public Long getId() {
        return id;
    }

    public String getCodice_univoco_tessera() {
        return codiceUnivocoTessera;
    }

    public LocalDate getData_di_emissione() {
        return dataDiEmissione;
    }

    public void setData_di_emissione(LocalDate data_di_emissione) {
        this.dataDiEmissione = data_di_emissione;
    }

    public LocalDate getData_di_scadenza() {
        return dataDiScadenza;
    }

    public PuntoDiEmissione getLuogo_di_emissione() {
        return luogoDiEmissione;
    }

    public Utente getProprietario_tessera() {
        return proprietarioTessera;
    }


    @Override
    public String toString() {
        return "Tessera{ \n" +
                "id=" + id + "\n" +
                ", data_di_emissione=" + dataDiEmissione + "\n" +
                ", data_di_scadenza=" + dataDiScadenza + "\n" +
                ", luogo_di_emissione=" + luogoDiEmissione + "\n" +
                ", codice_univoco_tessera='" + codiceUnivocoTessera + "\n" +
                ", proprietario_tessera=" + proprietarioTessera + "\n" +
                "} \n ";
    }
}
