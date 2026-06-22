package entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "manutenzione")
@Inheritance(strategy = InheritanceType.JOINED)
public class Manutenzione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_inizio_manutenzione", nullable = false)
    private LocalDate data_inizio_manutenzione;

    @Column(name = "data_fine_manutenzione")
    private LocalDate data_fine_manutenzione;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_manutenzione", nullable = false)
    private Tipo_Manutenzione tipo_manutenzione;


    public Manutenzione() {}


    public Manutenzione(LocalDate data_fine_manutenzione, Tipo_Manutenzione tipo_manutenzione) {
        this.data_inizio_manutenzione = LocalDate.now();
        this.data_fine_manutenzione = data_fine_manutenzione;
        this.tipo_manutenzione = tipo_manutenzione;
    }


    public Long getId() {
        return id;
    }

    public LocalDate getData_inizio_manutenzione() {
        return data_inizio_manutenzione;
    }

    public void setData_inizio_manutenzione(LocalDate data_inizio_manutenzione) {
        this.data_inizio_manutenzione = data_inizio_manutenzione;
    }

    public LocalDate getData_fine_manutenzione() {
        return data_fine_manutenzione;
    }

    public void setData_fine_manutenzione(LocalDate data_fine_manutenzione) {
        this.data_fine_manutenzione = data_fine_manutenzione;
    }

    public Tipo_Manutenzione getTipo_manutenzione() {
        return tipo_manutenzione;
    }

    public void setTipo_manutenzione(Tipo_Manutenzione tipo_manutenzione) {
        this.tipo_manutenzione = tipo_manutenzione;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Manutenzione{");
        sb.append("id=").append(id);
        sb.append(", data_inizio_manutenzione=").append(data_inizio_manutenzione);
        sb.append(", data_fine_manutenzione=").append(data_fine_manutenzione);
        sb.append(", tipo_manutenzione=").append(tipo_manutenzione);
        sb.append('}');
        return sb.toString();
    }
}