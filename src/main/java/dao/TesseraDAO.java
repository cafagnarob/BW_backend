package dao;

import entities.Tessera;
import exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class TesseraDAO {
    private final EntityManager entityManager;

    public TesseraDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //save
    public void save(Tessera newTessera) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();
        this.entityManager.persist(newTessera);
        transaction.commit();
        System.out.println("La TESSERA " + newTessera + "è stato aggiungo al DB");
    }

    //get
    // STAMPA INFO TESSERA DATO ID-TESSERA
    public Tessera getById(Long id) {
        Tessera fromDB = this.entityManager.find(Tessera.class, id);
        if (fromDB == null) throw new NotFoundException("Tessera non trovato");
        System.out.println("TESSERA RICHIESTA" + fromDB);
        return fromDB;

    }

    // delete
    public void delete(Long id) {
        Tessera fromDB = this.getById(id);
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();
        this.entityManager.remove(fromDB);
        transaction.commit();
        System.out.println("LA TESSERA " + fromDB + "è stato rimosso dal DB");


    }

}
