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
                        "WHERE b.orarioVidimazione BETWEEN :param AND :param2 AND b.mezzo.id=:param3", Biglietto.class);
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
        //cerco l'abbonamento legato al numero di tessera inserito
        TypedQuery<Abbonamento> query = this.entityManager.createQuery(
                "SELECT a FROM Abbonamento a WHERE a.tessera.id = :idTessera ORDER BY a.dataDiEmissione DESC",
                Abbonamento.class); //ordino in modo decrescente per prendere ultimo valido per data di emissione
        query.setParameter("idTessera", idTessera);
        query.setMaxResults(1); // prendo solo l'ultimo abbonamento emesso

        List<Abbonamento> listaresults = query.getResultList();

        System.out.println("=== VERIFICA VALIDITA' ABBONAMENTO ===");
        if (listaresults.isEmpty()) {
            System.out.println("Nessun abbonamento trovato nel sistema per la tessera n. " + idTessera);
        } else {
            Abbonamento ultimoAbbonamento = listaresults.get(0);
            LocalDate dataScadenza = ultimoAbbonamento.getDataDiScadenza();

            System.out.println("Tessera Numero: " + idTessera);
            System.out.println("Tipo Abbonamento: " + ultimoAbbonamento.getTipo()); //metodo di getTipo in abbonamento
            System.out.println("Data Scadenza: " + dataScadenza);

            // controllo validità rispetto a OGGI
            if (dataScadenza.isAfter(LocalDate.now()) || dataScadenza.isEqual(LocalDate.now())) {
                System.out.println(" STATO: VALIDO");
            } else {
                System.out.println("STATO: SCADUTO");
            }
        }
        System.out.println("===================================");
    }

    public void popolaSeVuoto() {
        long count = entityManager.createQuery("SELECT COUNT(t) FROM Percorrenza t", Long.class).getSingleResult();
        if (count == 0) {
            //inserire qui nuovi titoli di viaggio con i save
            //aggiungere poi il metodo nel main

            System.out.println("Titoli di viaggio aggiunti!");
        } else {
            System.out.println("Tabella Titoli di viaggio piena");
        }
    }

}
