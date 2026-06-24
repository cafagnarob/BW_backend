package dao;

import entities.*;
import exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;


import java.time.LocalDate;
import java.util.List;

public class PercorrenzaDAO {

    private final EntityManager entityManager;

    public PercorrenzaDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    // SAVE
    public void save(Percorrenza p){
        EntityTransaction transaction = entityManager.getTransaction();

            transaction.begin();
        entityManager.persist(p);
            transaction.commit();
            System.out.println("Nuova Percorrenza " + p + " aggiunta con successo al DB");

    }

    // GET
    public Percorrenza getById(long id) {
        Percorrenza p = entityManager.find(Percorrenza.class, id);
        if (p == null) {
            throw new NotFoundException("Non esistono percorrenze con questo id: " + id);
        }
        return p;
    }

    //DELETE
    public void delete(long id) {
        Percorrenza fromDB = this.getById(id);
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();
        this.entityManager.remove(fromDB);
        transaction.commit();
        System.out.println("La Percorrenza " + fromDB + "è stato rimosso dal DB");
    }

    // Tempo effettivo da ID
    public int getTempoEffettivoDaId(long id) {

        Percorrenza p = this.getById(id);
        return p.getTempoPercorrenzaEffettivo();
    }

    // Nuova percorrenza assegnando una tratta ad un mezzo

    public void assegnaMezzoATratta(Mezzo mezzo, Tratta tratta, LocalDate data, int tempoEffettivo){

        Percorrenza nuovaPercorrenza = new Percorrenza(data, mezzo, tratta, tempoEffettivo);
        this.save(nuovaPercorrenza);
    }

    // Informazioni di percorreze da id di un mezzo

    public void stampaInfoDaIdMezzo(long idMezzo){

        TypedQuery<Percorrenza> query = entityManager.createQuery(
                "SELECT p FROM Percorrenza p WHERE p.mezzo.id = :idMezzo",
                Percorrenza.class
        );
        query.setParameter("idMezzo", idMezzo);

        List<Percorrenza> listaPercorrenze = query.getResultList();

        if (listaPercorrenze.isEmpty()) {
            throw new NotFoundException("Non esistono percorrenze con questo id: " + idMezzo);
        } else {
            System.out.println("Storico percorrenze registrate per questo mezzo: " + idMezzo);
            for (Percorrenza p : listaPercorrenze) {
                System.out.println("Data: " + p.getData() +
                        " Tempo effettivo: " + p.getTempoPercorrenzaEffettivo() + " minuti" +
                        " Tratta: ID " + p.getTratta().getId() +
                        " (" + p.getTratta().getPartenza() + " -> " + p.getTratta().getCapolinea() + ")");
            }
        }
    }

    public void popolaSeVuoto() {
        long count = entityManager.createQuery("SELECT COUNT(p) FROM Percorrenza p", Long.class).getSingleResult();
        if (count == 0) {
            //inserire qui nuove percorrenze con i save
            //aggiungere poi il metodo nel main

            System.out.println("Percorrenze aggiunte!");
        } else {
            System.out.println("Tabella percorrenze piena");
        }
    }
}

