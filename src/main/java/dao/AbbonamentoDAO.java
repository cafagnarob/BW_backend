package dao;

import entities.*;
import exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import Enum.TipoAbbonamento;

import javax.print.attribute.standard.DateTimeAtCompleted;
import java.time.LocalDate;

public class AbbonamentoDAO {
    private final EntityManager entityManager;
    private final PuntoDiEmissioneDAO puntoDiEmissioneDAO;

    public AbbonamentoDAO(EntityManager entityManager, PuntoDiEmissioneDAO puntoDiEmissioneDAO) {
        this.entityManager = entityManager;
        this.puntoDiEmissioneDAO = puntoDiEmissioneDAO;
    }


    //save
    public void save(Abbonamento newAbbonamnto) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            this.entityManager.persist(newAbbonamnto);
            transaction.commit();
            System.out.println("Il nuovo abbonamento " + newAbbonamnto + "è stato aggiungo al DB");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Errore nel salvataggio del DB " + e.getMessage());
        }
    }

    //get
    public Abbonamento getById(Long id) {
        Abbonamento fromDB = this.entityManager.find(Abbonamento.class, id);
        if (fromDB == null) throw new NotFoundException("Abbonamento non trovato");
        System.out.println("MANUTENZIONE RICHIESTA" + fromDB);
        return fromDB;

    }

    // delete
    public void delete(Long id) {
        Abbonamento fromDB = this.getById(id);
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();
        this.entityManager.remove(fromDB);
        transaction.commit();
        System.out.println("Abbonamento " + fromDB + "è stato rimosso dal DB");
    }

    // Compra abbonamento (controllo sulla tessera ed aggiornamento del portafoglio)
    public void compraAbbonamento(long idTessera, long idUtente, long idPuntoEmissione, String tipologia, double prezzo) {

        Tessera tessera = entityManager.find(Tessera.class, idTessera);
        if (tessera == null) {
            throw new NotFoundException("Impossibile avviare l'abbonamento: Tessera ID " + idTessera + " non trovata!");
        }

        if (tessera.getData_di_scadenza().isBefore(LocalDate.now())) {
            throw new IllegalStateException("Impossibile fare l'abbonamento: La tessera inserita è scaduta!");
        }

        Utente utente = entityManager.find(Utente.class, idUtente);
        if (utente == null) {
            throw new NotFoundException("Impossibile fare l'abbonamento: Utente ID " + idUtente + " non trovato!");
        }

        if (utente.getPortafoglio() < prezzo) {
            throw new IllegalStateException("Credito insufficiente!");
        }

        utente.setPortafoglio(utente.getPortafoglio() - prezzo);

        PuntoDiEmissione punto = this.puntoDiEmissioneDAO.getById(idPuntoEmissione);

        LocalDate dataInizio = LocalDate.now();
        LocalDate dataFine;

        TipoAbbonamento tipoEnum;

        if (tipologia.equalsIgnoreCase("SETTIMANALE")) {
            tipoEnum = TipoAbbonamento.SETTIMANALE;
        } else if (tipologia.equalsIgnoreCase("MENSILE")) {
            tipoEnum = TipoAbbonamento.MENSILE;
        } else {
            throw new NotFoundException("Tipologia non valida!");
        }

        Abbonamento nuovoAbbonamento = new Abbonamento(dataInizio, punto, prezzo, tipoEnum, tessera);

        EntityTransaction transaction = entityManager.getTransaction();

            transaction.begin();
            entityManager.merge(utente);
            entityManager.persist(nuovoAbbonamento);
            transaction.commit();

            System.out.println("Abbonamento acquistato!");
    }

    public void popolaSeVuoto() {
        long count = entityManager.createQuery("SELECT COUNT(a) FROM Abbonamento a", Long.class).getSingleResult();
        if (count == 0) {
            //inserire qui nuovi abbonamenti con i save
            //aggiungere poi il metodo nel main

            System.out.println("Abbonamenti aggiunti!");
        } else {
            System.out.println("Tabella abbonamenti piena");
        }
    }

}
