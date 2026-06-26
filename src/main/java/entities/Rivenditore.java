package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "rivenditore_autorizzato")
public class Rivenditore extends PuntoDiEmissione {

    public String nome;

    // costruttore vuoto
    public Rivenditore() {
        super();
    }


    //costruttore pieno
    public Rivenditore(String indirizzo) {
        super(indirizzo);

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIndirizzo() {
        return super.getIndirizzo();
    }

    public void setIndirizzo(String indirizzo) {
        super.setIndirizzo(indirizzo);
    }


    @Override
    public String toString() {
        return "Rivenditore{ \n"
                + super.toString() +
                "nome='" + nome + "\n" +
                "} \n ";
    }
}
