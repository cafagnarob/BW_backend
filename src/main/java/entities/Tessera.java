package entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tessera")
public class Tessera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dataDiEmissione;

    @Column(nullable = false)
    private LocalDate dataDiScadenza;

    @Column(nullable = false)
    private double prezzo;

    @OneToOne
    @JoinColumn(name = "id_utente", nullable = false, unique = true)
    private Utente utente;

    @ManyToOne
    @JoinColumn(nullable = false)
    private PuntoDiEmissione luogoDiEmissione;




    public Tessera() {
    }

    public Tessera(LocalDate dataDiEmissione,
                   PuntoDiEmissione luogoDiEmissione, Utente utente) {
        this.dataDiEmissione = dataDiEmissione;
        this.dataDiScadenza = dataDiEmissione.plusYears(1);
        this.luogoDiEmissione = luogoDiEmissione;
        this.utente = utente;
        this.prezzo = 49.99;
    }

    public Long getId() {
        return id;
    }

    public double getPrezzo() {
        return prezzo;
    }
    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
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


    @Override
    public String toString() {
        return "Tessera{ \n" +
                "id=" + id + "\n" +
                ", data_di_emissione=" + dataDiEmissione + "\n" +
                ", data_di_scadenza=" + dataDiScadenza + "\n" +
                ", luogo_di_emissione=" + luogoDiEmissione + "\n" +
                "} \n ";
    }
}
