package entities;

import Enum.StatoMezzo;
import Enum.TipoMezzo;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "mezzo", cascade = CascadeType.ALL)
    private List<Biglietto> bigliettiVidimati = new ArrayList<>();

    @OneToMany(mappedBy = "mezzo", cascade = CascadeType.ALL)
    private List<Manutenzione> manutenzioni = new ArrayList<>();


    public Mezzo() {

    }

    public Mezzo(int capienza, StatoMezzo stato, TipoMezzo tipo) {

        this.stato = stato;
        this.tipo = tipo;
        if (tipo == TipoMezzo.AUTOBUS) {

            this.capienza = 50;
        } else {
            this.capienza = 80;
        }
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

    //SET STATO MEZZO
    public void setStato(StatoMezzo stato) {
        this.stato = stato;
    }

    public TipoMezzo getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Mezzo{ \n" +
                "id=" + id + "\n" +
                ", capienza= " + capienza + "\n" +
                ", stato: " + stato + "\n" +
                ", tipo:" + tipo + "\n" +
                "} \n";
    }

}
