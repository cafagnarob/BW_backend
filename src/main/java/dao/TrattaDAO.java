package dao;

import entities.Tratta;
import exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

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

}
