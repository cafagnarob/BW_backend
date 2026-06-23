package dao;

import entities.TitoloDiViaggio;
import exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class TitoloDiViaggioDAO {

    private final EntityManager entityManager;

    public TitoloDiViaggioDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //save
    public void save(TitoloDiViaggio newTitoloDiViaggio) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();
        this.entityManager.persist(newTitoloDiViaggio);
        transaction.commit();
        System.out.println("Il TITOLO DI VIAGGIO " + newTitoloDiViaggio + "è stato aggiungo al DB");
    }

    //get
    public TitoloDiViaggio getById(Long id) {
        TitoloDiViaggio fromDB = this.entityManager.find(TitoloDiViaggio.class, id);
        if (fromDB == null) throw new NotFoundException("titolo di viaggio non trovato");
        System.out.println("TITOLO DI VIAGGIO RICHIESTO" + fromDB);
        return fromDB;

    }

    // delete
    public void delete(Long id) {
        TitoloDiViaggio fromDB = this.getById(id);
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();
        this.entityManager.remove(fromDB);
        transaction.commit();
        System.out.println("IL TITOLO DI VIAGGIO " + fromDB + "è stato rimosso dal DB");
    }
}
