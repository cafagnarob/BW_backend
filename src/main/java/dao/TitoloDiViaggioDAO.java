package dao;

import entities.TitoloDiViaggio;
import exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

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


    // STAMPA  IL NUMERO E LA LISTA DEI TITOLI DI VIAGGIO VENDUTI DAL ... AL ...
    public List<TitoloDiViaggio> stampaNumListTDVPerTempo(LocalDate dataInizo, LocalDate dataFine) {
        TypedQuery<TitoloDiViaggio> query = this.entityManager.createQuery(
                "SELECT t FROM TitoloDiViaggio t " +
                        "WHERE t.dataDiEmissione BETWEEN :inizio AND :fine ", TitoloDiViaggio.class);
        query.setParameter("param", dataInizo);
        query.setParameter("param2", dataFine);
        List<TitoloDiViaggio> res = query.getResultList();
        System.out.println("I TITOLI DI VIAGGIO EMESSI DAL" + dataInizo + "AL" + dataFine + "SONO : " + res.size());
        System.out.println(res);
        return res;
    }

    public void stampaInfoAbbonamento(Long idAbbonamento) {
    }
}
