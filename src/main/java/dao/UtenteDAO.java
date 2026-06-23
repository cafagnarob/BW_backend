package dao;

import entities.Utente;
import exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class UtenteDAO {
    private final EntityManager entityManager;

    public UtenteDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //save
    public void save(Utente newUtente) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            this.entityManager.persist(newUtente);
            transaction.commit();
            System.out.println("L' UTENTE " + newUtente + "è stato aggiungo al DB");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Errore nel salvataggio del DB " + e.getMessage());
        }
    }

    //get
    public Utente getById(Long id) {
        Utente fromDB = this.entityManager.find(Utente.class, id);
        if (fromDB == null) throw new NotFoundException("Utente non trovato");
        System.out.println("UTENTE RICHIESTO" + fromDB);
        return fromDB;

    }

    // delete
    public void delete(Long id) {
        Utente fromDB = this.getById(id);
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();
        this.entityManager.remove(fromDB);
        transaction.commit();
        System.out.println("L' UTENTE " + fromDB + "è stato rimosso dal DB");
    }


}
