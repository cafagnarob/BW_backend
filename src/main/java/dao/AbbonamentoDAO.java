package dao;

import entities.*;
import exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import Enum.TipoAbbonamento;

import javax.print.attribute.standard.DateTimeAtCompleted;
import java.time.LocalDate;
import java.util.List;

public class AbbonamentoDAO {
    private final EntityManager entityManager;
    private final PuntoDiEmissioneDAO puntoDiEmissioneDAO;

    public AbbonamentoDAO(EntityManager entityManager, PuntoDiEmissioneDAO puntoDiEmissioneDAO) {
        this.entityManager = entityManager;
        this.puntoDiEmissioneDAO = puntoDiEmissioneDAO;
    }


    //save
    public void save(Abbonamento newAbbonamento) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            this.entityManager.persist(newAbbonamento);
            transaction.commit();
            System.out.println("Il nuovo abbonamento " + newAbbonamento + "è stato aggiungo al DB");
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

        Abbonamento nuovoAbbonamento = new Abbonamento(dataInizio, punto, tipoEnum, tessera);

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

            List<PuntoDiEmissione> punti = entityManager.createQuery("SELECT p FROM PuntoDiEmissione p", PuntoDiEmissione.class).getResultList();
            List<Tessera> tessere = entityManager.createQuery("SELECT t FROM Tessera t", Tessera.class).getResultList();

            PuntoDiEmissione rivenditore1 = punti.get(0);
            PuntoDiEmissione rivenditore2 = punti.get(1);
            PuntoDiEmissione rivenditore3 = punti.get(2);
            PuntoDiEmissione rivenditore4 = punti.get(3);
            PuntoDiEmissione rivenditore5 = punti.get(4);

            PuntoDiEmissione distributore1 = punti.get(5);
            PuntoDiEmissione distributore2 = punti.get(6);
            PuntoDiEmissione distributore3 = punti.get(7);
            PuntoDiEmissione distributore4 = punti.get(8);

            Tessera t1 = tessere.get(0);
            Tessera t2 = tessere.get(1);
            Tessera t3 = tessere.get(2);
            Tessera t4 = tessere.get(3);
            Tessera t5 = tessere.get(4);
            Tessera t6 = tessere.get(5);
            Tessera t7 = tessere.get(6);
            Tessera t8 = tessere.get(7);
            Tessera t9 = tessere.get(8);

            Abbonamento a1 = new Abbonamento(LocalDate.now(), rivenditore1, TipoAbbonamento.SETTIMANALE, t1); save(a1);
            Abbonamento a2 = new Abbonamento(LocalDate.now(), rivenditore2, TipoAbbonamento.MENSILE, t2); save(a2);
            Abbonamento a3 = new Abbonamento(LocalDate.now(), rivenditore3, TipoAbbonamento.SETTIMANALE, t3); save(a3);
            Abbonamento a4 = new Abbonamento(LocalDate.now().minusDays(5), distributore1, TipoAbbonamento.MENSILE, t4); save(a4);
            Abbonamento a5 = new Abbonamento(LocalDate.now(), distributore2, TipoAbbonamento.SETTIMANALE, t5); save(a5);
            Abbonamento a6 = new Abbonamento(LocalDate.now(), rivenditore4, TipoAbbonamento.MENSILE, t6); save(a6);
            Abbonamento a7 = new Abbonamento(LocalDate.now(), rivenditore5, TipoAbbonamento.SETTIMANALE, t7); save(a7);
            Abbonamento a8 = new Abbonamento(LocalDate.now(), distributore3, TipoAbbonamento.MENSILE, t8); save(a8);
            Abbonamento a9 = new Abbonamento(LocalDate.now(), distributore4, TipoAbbonamento.SETTIMANALE, t9); save(a9);

            System.out.println("Tessere aggiunte!");
        } else {
            System.out.println("Tabella Abbonamento piena");
        }
    }

}
