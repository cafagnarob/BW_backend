package dao;

import entities.Manutenzione;
import exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ManutenzioneDAO {
    private final EntityManager entityManager;

    public ManutenzioneDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //save
    public void save(Manutenzione newManutenzione) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            this.entityManager.persist(newManutenzione);
            transaction.commit();
            System.out.println("Il TITOLO DI VIAGGIO " + newManutenzione + "è stato aggiungo al DB");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Errore nel salvataggio del DB " + e.getMessage());
        }
    }

    //get
    //
    public Manutenzione getById(Long id) {
        Manutenzione fromDB = this.entityManager.find(Manutenzione.class, id);
        if (fromDB == null) throw new NotFoundException("Manutenzione non trovata");
        System.out.println("MANUTENZIONE RICHIESTA" + fromDB);
        return fromDB;

    }

    // delete
    public void delete(Long id) {
        Manutenzione fromDB = this.getById(id);
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();
        this.entityManager.remove(fromDB);
        transaction.commit();
        System.out.println("MANUTENZIONE " + fromDB + "è stato rimosso dal DB");
    }

    // STAMPA INFO MANUTENZIONI DATO ID MEZZO

    public List<Manutenzione> stampaInfoManutenzione(Long idMezzo) {
        TypedQuery<Manutenzione> query = this.entityManager.createQuery(
                "SELECT m FROM Manutenzione m WHERE m.mezzo.id = :param", Manutenzione.class);
        query.setParameter("param", idMezzo);
        List<Manutenzione> risultati = query.getResultList();
        System.out.println("LISTA MANUTENZIONE DI" + idMezzo + ":" + risultati);
        return risultati;
    }
}
