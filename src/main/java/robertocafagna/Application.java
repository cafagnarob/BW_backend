package robertocafagna;

import Enum.TipoUtente;
import dao.MezzoDAO;
import dao.PuntoDiEmissioneDAO;
import dao.UtenteDAO;
import entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

import java.util.Scanner;

public class Application {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BW_backendpu");


    public static void main(String[] args) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        UtenteDAO utenteDAO = new UtenteDAO(entityManager);
        MezzoDAO mezzoDAO = new MezzoDAO(entityManager);
        PuntoDiEmissioneDAO dao = new PuntoDiEmissioneDAO(entityManager);
        Scanner scanner = new Scanner(System.in);


        // CREO NUOVI UTENTI
       /* Utente u1 = new Utente("Super", "Admin", "admin@system.com", 99, 9999.99, TipoUtente.ADMIN);
        Utente u2 = new Utente("Mario", "Rossi", "mario.rossi@mail.com", 25, 120.50, TipoUtente.VIAGGIATORE);
        Utente u3 = new Utente("Luca", "Bianchi", "luca.bianchi@mail.com", 30, 80.00, TipoUtente.VIAGGIATORE);
        Utente u4 = new Utente("Giulia", "Verdi", "giulia.verdi@mail.com", 22, 200.00, TipoUtente.VIAGGIATORE);
        Utente u5 = new Utente("Anna", "Neri", "anna.neri@mail.com", 28, 50.00, TipoUtente.VIAGGIATORE);
        Utente u6 = new Utente("Marco", "Gialli", "marco.gialli@mail.com", 35, 300.00, TipoUtente.VIAGGIATORE);
        Utente u7 = new Utente("Francesca", "Blu", "francesca.blu@mail.com", 27, 90.00, TipoUtente.VIAGGIATORE);
        Utente u8 = new Utente("Davide", "Rossi", "davide.rossi@mail.com", 31, 110.00, TipoUtente.VIAGGIATORE);
        Utente u9 = new Utente("Elena", "Ferrari", "elena.ferrari@mail.com", 24, 60.00, TipoUtente.VIAGGIATORE);
        Utente u10 = new Utente("Simone", "Romano", "simone.romano@mail.com", 29, 140.00, TipoUtente.VIAGGIATORE);
        Utente u11 = new Utente("Chiara", "Conti", "chiara.conti@mail.com", 26, 75.00, TipoUtente.VIAGGIATORE);
        Utente u12 = new Utente("Paolo", "Marino", "paolo.marino@mail.com", 40, 500.00, TipoUtente.VIAGGIATORE);
        Utente u13 = new Utente("Sara", "Greco", "sara.greco@mail.com", 23, 95.00, TipoUtente.VIAGGIATORE);
        Utente u14 = new Utente("Alessandro", "Fontana", "alessandro.fontana@mail.com", 33, 180.00, TipoUtente.VIAGGIATORE);
        Utente u15 = new Utente("Martina", "Costa", "martina.costa@mail.com", 21, 45.00, TipoUtente.VIAGGIATORE);
        Utente u16 = new Utente("Giorgio", "Lombardi", "giorgio.lombardi@mail.com", 38, 220.00, TipoUtente.VIAGGIATORE);
        Utente u17 = new Utente("Valentina", "Moretti", "valentina.moretti@mail.com", 27, 130.00, TipoUtente.VIAGGIATORE);
        Utente u18 = new Utente("Riccardo", "Barbieri", "riccardo.barbieri@mail.com", 32, 160.00, TipoUtente.VIAGGIATORE);
        Utente u19 = new Utente("Federica", "De Luca", "federica.deluca@mail.com", 26, 85.00, TipoUtente.VIAGGIATORE);
        Utente u20 = new Utente("Andrea", "Gallo", "andrea.gallo@mail.com", 29, 210.00, TipoUtente.VIAGGIATORE);

        //SALVA NUOVI UTENTI
        utenteDAO.save(u1);
        utenteDAO.save(u2);
        utenteDAO.save(u3);
        utenteDAO.save(u4);
        utenteDAO.save(u5);
        utenteDAO.save(u6);
        utenteDAO.save(u7);
        utenteDAO.save(u8);
        utenteDAO.save(u9);
        utenteDAO.save(u10);
        utenteDAO.save(u11);
        utenteDAO.save(u12);
        utenteDAO.save(u13);
        utenteDAO.save(u14);
        utenteDAO.save(u15);
        utenteDAO.save(u16);
        utenteDAO.save(u17);
        utenteDAO.save(u18);
        utenteDAO.save(u19);
        utenteDAO.save(u20);
        */


        // creo punti di emissioni

//        try {
//            System.out.println("Inizio creazione dei distributori e rivenditori...");
//
//            PuntoDiEmissione rivenditore1 = new Rivenditore("Via Roma 15, Milano");
//            ((Rivenditore) rivenditore1).setNome("Tabaccheria Rossi");
//
//            PuntoDiEmissione rivenditore2 = new Rivenditore("Piazza Garibaldi 2, Napoli");
//            ((Rivenditore) rivenditore2).setNome("Edicola Stazione");
//
//            PuntoDiEmissione rivenditore3 = new Rivenditore("Corso Vittorio Emanuele 88, Bari");
//            ((Rivenditore) rivenditore3).setNome("Bar dello Sport");
//
//            PuntoDiEmissione rivenditore4 = new Rivenditore("Via Dante 42, Firenze");
//            ((Rivenditore) rivenditore4).setNome("Tabacchi n.4");
//
//            PuntoDiEmissione rivenditore5 = new Rivenditore("Via dei Fori Imperiali 5, Roma");
//            ((Rivenditore) rivenditore5).setNome("Edicola Central");
//
//            //
//            PuntoDiEmissione distributore1 = new Distributore(StatoDistributore.DISPONIBILE, "Stazione Termini, Roma");
//            PuntoDiEmissione distributore2 = new Distributore(StatoDistributore.DISPONIBILE, "Stazione Cadorna, Milano");
//            PuntoDiEmissione distributore3 = new Distributore(StatoDistributore.DISPONIBILE, "Piazza Aldo Moro, Bari");
//            PuntoDiEmissione distributore4 = new Distributore(StatoDistributore.DISPONIBILE, "Via Toledo 110, Napoli");
//            PuntoDiEmissione distributore5 = new Distributore(StatoDistributore.NON_DISPONIBILE, "Fermata Tram 14, Torino");
//
//
//            dao.SalvaPuntoDiEmissione(rivenditore1);
//            dao.SalvaPuntoDiEmissione(rivenditore2);
//            dao.SalvaPuntoDiEmissione(rivenditore3);
//            dao.SalvaPuntoDiEmissione(rivenditore4);
//            dao.SalvaPuntoDiEmissione(rivenditore5);
//
//            dao.SalvaPuntoDiEmissione(distributore1);
//            dao.SalvaPuntoDiEmissione(distributore2);
//            dao.SalvaPuntoDiEmissione(distributore3);
//            dao.SalvaPuntoDiEmissione(distributore4);
//            dao.SalvaPuntoDiEmissione(distributore5);
//
//            System.out.println("Tutti i distributori e rivenditori sono stati elaborati con successo!");
//
//        } catch (Exception e) {
//            System.err.println("Errore durante il popolamento dei punti di emissione: " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//
//            entityManager.close();
//            entityManagerFactory.close();
//        }


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


//
//


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
