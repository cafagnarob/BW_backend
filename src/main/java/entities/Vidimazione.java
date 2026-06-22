package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity

public class Vidimazione {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "id_biglietto", nullable = false)
    private Biglietto biglietto;

    @Column(nullable = false)
    private LocalDateTime orario;

    @Column(name = "id_mezzo", nullable = false)
    private Mezzo mezzo;

    @Column(name = "id_servizio", nullable = false)
    private InServizio servizio;

    public Vidimazione() {
    }

    public Vidimazione(Biglietto biglietto, Mezzo mezzo, InServizio servizio) {
        this.orario = LocalDateTime.now();
        this.biglietto = biglietto;
        this.mezzo = mezzo;
        this.servizio = servizio;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getOrario() {
        return orario;
    }


    public Titolo_di_viaggio getBiglietto() {
        return biglietto;
    }


}
