package dao;

import entities.Tratta;
import exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.List;

public class TrattaDAO {
    private final EntityManager entityManager;

    public TrattaDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //save
    public void save(Tratta newTratta) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {

            transaction.begin();
            this.entityManager.persist(newTratta);
            transaction.commit();
            System.out.println("La TRATTA " + newTratta + "è stato aggiungo al DB");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Errore nel salvataggio del DB " + e.getMessage());
        }
    }

    //get
    // ------- STAMPA INFO TRATTA BY ID ---------
    public Tratta getById(Long id) {
        Tratta fromDB = this.entityManager.find(Tratta.class, id);
        if (fromDB == null) throw new NotFoundException("Tratta non trovato");
        System.out.println("TRATTA RICHIESTA" + fromDB);
        return fromDB;

    }

    // delete
    public void delete(Long id) {
        Tratta fromDB = this.getById(id);
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();
        this.entityManager.remove(fromDB);
        transaction.commit();
        System.out.println("LA TRATTA " + fromDB + "è stato rimosso dal DB");
    }

    public void popolaSeVuoto() {
        long count = entityManager.createQuery("SELECT COUNT(t) FROM Tratta t", Long.class).getSingleResult();
        if (count == 0) {

            List<Tratta> tratte = new ArrayList<>();

            tratte.add(new Tratta(45, "Milano Centrale", "Bergamo"));
            tratte.add(new Tratta(60, "Milano Centrale", "Brescia"));
            tratte.add(new Tratta(120, "Milano Centrale", "Verona"));
            tratte.add(new Tratta(180, "Milano Centrale", "Venezia Santa Lucia"));
            tratte.add(new Tratta(150, "Milano Porta Garibaldi", "Torino Porta Nuova"));
            tratte.add(new Tratta(90, "Milano Rogoredo", "Piacenza"));
            tratte.add(new Tratta(70, "Milano Lambrate", "Monza"));
            tratte.add(new Tratta(200, "Milano Centrale", "Bologna Centrale"));
            tratte.add(new Tratta(240, "Milano Centrale", "Firenze Santa Maria Novella"));
            tratte.add(new Tratta(300, "Milano Centrale", "Roma Termini"));
            tratte.add(new Tratta(50, "Milano Cadorna", "Saronno"));
            tratte.add(new Tratta(35, "Milano Bovisa", "Como San Giovanni"));
            tratte.add(new Tratta(110, "Milano Centrale", "Genova Piazza Principe"));
            tratte.add(new Tratta(95, "Milano Porta Garibaldi", "Varese"));
            tratte.add(new Tratta(75, "Milano Rogoredo", "Lodi"));

            for (Tratta t : tratte) {
                try {
                    save(t);
                } catch (Exception e) {
                    System.err.println("Errore nel salvare la tratta da " + t.getPartenza() + " a " + t.getCapolinea());
                }
            }

            System.out.println("Tratte aggiunte!");
        } else {
            System.out.println("Tabella Tratta piena");
        }
    }
}
