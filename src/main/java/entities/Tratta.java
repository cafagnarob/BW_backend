package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Tratta {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "tempo_di_percorrenza_previsto", nullable = false)
    private int tempoDiPercorrenzaPrevisto;

    @Column(nullable = false)
    private String partenza;

    @Column(nullable = false)
    private String capolinea;

    public Tratta() {
    }

    public Tratta(int tempoDiPercorrenzaPrevisto, String partenza, String capolinea) {
        this.tempoDiPercorrenzaPrevisto = tempoDiPercorrenzaPrevisto;
        this.partenza = partenza;
        this.capolinea = capolinea;
    }

    public long getId() {
        return id;
    }

    public int getTempoDiPercorrenzaPrevisto() {
        return tempoDiPercorrenzaPrevisto;
    }

    public void setTempoDiPercorrenzaPrevisto(int tempoDiPercorrenzaMedio) {
        this.tempoDiPercorrenzaPrevisto = tempoDiPercorrenzaMedio;
    }

    public String getPartenza() {
        return partenza;
    }

    public void setPartenza(String partenza) {
        this.partenza = partenza;
    }

    public String getCapolinea() {
        return capolinea;
    }

    public void setCapolinea(String capolinea) {
        this.capolinea = capolinea;
    }

    @Override
    public String toString() {
        return "Tratta{ \n" +
                "id=" + id + "\n" +
                ", tempo di percorrenza medio= " + tempoDiPercorrenzaPrevisto + "\n" +
                ", partenza da " + partenza + "\n" +
                ", arrivo a " + capolinea + "\n" +
                "} \n";
    }
}
