package dao;

import entities.*;
import Enum.StatoDistributore;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class BigliettoDAO {

    private final EntityManager entityManager;

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

        if (puntoDiEmissione instanceof Distributore distributore) {
            if (distributore.getStato() == StatoDistributore.NON_DISPONIBILE) {
                System.out.println("Il distributore non è disponibile: " + distributore);
                return; // ← stop here
            }
            System.out.println("Acquisto in corso presso il distributore #" + puntoDiEmissione.getId() + "...");
        }

        if (utente.getPortafoglio() < biglietto.getPrezzo()) {
            System.out.println("Credito insufficiente per completare l'acquisto. Prezzo: " + biglietto.getPrezzo());
        } else {
            try {
                utente.setPortafoglio(utente.getPortafoglio() - biglietto.getPrezzo());
                System.out.println("Acquisto andato a buon fine.");
                System.out.println(biglietto);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

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

            List<PuntoDiEmissione> punti = entityManager.createQuery("SELECT p FROM PuntoDiEmissione p", entities.PuntoDiEmissione.class).getResultList();

            PuntoDiEmissione rivenditore1 = punti.get(0);
            PuntoDiEmissione rivenditore2 = punti.size() > 1 ? punti.get(1) : rivenditore1;
            PuntoDiEmissione rivenditore3 = punti.size() > 2 ? punti.get(2) : rivenditore1;
            PuntoDiEmissione rivenditore4 = punti.size() > 3 ? punti.get(3) : rivenditore1;
            PuntoDiEmissione rivenditore5 = punti.size() > 4 ? punti.get(4) : rivenditore1;

            PuntoDiEmissione distributore1 = punti.size() > 5 ? punti.get(5) : rivenditore1;
            PuntoDiEmissione distributore2 = punti.size() > 6 ? punti.get(6) : rivenditore1;
            PuntoDiEmissione distributore3 = punti.size() > 7 ? punti.get(7) : rivenditore1;
            PuntoDiEmissione distributore4 = punti.size() > 8 ? punti.get(8) : rivenditore1;

            Biglietto b1 = new Biglietto(LocalDate.now(), rivenditore1, 1.50);
            save(b1);

            Biglietto b2 = new Biglietto(LocalDate.now(), distributore1, 1.50);
            save(b2);

            Biglietto b3 = new Biglietto(LocalDate.now(), rivenditore2, 2.50);
            save(b3);

            Biglietto b4 = new Biglietto(LocalDate.now().minusDays(1), rivenditore3, 1.50);
            save(b4);

            Biglietto b5 = new Biglietto(LocalDate.now().minusDays(2), distributore2, 1.50);
            save(b5);

            Biglietto b6 = new Biglietto(LocalDate.now(), rivenditore4, 1.00);
            save(b6);

            Biglietto b7 = new Biglietto(LocalDate.now(), rivenditore5, 1.50);
            save(b7);

            Biglietto b8 = new Biglietto(LocalDate.now(), distributore3, 1.50);
            save(b8);

            Biglietto b9 = new Biglietto(LocalDate.now(), distributore4, 3.00);
            save(b9);

            Biglietto b10 = new Biglietto(LocalDate.now(), rivenditore1, 1.50);
            save(b10);

            System.out.println("Biglietti aggiunti!");
        } else {
            System.out.println("Tabella Biglietto piena");
        }
    }


}
