package dao;

import entities.Biglietto;
import entities.Mezzo;
import entities.PuntoDiEmissione;
import entities.Utente;
import exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class BigliettoDAO {

    public final EntityManager entityManager;

    public BigliettoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    // ----------------- SALVA BIGLIETTO -----------------------

    public void save(Biglietto biglietto) {
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(biglietto);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Errore nel salvataggio del DB " + ex.getMessage());

        }
    }


    // ----------------- COMPRA BIGLIETTO -----------------------

    public void compraBiglietto(Utente utente, Biglietto biglietto) {
        PuntoDiEmissione puntoDiEmissione = biglietto.getLuogoDiEmissione();

        if (utente.getPortafoglio() < biglietto.getPrezzo()) {
            System.out.println("Credito insufficiente per completare l'acquisto. Prezzo: " + biglietto.getPrezzo());
        } else {
            EntityTransaction transaction = entityManager.getTransaction();
            try {
                transaction.begin();
                entityManager.persist(biglietto);
                utente.setPortafoglio(utente.getPortafoglio() - biglietto.getPrezzo());
                if (utente.getListaBigliettiDellUtente() == null) {
                    utente.setListaBigliettiDellUtente(new ArrayList<>());
                }
                utente.getListaBigliettiDellUtente().add(biglietto);
                entityManager.merge(utente);
                transaction.commit();
                System.out.println("Acquisto andato a buon fine.");
                System.out.println(biglietto);
            } catch (Exception ex) {
                if (transaction.isActive()) transaction.rollback();
                ex.printStackTrace();
            }
        }

    }


    public Biglietto getById(Long id) {
        Biglietto fromDB = this.entityManager.find(Biglietto.class, id);
        if (fromDB == null) throw new NotFoundException("Biglietto non trovata");
        System.out.println("BIGLIETTO RICHIESTA" + fromDB);
        return fromDB;

    }

    // ----------------- VIDIMA BIGLIETTO -----------------------

    public void vidimaBiglietto(Biglietto biglietto, Mezzo mezzo) {

        if (biglietto.getOrarioVidimazione() != null) {
            System.out.println("Biglietto già convalidato alle " + biglietto.getOrarioVidimazione());
            return;
        }

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            LocalDateTime ora = LocalDateTime.now();
            biglietto.setOrarioVidimazione(ora);
            biglietto.setMezzo(mezzo);
            entityManager.merge(biglietto); // ← persist the changes
            transaction.commit();
            System.out.println("Biglietto " + biglietto.getId()
                    + " vidimato sul mezzo " + mezzo.getId()
                    + " alle " + ora
                    + ". Valido fino alle: " + ora.plusMinutes(90));
        } catch (Exception ex) {
            if (transaction.isActive()) transaction.rollback();
            ex.printStackTrace();
        }
    }


    // ----------------- ANNULLA BIGLIETTO -----------------------
    public void annullaBiglietto(Biglietto biglietto) {
        if (biglietto.getOrarioVidimazione() != null) {
            System.out.println("Impossibile annullare: il biglietto è già stato vidimato.");
            return;
        }

        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Biglietto managed = entityManager.contains(biglietto)
                    ? biglietto
                    : entityManager.merge(biglietto);
            entityManager.remove(managed);
            transaction.commit();
            System.out.println("Biglietto " + biglietto.getId() + " annullato e rimborsato con successo.");
        } catch (Exception ex) {
            if (transaction.isActive()) transaction.rollback();
            ex.printStackTrace();
        }
    }


    public void popolaSeVuoto() {
        long count = entityManager.createQuery("SELECT COUNT(b) FROM Biglietto b", Long.class).getSingleResult();

        if (count == 0) {
            List<Mezzo> mezzi = entityManager.createQuery("SELECT m FROM Mezzo m", Mezzo.class).getResultList();
            List<PuntoDiEmissione> punti = entityManager.createQuery("SELECT p FROM PuntoDiEmissione p", entities.PuntoDiEmissione.class).getResultList();

            PuntoDiEmissione rivenditore1 = punti.get(0);
            PuntoDiEmissione rivenditore2 = punti.get(1);
            PuntoDiEmissione rivenditore3 = punti.get(2);
            PuntoDiEmissione rivenditore4 = punti.get(3);
            PuntoDiEmissione rivenditore5 = punti.get(4);

            PuntoDiEmissione distributore1 = punti.get(5);
            PuntoDiEmissione distributore2 = punti.get(6);
            PuntoDiEmissione distributore3 = punti.get(7);
            PuntoDiEmissione distributore4 = punti.get(8);


            Mezzo m1 = mezzi.get(0);
            Mezzo m2 = mezzi.get(1);
            Mezzo m3 = mezzi.get(2);
            Mezzo m4 = mezzi.get(3);
            Mezzo m5 = mezzi.get(4);
            Mezzo m6 = mezzi.get(5);
            Mezzo m7 = mezzi.get(6);
            Mezzo m8 = mezzi.get(7);
            Mezzo m9 = mezzi.get(8);

            Biglietto b1 = new Biglietto(LocalDate.now().minusDays(2), rivenditore1, 1.50, LocalDateTime.now().minusDays(2).plusHours(3), m6);
            save(b1);

            Biglietto b2 = new Biglietto(LocalDate.now().minusDays(2), distributore1, 1.50, LocalDateTime.now().minusDays(2).plusHours(2), m6);
            save(b2);

            Biglietto b3 = new Biglietto(LocalDate.now().minusDays(2), rivenditore2, 2.50, LocalDateTime.now().minusDays(2).plusHours(3), m4);
            save(b3);

            Biglietto b4 = new Biglietto(LocalDate.now().minusDays(2), rivenditore3, 1.50, LocalDateTime.now().minusDays(2).plusHours(1), m4);
            save(b4);

            Biglietto b5 = new Biglietto(LocalDate.now().minusDays(3), distributore2, 1.50, LocalDateTime.now().minusDays(3).plusHours(3), m2);
            save(b5);

            Biglietto b6 = new Biglietto(LocalDate.now().minusDays(3), rivenditore4, 1.00, LocalDateTime.now().minusDays(3).plusHours(2), m2);
            save(b6);

            Biglietto b7 = new Biglietto(LocalDate.now().minusDays(3), rivenditore5, 1.50, LocalDateTime.now().minusDays(3).plusHours(1), m2);
            save(b7);

            Biglietto b8 = new Biglietto(LocalDate.now().minusDays(3), distributore3, 1.50, LocalDateTime.now().minusDays(3).plusHours(4), m2);
            save(b8);

            Biglietto b9 = new Biglietto(LocalDate.now().minusDays(4), distributore4, 3.00, LocalDateTime.now().minusDays(4).plusHours(3), m9);
            save(b9);

            Biglietto b10 = new Biglietto(LocalDate.now().minusDays(4), rivenditore1, 1.50, LocalDateTime.now().minusDays(4).plusHours(2), m9);
            save(b10);

            System.out.println("Biglietti aggiunti!");
        } else {
            System.out.println("Tabella Biglietto piena");
        }
    }


}
