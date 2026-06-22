package entities;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_utente")
public abstract class Utente {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String cognome;
    @Column(nullable = false, unique = true)
    private String email;

    private int eta;


    public Utente() {
    }

    public Utente(String nome, String cognome, String email, int eta) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.eta = eta;
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


    @Override
    public String toString() {
        return "Utente{ \n" +
                "id=" + id + "\n" +
                ", nome='" + nome + '\'' + "\n" +
                ", cognome='" + cognome + '\'' + "\n" +
                ", email='" + email + '\'' + "\n" +
                ", eta=" + eta + "\n" +
                "} \n";
    }
}
