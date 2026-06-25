package dao;

import entities.PuntoDiEmissione;
import entities.Tessera;
import entities.Utente;
import exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import Enum.TipoAbbonamento;
import jakarta.persistence.NoResultException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TesseraDAO {
    private final EntityManager entityManager;

    public TesseraDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //save
    public void save(Tessera newTessera) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {

            transaction.begin();
            this.entityManager.persist(newTessera);
            transaction.commit();
            System.out.println("La TESSERA " + newTessera + "è stato aggiungo al DB");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Errore nel salvataggio del DB " + e.getMessage());
        }
    }

    //get
    // STAMPA INFO TESSERA DATO ID-TESSERA
    public Tessera getById(Long id) {
        Tessera fromDB = this.entityManager.find(Tessera.class, id);
        if (fromDB == null) throw new NotFoundException("Tessera non trovato");
        System.out.println("TESSERA RICHIESTA" + fromDB);
        return fromDB;

    }

    // delete
    public void delete(Long id) {
        Tessera fromDB = this.getById(id);
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();
        this.entityManager.remove(fromDB);
        transaction.commit();
        System.out.println("LA TESSERA " + fromDB + "è stato rimosso dal DB");
    }

    // Compra Tessera
    public void compraTessera(PuntoDiEmissione luogo, Utente utente) {
        if (luogo == null || utente == null) {
            throw new NotFoundException("Impossibile comprare una nuova tessera!");
        }
        Tessera nuovaTessera = new Tessera(LocalDate.now(), luogo, utente);

        this.save(nuovaTessera);
    }

    public Tessera getTesseraByUtenteId(Long utenteId) {
        try {
            return entityManager.createQuery(
                            "SELECT t FROM Tessera t WHERE t.utente.id = :id",
                            Tessera.class
                    )
                    .setParameter("id", utenteId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    public void update(Tessera tessera) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {

            transaction.begin();
            entityManager.merge(tessera);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }


    public Tessera insertTessera(LocalDate data, PuntoDiEmissione puntoDiEmissione, Utente utente) {
        return new Tessera(data, puntoDiEmissione, utente);
    }

    public Tessera getTesseraByUtente(Utente utente) {
        try {
            return entityManager.createQuery(
                            "SELECT t FROM Tessera t WHERE t.utente = :utente", Tessera.class)
                    .setParameter("utente", utente)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


    public void popolaSeVuoto() {
        long count = entityManager.createQuery("SELECT COUNT(t) FROM Tessera t", Long.class).getSingleResult();

        if (count == 0) {

            List<PuntoDiEmissione> punti = entityManager.createQuery("SELECT p FROM PuntoDiEmissione p", PuntoDiEmissione.class).getResultList();
            List<Utente> utenti = entityManager.createQuery("SELECT u FROM Utente u", Utente.class).getResultList();


            PuntoDiEmissione rivenditore1 = punti.get(0);
            PuntoDiEmissione rivenditore2 = punti.get(1);
            PuntoDiEmissione rivenditore3 = punti.get(2);
            PuntoDiEmissione rivenditore4 = punti.get(3);
            PuntoDiEmissione rivenditore5 = punti.get(4);

            PuntoDiEmissione distributore1 = punti.get(5);
            PuntoDiEmissione distributore2 = punti.get(6);
            PuntoDiEmissione distributore3 = punti.get(7);
            PuntoDiEmissione distributore4 = punti.get(8);

            Utente u1 = utenti.get(0);
            Utente u2 = utenti.get(1);
            Utente u3 = utenti.get(2);
            Utente u4 = utenti.get(3);
            Utente u5 = utenti.get(4);
            Utente u6 = utenti.get(5);
            Utente u7 = utenti.get(6);
            Utente u8 = utenti.get(7);
            Utente u9 = utenti.get(8);

            List<Tessera> tessere = new ArrayList<>();

            tessere.add(insertTessera(LocalDate.of(2024, 5, 12), rivenditore1, u1));
            tessere.add(insertTessera(LocalDate.of(2024, 11, 20), rivenditore2, u2));
            tessere.add(insertTessera(LocalDate.of(2025, 2, 15), rivenditore3, u3));
            tessere.add(insertTessera(LocalDate.of(2025, 8, 22), distributore1, u4));
            tessere.add(insertTessera(LocalDate.of(2025, 12, 5), distributore2, u5));
            tessere.add(insertTessera(LocalDate.of(2026, 1, 18), rivenditore4, u6));
            tessere.add(insertTessera(LocalDate.of(2026, 3, 30), rivenditore5, u7));
            tessere.add(insertTessera(LocalDate.of(2026, 5, 14), distributore3, u8));
            tessere.add(insertTessera(LocalDate.of(2025, 2, 4), distributore4, u9));

            for (Tessera t : tessere) {
                try {
                    save(t);
                } catch (Exception e) {
                    System.err.println("Errore nel salvare la tessera: " + e.getMessage());
                }
            }

            System.out.println("Tessere aggiunte!");
        } else {
            System.out.println("Tabella Tessera piena");
        }
    }

}
