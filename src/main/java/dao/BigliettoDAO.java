package dao;

import entities.*;
import Enum.StatoDistributore;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDateTime;


public class BigliettoDAO {

    public final EntityManager entityManager;

    public BigliettoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    // ----------------- SALVA BIGLIETTO -----------------------

    public void salvaBiglietto(Biglietto biglietto) {
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

        try {
            salvaBiglietto(biglietto);
            utente.setPortafoglio(utente.getPortafoglio() - biglietto.getPrezzo());
            System.out.println("Acquisto andato a buon fine.");
            System.out.println(biglietto);
        } catch (Exception ex) {
            ex.printStackTrace();
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

}
