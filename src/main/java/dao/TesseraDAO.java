package dao;

import entities.PuntoDiEmissione;
import entities.Tessera;
import entities.Utente;
import exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;

public class TesseraDAO {
    private final EntityManager entityManager;

    public TesseraDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //save
    public void save(Tessera newTessera) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {

            transaction.begin();
            this.entityManager.persist(newTessera);
            transaction.commit();
            System.out.println("La TESSERA " + newTessera + "è stato aggiungo al DB");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Errore nel salvataggio del DB " + e.getMessage());
        }
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

    // Compra Tessera
    public void compraTessera(PuntoDiEmissione luogo, Utente utente){
            if(luogo==null || utente==null){
                throw new NotFoundException("Impossibile comprare una nuova tessera!");
            }
            Tessera nuovaTessera = new Tessera(LocalDate.now(), luogo, utente);

            this.save(nuovaTessera);
    }

    public Tessera creaTessera(PuntoDiEmissione puntoDiEmissione, Utente utente) {
        return new Tessera(LocalDate.now(), puntoDiEmissione, utente);
    }

}
