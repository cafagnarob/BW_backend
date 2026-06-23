package entities;

import Enum.TipoUtente;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_utente")
public abstract class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String cognome;
    @Column(nullable = false, unique = true)
    private String email;

    private int eta;

    @Column
    private double portafoglio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUtente tipo;


    public Utente() {
    }

    public Utente(String nome, String cognome, String email, int eta, double portafoglio,
                  TipoUtente tipo) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.eta = eta;
        this.portafoglio = portafoglio;
        this.tipo = tipo;
    }

    public String getCognome() {
        return cognome;
    }

    public String getNome() {
        return nome;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEta() {
        return eta;
    }

    // poichè non abbiamo messo nullable= false all 'eta questa puo essere anche null,
// perciò facciamo un set per poter modificare questo dato.
    public void setEta(int eta) {
        this.eta = eta;
    }

    public double getPortafoglio() {
        return portafoglio;
    }

    public void setPortafoglio(double portafoglio) {
        this.portafoglio = portafoglio;
    }

    public TipoUtente getTipo() {
        return tipo;
    }

    public void setTipo(TipoUtente tipo) {
        this.tipo = tipo;
    }


    @Override
    public String toString() {
        return "Utente{ \n" +
                "id=" + id + "\n" +
                ", nome='" + nome + "\n" +
                ", cognome='" + cognome + "\n" +
                ", email='" + email + "\n" +
                ", eta=" + eta + "\n" +
                ", portafoglio=" + portafoglio + "\n" +
                ", tipo=" + tipo + "\n" +
                "} \n";
    }
}
