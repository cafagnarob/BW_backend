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

            List<PuntoDiEmissione> punti = entityManager.createQuery("SELECT p FROM PuntoDiEmissione p", PuntoDiEmissione.class).getResultList();
            List<Tessera> tessere = entityManager.createQuery("SELECT t FROM Tessera t", Tessera.class).getResultList();

            PuntoDiEmissione rivenditore1 = punti.get(0);
            PuntoDiEmissione rivenditore2 = punti.size() > 1 ? punti.get(1) : rivenditore1;
            PuntoDiEmissione rivenditore3 = punti.size() > 2 ? punti.get(2) : rivenditore1;
            PuntoDiEmissione rivenditore4 = punti.size() > 3 ? punti.get(3) : rivenditore1;
            PuntoDiEmissione rivenditore5 = punti.size() > 4 ? punti.get(4) : rivenditore1;

            PuntoDiEmissione distributore1 = punti.size() > 5 ? punti.get(5) : rivenditore1;
            PuntoDiEmissione distributore2 = punti.size() > 6 ? punti.get(6) : rivenditore1;
            PuntoDiEmissione distributore3 = punti.size() > 7 ? punti.get(7) : rivenditore1;
            PuntoDiEmissione distributore4 = punti.size() > 8 ? punti.get(8) : rivenditore1;

            Tessera t1 = tessere.get(0);
            Tessera t2 = tessere.size() > 1 ? tessere.get(1) : t1;
            Tessera t3 = tessere.size() > 2 ? tessere.get(2) : t1;
            Tessera t4 = tessere.size() > 3 ? tessere.get(3) : t1;
            Tessera t5 = tessere.size() > 4 ? tessere.get(4) : t1;
            Tessera t6 = tessere.size() > 5 ? tessere.get(5) : t1;
            Tessera t7 = tessere.size() > 6 ? tessere.get(6) : t1;
            Tessera t8 = tessere.size() > 7 ? tessere.get(7) : t1;
            Tessera t9 = tessere.size() > 8 ? tessere.get(8) : t1;

            Abbonamento a1 = new Abbonamento(java.time.LocalDate.now(), rivenditore1, 12.00, TipoAbbonamento.SETTIMANALE, t1); save(a1);
            Abbonamento a2 = new Abbonamento(java.time.LocalDate.now(), rivenditore2, 35.00, TipoAbbonamento.MENSILE, t2); save(a2);
            Abbonamento a3 = new Abbonamento(java.time.LocalDate.now(), rivenditore3, 12.00, TipoAbbonamento.SETTIMANALE, t3); save(a3);
            Abbonamento a4 = new Abbonamento(java.time.LocalDate.now().minusDays(5), distributore1, 35.00, TipoAbbonamento.MENSILE, t4); save(a4);
            Abbonamento a5 = new Abbonamento(java.time.LocalDate.now(), distributore2, 12.00, TipoAbbonamento.SETTIMANALE, t5); save(a5);
            Abbonamento a6 = new Abbonamento(java.time.LocalDate.now(), rivenditore4, 35.00, TipoAbbonamento.MENSILE, t6); save(a6);
            Abbonamento a7 = new Abbonamento(java.time.LocalDate.now(), rivenditore5, 12.00, TipoAbbonamento.SETTIMANALE, t7); save(a7);
            Abbonamento a8 = new Abbonamento(java.time.LocalDate.now(), distributore3, 35.00, TipoAbbonamento.MENSILE, t8); save(a8);
            Abbonamento a9 = new Abbonamento(java.time.LocalDate.now(), distributore4, 12.00, TipoAbbonamento.SETTIMANALE, t9); save(a9);

            System.out.println("Tessere aggiunte!");
        } else {
            System.out.println("Tabella Tessera piena");
        }
    }

}
