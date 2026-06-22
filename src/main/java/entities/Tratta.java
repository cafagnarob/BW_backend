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

    @Column(name = "tempo_di_percorrenza_medio", nullable = false)
    private int tempoDiPercorrenzaMedio;

    @Column(nullable = false)
    private String partenza;

    @Column(nullable = false)
    private String capolinea;

    public Tratta() {
    }

    public Tratta(int tempoDiPercorrenzaMedio, String partenza, String capolinea){
        this.tempoDiPercorrenzaMedio = tempoDiPercorrenzaMedio;
        this.partenza = partenza;
        this.capolinea = capolinea;
    }

    public long getId() {
        return id;
    }

    public int getTempoDiPercorrenzaMedio() {
        return tempoDiPercorrenzaMedio;
    }

    public void setTempoDiPercorrenzaMedio(int tempoDiPercorrenzaMedio) {
        this.tempoDiPercorrenzaMedio = tempoDiPercorrenzaMedio;
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
                ", tempo di percorrenza medio= " + tempoDiPercorrenzaMedio + "\n" +
                ", partenza da " + partenza + "\n" +
                ", arrivo a " + capolinea + "\n" +
                "} \n";
    }
}
