package entities;

import Enum.StatoMezzo;
import Enum.TipoMezzo;
import jakarta.persistence.*;

@Entity
public class Mezzo {
    @Id
    @GeneratedValue
    private Long id;

    private int capienza;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato", nullable = false)
    private StatoMezzo stato;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoMezzo tipo;

    public Mezzo() {

    }

    public Mezzo(int capienza, StatoMezzo stato, TipoMezzo tipo) {
        if (tipo == TipoMezzo.AUTOBUS) {

            this.capienza = 50;
        } else {
            this.capienza = 80;
        }
        this.stato = stato;
        this.tipo = tipo;
    }

    public long getId() {
        return id;
    }

    public int getCapienza() {
        return capienza;
    }


    public StatoMezzo getStato() {
        return stato;
    }

    public void setStato(StatoMezzo stato) {
        this.stato = stato;
    }

    public TipoMezzo getTipo() {
        return tipo;
    }

    
}
