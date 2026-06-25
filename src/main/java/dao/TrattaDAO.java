package dao;

import entities.Tratta;
import exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

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

    public void listaTratte() {
        TypedQuery<Tratta> query = entityManager.createQuery(
                "SELECT t FROM Tratta t", Tratta.class
        );
        query.getResultList().forEach(System.out::println);
    }

    public void popolaSeVuoto() {
        long count = entityManager.createQuery("SELECT COUNT(t) FROM Tratta t", Long.class).getSingleResult();
        if (count == 0) {

            save(new Tratta(45, "Milano Centrale", "Bergamo"));
            save(new Tratta(60, "Milano Centrale", "Brescia"));
            save(new Tratta(120, "Milano Centrale", "Verona"));
            save(new Tratta(180, "Milano Centrale", "Venezia Santa Lucia"));
            save(new Tratta(150, "Milano Porta Garibaldi", "Torino Porta Nuova"));
            save(new Tratta(90, "Milano Rogoredo", "Piacenza"));
            save(new Tratta(70, "Milano Lambrate", "Monza"));
            save(new Tratta(200, "Milano Centrale", "Bologna Centrale"));
            save(new Tratta(240, "Milano Centrale", "Firenze Santa Maria Novella"));
            save(new Tratta(300, "Milano Centrale", "Roma Termini"));
            save(new Tratta(50, "Milano Cadorna", "Saronno"));
            save(new Tratta(35, "Milano Bovisa", "Como San Giovanni"));
            save(new Tratta(110, "Milano Centrale", "Genova Piazza Principe"));
            save(new Tratta(95, "Milano Porta Garibaldi", "Varese"));
            save(new Tratta(75, "Milano Rogoredo", "Lodi"));


            System.out.println("Tratte aggiunte!");
        } else {
            System.out.println("Tabella Tratta piena");
        }
    }
}
