package robertocafagna;

import dao.PuntoDiEmissioneDAO;
import dao.TesseraDAO;
import dao.TitoloDiViaggioDAO;
import dao.UtenteDAO;
import entities.Distributore;
import entities.PuntoDiEmissione;
import entities.Rivenditore;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import Enum.StatoDistributore;
import Enum.TipoUtente;
import Enum.TipoAbbonamento;

public class Application {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BW_backendpu");


    public static void main(String[] args) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        UtenteDAO utenteDAO = new UtenteDAO(entityManager);
        PuntoDiEmissioneDAO dao = new PuntoDiEmissioneDAO(entityManager);
        TitoloDiViaggioDAO TdiViaggioDAO = new TitoloDiViaggioDAO(entityManager);
TesseraDAO tesseraDAO = new TesseraDAO(entityManager);

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


         //creo punti di emissioni
//
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
//
//


        System.out.println("Hello World!");
    }


}
