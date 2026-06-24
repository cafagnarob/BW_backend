package dao;

import Enum.StatoMezzo;
import entities.Mezzo;
import exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class MezzoDAO {
    private final EntityManager entityManager;

    public MezzoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //save
    public void save(Mezzo newMezzo) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {

            transaction.begin();
            this.entityManager.persist(newMezzo);
            transaction.commit();
            System.out.println("Mezzo salvato con successo: " + newMezzo);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Errore nel salvataggio del DB " + e.getMessage());
            e.printStackTrace();
        }
    }

    //get
    public Mezzo getById(Long id) {
        Mezzo fromDB = this.entityManager.find(Mezzo.class, id);
        if (fromDB == null) throw new NotFoundException("Mezzo non trovato");
        System.out.println("MEZZO RICHIESTA" + fromDB);
        return fromDB;

    }

    // delete
    public void delete(Long id) {
        Mezzo fromDB = this.getById(id);
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();
        this.entityManager.remove(fromDB);
        transaction.commit();
        System.out.println("IL MEZZO " + fromDB + "è stato rimosso dal DB");
    }


    public void listaMezzoPerStato(StatoMezzo stato) {
        TypedQuery<Mezzo> query = entityManager.createQuery(
                "SELECT m FROM Mezzo m WHERE m.StatoMezzo= :param", Mezzo.class);
        query.setParameter("param", stato);
        List<Mezzo> res = query.getResultList();
        res.forEach(System.out::println);
    }
}
