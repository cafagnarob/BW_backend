package dao;

import entities.Biglietto;
import entities.TitoloDiViaggio;
import exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.time.LocalTime;
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
                        "WHERE t.dataDiEmissione BETWEEN :param AND :param2 ", TitoloDiViaggio.class);
        query.setParameter("param", dataInizo);
        query.setParameter("param2", dataFine);
        List<TitoloDiViaggio> res = query.getResultList();
        System.out.println("I TITOLI DI VIAGGIO EMESSI DAL" + dataInizo + "AL" + dataFine + "SONO : " + res.size());
        System.out.println(res);
        return res;
    }

    // metodo per Stampare il numero e le lista di Titoli di viaggio emessi da uno specifico punto di emissione

    public List<TitoloDiViaggio> stampaListaNumTDV(Long idPunto) {
        TypedQuery<TitoloDiViaggio> query = this.entityManager.createQuery(
                "SELECT t FROM TitoloDiViaggio t WHERE t.luogoDiEmissione = :param", TitoloDiViaggio.class);
        query.setParameter("param", idPunto);
        List<TitoloDiViaggio> res = query.getResultList();
        System.out.println("I TITOLI DI VIAGGIO EMESSI DALL ATTIVITA: " + idPunto + "SONO : " + res.size());
        System.out.println(res);
        return res;
    }

    // metodo che stampa i biglietti vidimanti dalle ore ... alle ore ...
    public List<TitoloDiViaggio> stampaListNumTDVVidimatiPerTempo(LocalTime oraInizo, LocalTime oraFine) {
        TypedQuery<Biglietto> query = this.entityManager.createQuery(
                "SELECT b FROM Biglietto b " +
                        "WHERE b.orarioDiVidimazione BETWEEN :param AND :param2 ", Biglietto.class);
        query.setParameter("param", oraInizo);
        query.setParameter("param2", oraFine);
        List<Biglietto> res = query.getResultList();
        System.out.println("I BIGLIETTI VIDIMATI DAL" + oraInizo + "AL" + oraFine + "SONO : " + res.size());
        System.out.println(res);
        return res;
    }


    public void stampaInfoAbbonamento(Long idAbbonamento) {
    }

}
