package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "rivenditore_autorizzato")
public class Rivenditore extends Punto_di_Emissione {

    public String nome;


    // costruttore vuoto
    public Rivenditore() {
        super();
    }


    //costruttore pieno

    public Rivenditore(String nome, String indirizzo) {
        super(indirizzo);
        this.nome = nome;
    }

    //getter e setter
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Rivenditore{");
        sb.append("nome='").append(nome).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
