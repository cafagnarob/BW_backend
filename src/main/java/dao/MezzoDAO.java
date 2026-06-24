package dao;

import Enum.StatoMezzo;
import Enum.TipoMezzo;
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
                "SELECT m FROM Mezzo m WHERE m.stato= :param", Mezzo.class);
        query.setParameter("param", stato);
        List<Mezzo> res = query.getResultList();
        res.forEach(System.out::println);
    }

    public void update(Mezzo mezzo) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {

            transaction.begin();
            entityManager.merge(mezzo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void listaMezzi() {
        TypedQuery<Mezzo> query = entityManager.createQuery(
                "SELECT m FROM Mezzo m", Mezzo.class);
        List<Mezzo> res = query.getResultList();
        res.forEach(System.out::println);
    }

    public void popolaSeVuoto() {

        long count = entityManager.createQuery("SELECT COUNT(m) FROM Mezzo m", Long.class).getSingleResult();

        if (count == 0) {
            Mezzo m1 = new Mezzo(StatoMezzo.FERMO, TipoMezzo.AUTOBUS);
            save(m1);
            Mezzo m2 = new Mezzo(StatoMezzo.FERMO, TipoMezzo.AUTOBUS);
            save(m2);
            Mezzo m3 = new Mezzo(StatoMezzo.FERMO, TipoMezzo.AUTOBUS);
            save(m3);
            Mezzo m4 = new Mezzo(StatoMezzo.FERMO, TipoMezzo.AUTOBUS);
            save(m4);
            Mezzo m5 = new Mezzo(StatoMezzo.FERMO, TipoMezzo.AUTOBUS);
            save(m5);
            Mezzo m6 = new Mezzo(StatoMezzo.FERMO, TipoMezzo.TRAM);
            save(m6);
            Mezzo m7 = new Mezzo(StatoMezzo.FERMO, TipoMezzo.TRAM);
            save(m7);
            Mezzo m8 = new Mezzo(StatoMezzo.FERMO, TipoMezzo.TRAM);
            save(m8);
            Mezzo m9 = new Mezzo(StatoMezzo.FERMO, TipoMezzo.TRAM);
            save(m9);
            Mezzo m10 = new Mezzo(StatoMezzo.FERMO, TipoMezzo.TRAM);
            save(m10);

            System.out.println("Mezzi aggiunti!");
        } else {
            System.out.println("Tabella Mezzo piena");
        }
    }

}
