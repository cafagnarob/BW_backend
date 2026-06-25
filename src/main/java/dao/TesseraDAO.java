package dao;

import entities.Abbonamento;
import entities.PuntoDiEmissione;
import entities.Tessera;
import entities.Utente;
import exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import Enum.TipoAbbonamento;

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
    public void compraTessera(PuntoDiEmissione luogo, Utente utente){
        Tessera nuovaTessera = new Tessera();
        if(luogo == null || utente==null){
            throw new NotFoundException("Impossibile comprare una nuova tessera! Luogo o utente non validi. Riprovare.");
        } else if (utente.getPortafoglio() < nuovaTessera.getPrezzo()) {
            System.out.println("Credito insufficiente. Impossibile completare l'acquisto.");
            return;
        } else {
            nuovaTessera = new Tessera(LocalDate.now(), luogo, utente);
            utente.setPortafoglio(utente.getPortafoglio() - nuovaTessera.getPrezzo());
        }

        this.save(nuovaTessera);
    }

    public Tessera creaTessera(PuntoDiEmissione puntoDiEmissione, Utente utente) {
        return new Tessera(LocalDate.now(), puntoDiEmissione, utente);
    }

    public Tessera insertTessera(LocalDate data, PuntoDiEmissione puntoDiEmissione, Utente utente) {
        return new Tessera(data, puntoDiEmissione, utente);
    }


    public void popolaSeVuoto() {
        long count = entityManager.createQuery("SELECT COUNT(t) FROM Tessera t", Long.class).getSingleResult();

        if (count == 0) {

            List<PuntoDiEmissione> punti = entityManager.createQuery("SELECT p FROM PuntoDiEmissione p", PuntoDiEmissione.class).getResultList();
            List<Utente> utenti = entityManager.createQuery("SELECT u FROM Utente u", Utente.class).getResultList();


            PuntoDiEmissione rivenditore1 = punti.get(0);
            PuntoDiEmissione rivenditore2 = punti.size() > 1 ? punti.get(1) : rivenditore1;
            PuntoDiEmissione rivenditore3 = punti.size() > 2 ? punti.get(2) : rivenditore1;
            PuntoDiEmissione rivenditore4 = punti.size() > 3 ? punti.get(3) : rivenditore1;
            PuntoDiEmissione rivenditore5 = punti.size() > 4 ? punti.get(4) : rivenditore1;

            PuntoDiEmissione distributore1 = punti.size() > 5 ? punti.get(5) : rivenditore1;
            PuntoDiEmissione distributore2 = punti.size() > 6 ? punti.get(6) : rivenditore1;
            PuntoDiEmissione distributore3 = punti.size() > 7 ? punti.get(7) : rivenditore1;
            PuntoDiEmissione distributore4 = punti.size() > 8 ? punti.get(8) : rivenditore1;

            Utente u1 = utenti.get(0);
            Utente u2 = utenti.size() > 1 ? utenti.get(1) : u1;
            Utente u3 = utenti.size() > 2 ? utenti.get(2) : u1;
            Utente u4 = utenti.size() > 3 ? utenti.get(3) : u1;
            Utente u5 = utenti.size() > 4 ? utenti.get(4) : u1;
            Utente u6 = utenti.size() > 5 ? utenti.get(5) : u1;
            Utente u7 = utenti.size() > 6 ? utenti.get(6) : u1;
            Utente u8 = utenti.size() > 7 ? utenti.get(7) : u1;
            Utente u9 = utenti.size() > 8 ? utenti.get(8) : u1;

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
