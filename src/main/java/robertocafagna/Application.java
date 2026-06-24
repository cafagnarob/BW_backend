package robertocafagna;
import dao.MezzoDAO;
import dao.*;
import entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import Enum.StatoDistributore;
import Enum.TipoUtente;
import Enum.TipoAbbonamento;
import Enum.StatoMezzo;
import Enum.TipoMezzo;

import java.util.Scanner;

public class Application {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BW_backendpu");


    public static void main(String[] args) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        UtenteDAO utenteDAO = new UtenteDAO(entityManager);
        MezzoDAO mezzoDAO = new MezzoDAO(entityManager);
        Scanner scanner = new Scanner(System.in);
        PuntoDiEmissioneDAO puntoDao = new PuntoDiEmissioneDAO(entityManager);
        TitoloDiViaggioDAO TdiViaggioDAO = new TitoloDiViaggioDAO(entityManager);
        TesseraDAO tesseraDAO = new TesseraDAO(entityManager);
        AbbonamentoDAO abbonamentoDAO = new AbbonamentoDAO(entityManager, puntoDao);
        TrattaDAO trattaDAO = new TrattaDAO(entityManager);

        //Ripopolamento delle tabelle
        utenteDAO.popolaSeVuoto();
        puntoDao.popolaSeVuoto();

        trattaDAO.popolaSeVuoto();
        mezzoDAO.popolaSeVuoto();
        tesseraDAO.popolaSeVuoto();
        abbonamentoDAO.popolaSeVuoto();

/*
// creazione di prova per testare metodo STAMPAINFOABBONAMENTO
        //inizio transazione per la creazione di utente, tessera,abb;
        entityManager.getTransaction().begin();

        entities.Utente nuovoUtente = new entities.Utente("Rosa", "Dev", "emanuela@test.com", 25, 50.0, TipoUtente.VIAGGIATORE);
        entityManager.persist(nuovoUtente);

        entities.PuntoDiEmissione nuovoPunto = new entities.Rivenditore("Via Vesuvio 10, Napoli");
        ((entities.Rivenditore) nuovoPunto).setNome("Tabaccheria Rossi");
        entityManager.persist(nuovoPunto);
        entityManager.getTransaction().commit();
        System.out.println(" Utente e Punto di Emissione creati!");

//compro la tess
        tesseraDAO.compraTessera(nuovoPunto, nuovoUtente);

        Long idNuovaTessera = nuovoUtente.getId();
        entities.Tessera tesseraPerTest = tesseraDAO.getById(idNuovaTessera);
//compro abbonamento
        entities.Abbonamento nuovoAbb = TdiViaggioDAO.creaAbbonamento(nuovoPunto, tesseraPerTest, 35.0, TipoAbbonamento.MENSILE);
//test di scadenza
        nuovoAbb.setDataDiScadenza(java.time.LocalDate.now().plusMonths(1));

        TdiViaggioDAO.save(nuovoAbb);


        System.out.println(" STAMPA INFO ABBONAMENTO");
        TdiViaggioDAO.stampaInfoAbbonamento(idNuovaTessera);

        System.out.println("=== FINE TEST COMPLETO ===");


        // AGGIUNGIAMO UN MEZZO
        /*Mezzo m1 = new Mezzo(StatoMezzo.FERMO, TipoMezzo.AUTOBUS);

        mezzoDAO.save(m1);*/


        // INIZIO PROGRAMMA

        System.out.println("------ INIZIO PROGRAMMA -----");
        System.out.println("------ BENVENUTO-------");
        System.out.println("------ INSERISCI LA TUA EMAIL------");
        System.out.println("ADMIN: admin@system.com ");
        System.out.println("USER SEMPLICE: mario.rossi@mail.com ");


        String email = null;
        Utente fromDB = null;
        while (fromDB == null) {
            email = scanner.nextLine().trim();
            if (email == null && !email.contains("@") && !email.contains(".")) {
                continue;
            }
            try {
                fromDB = utenteDAO.getUtenteByEmail(email);
            } catch (NoResultException e) {
                System.out.println("Email non valida");
            }
        }

        System.out.println("------ BENVENUTO " + fromDB.getNome() + " " + fromDB.getCognome() + "-------");

        if (fromDB.getTipo() == TipoUtente.ADMIN) {
            menuAdmin();
        } else {
            menuUser();
        }

        System.out.println("=== FINE TEST COMPLETO ===");


        System.out.println("Hello World!");
    }

    public static void menuAdmin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("----- 0 per uscire -------");
        System.out.println("----- 1 per la GESTIONE DEI MEZZI ------");
        System.out.println("----- 2 per la GESTIONE DEI BIGLIETTI ED ABBONAMENTI ------");
        try {
            int scelta = Integer.parseInt(scanner.nextLine().trim());
            switch (scelta) {
                case 0 -> System.out.println("---- CHIUSURA DEL PROGRAMMA ------");
                case 1 -> {
                    System.out.println("----- GESTIONE MEZZI------");
                    menuMezzi();
                }
                case 2 -> {
                    System.out.println("----- GESTIONE TITOLI DI VIAGGIO ------");
                    menuTitoloDiViaggio();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static void menuMezzi() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("----- 0 per uscire -------");
        System.out.println("----- 1 per Aggiungere un nuovo mezzo ------");
        System.out.println("----- 2 per Mandare un mezzo in manutenzione ------");
        System.out.println("----- 3 per Visualizzare il numero di volte in cui un mezzo" +
                "ha fatto una specifica tratta");
        System.out.println("----- 4 per Assegnare un mezzo ad una tratta-------");

    }

    public static void menuTitoloDiViaggio() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("----- 0 per uscire -------");
        System.out.println("----- 1 Visualizzare uno storico dei biglietti " +
                "e abbonamenti emessi in un determinato periodo di tempo ------");
        System.out.println("----- 2 per Visualizzare i biglietti vidimati in un mezzo ------");
        System.out.println("----- 3 Controllare la validità di un biglietto------");

    }


    public static void menuUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("----- 0 per uscire -------");
        System.out.println("----- 1 per Comprare biglietto ------");
        System.out.println("----- 2 per Comprare la tessera ------");
        System.out.println("----- 3 per Comprare un nuovo abbonamento------");
        try {
            int scelta = Integer.parseInt(scanner.nextLine());
            switch (scelta) {
                case 0 -> System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ;
    }

}

