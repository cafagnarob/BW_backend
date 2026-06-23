package dao;

import Enum.TipoAbbonamento;
import entities.*;
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
        try {

            transaction.begin();
            this.entityManager.persist(newTitoloDiViaggio);
            transaction.commit();
            System.out.println("Il TITOLO DI VIAGGIO " + newTitoloDiViaggio + "è stato aggiungo al DB");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Errore nel salvataggio del DB " + e.getMessage());
        }
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
                "SELECT t FROM TitoloDiViaggio t WHERE t.luogoDiEmissione.id = :param", TitoloDiViaggio.class);
        query.setParameter("param", idPunto);
        List<TitoloDiViaggio> res = query.getResultList();
        System.out.println("I TITOLI DI VIAGGIO EMESSI DALL ATTIVITA: " + idPunto + "SONO : " + res.size());
        System.out.println(res);
        return res;
    }

    // metodo che stampa i biglietti vidimanti dalle ore ... alle ore ... dato il mezzo
    public List<Biglietto> stampaListNumTDVVidimatiPerTempo(LocalTime oraInizo, LocalTime oraFine,
                                                            Long idMezzo) {
        TypedQuery<Biglietto> query = this.entityManager.createQuery(
                "SELECT b FROM Biglietto b " +
                        "WHERE b.orarioDiVidimazione BETWEEN :param AND :param2 AND b.mezzo.id=:param3", Biglietto.class);
        query.setParameter("param", oraInizo);
        query.setParameter("param3", idMezzo);
        query.setParameter("param2", oraFine);
        List<Biglietto> res = query.getResultList();
        System.out.println("I BIGLIETTI VIDIMATI DAL" + oraInizo + "AL" + oraFine + "SONO : " + res.size());
        System.out.println(res);
        return res;
    }


    public Biglietto creaBiglietto(PuntoDiEmissione puntoDiEmissione) {
        return new Biglietto(LocalDate.now(), puntoDiEmissione, 2.50);
    }

    public Abbonamento creaAbbonamento(PuntoDiEmissione puntoDiEmissione, Tessera tessera,
                                       double prezzo, TipoAbbonamento tipo) {
        return new Abbonamento(LocalDate.now(), puntoDiEmissione, prezzo,
                tipo, tessera);
    }

    public void stampaInfoAbbonamento(Long idTessera) {
    }

}
