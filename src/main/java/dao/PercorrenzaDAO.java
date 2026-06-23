package dao;

import entities.Mezzo;
import entities.Percorrenza;
import entities.Tratta;
import exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class PercorrenzaDAO {

    private final EntityManager em;

    public PercorrenzaDAO(EntityManager em){
        this.em = em;
    }

    public void save(Percorrenza p){
        EntityTransaction transaction = em.getTransaction();

            transaction.begin();
            em.persist(p);
            transaction.commit();
            System.out.println("Nuova Percorrenza " + p + " aggiunta con successo al DB");

    }

    public int getTempoEffettivoDaId(long id) {

        Percorrenza p = em.find(Percorrenza.class, id);
        if (p == null) {
            throw new NotFoundException("Non esistono percorrenze con questo id: " + id);
        } else {
            return p.getTempoPercorrenzaEffettivo();
        }
    }

    // Nuova percorrenza assegnando una tratta ad un mezzo

    public void assegnaMezzoATratta(Mezzo mezzo, Tratta tratta, LocalDate data, int tempoEffettivo){

        Percorrenza nuovaPercorrenza = new Percorrenza(data, mezzo, tratta, tempoEffettivo);
        this.save(nuovaPercorrenza);
    }

    // Informazioni di percorreze da id di un mezzo
    public void stampaInfoDaIdMezzo(long idMezzo){

        TypedQuery<Percorrenza> query = em.createQuery(
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
}

