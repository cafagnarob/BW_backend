package entities;

import Enum.TipoManutenzione;

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
    private LocalDate dataInizioManutenzione;

    @Column(name = "data_fine_manutenzione")
    private LocalDate dataFineManutenzione;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_manutenzione", nullable = false)
    private TipoManutenzione tipoManutenzione;

    @ManyToOne
    @JoinColumn(name = "mezzo_id", nullable = false)
    private Mezzo mezzo;

    public Manutenzione() {
    }


    public Manutenzione(LocalDate dataFineManutenzione, TipoManutenzione tipoManutenzione, Mezzo mezzo) {
        this.dataInizioManutenzione = LocalDate.now();
        this.dataFineManutenzione = dataFineManutenzione;
        this.tipoManutenzione = tipoManutenzione;
        this.mezzo = mezzo;
    }


    public Long getId() {
        return id;
    }

    public LocalDate getDataInizioManutenzione() {
        return dataInizioManutenzione;
    }

    public void setDataInizioManutenzione(LocalDate data_inizio_manutenzione) {
        this.dataInizioManutenzione = data_inizio_manutenzione;
    }

    public LocalDate getDataFineManutenzione() {
        return dataFineManutenzione;
    }


    public TipoManutenzione getTipoManutenzione() {
        return tipoManutenzione;
    }

    public void setTipoManutenzione(TipoManutenzione tipo_manutenzione) {
        this.tipoManutenzione = tipo_manutenzione;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Manutenzione{");
        sb.append("id=").append(id);
        sb.append(", data_inizio_manutenzione=").append(dataInizioManutenzione);
        sb.append(", data_fine_manutenzione=").append(dataFineManutenzione);
        sb.append(", tipo_manutenzione=").append(tipoManutenzione);
        sb.append('}');
        return sb.toString();
    }
}