package robertocafagna;

import dao.*;
import entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import Enum.StatoDistributore;
import Enum.TipoUtente;
import Enum.TipoAbbonamento;
import Enum.StatoMezzo;
import Enum.TipoMezzo;

public class Application {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BW_backendpu");


    public static void main(String[] args) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        UtenteDAO utenteDAO = new UtenteDAO(entityManager);
        PuntoDiEmissioneDAO dao = new PuntoDiEmissioneDAO(entityManager);
        TitoloDiViaggioDAO TdiViaggioDAO = new TitoloDiViaggioDAO(entityManager);
        TesseraDAO tesseraDAO = new TesseraDAO(entityManager);
        MezzoDAO mezzoDAO = new MezzoDAO(entityManager);

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


//        // CREO NUOVI UTENTI

        Utente u1 = new Utente("Super", "Admin", "admin@system.com", 99, 9999.99, TipoUtente.ADMIN);
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

//
//
//         //creo punti di emissioni
        System.out.println("Inizio creazione dei distributori e rivenditori...");

        PuntoDiEmissione rivenditore1 = new Rivenditore("Via Roma 15, Milano");
        ((Rivenditore) rivenditore1).setNome("Tabaccheria Rossi");

        PuntoDiEmissione rivenditore2 = new Rivenditore("Piazza Garibaldi 2, Napoli");
        ((Rivenditore) rivenditore2).setNome("Edicola Stazione");

        PuntoDiEmissione rivenditore3 = new Rivenditore("Corso Vittorio Emanuele 88, Bari");
        ((Rivenditore) rivenditore3).setNome("Bar dello Sport");

        PuntoDiEmissione rivenditore4 = new Rivenditore("Via Dante 42, Firenze");
        ((Rivenditore) rivenditore4).setNome("Tabacchi n.4");

        PuntoDiEmissione rivenditore5 = new Rivenditore("Via dei Fori Imperiali 5, Roma");
        ((Rivenditore) rivenditore5).setNome("Edicola Central");

        //
        PuntoDiEmissione distributore1 = new Distributore(StatoDistributore.DISPONIBILE, "Stazione Termini, Roma");
        PuntoDiEmissione distributore2 = new Distributore(StatoDistributore.DISPONIBILE, "Stazione Cadorna, Milano");
        PuntoDiEmissione distributore3 = new Distributore(StatoDistributore.DISPONIBILE, "Piazza Aldo Moro, Bari");
        PuntoDiEmissione distributore4 = new Distributore(StatoDistributore.DISPONIBILE, "Via Toledo 110, Napoli");
        PuntoDiEmissione distributore5 = new Distributore(StatoDistributore.NON_DISPONIBILE, "Fermata Tram 14, Torino");


        dao.SalvaPuntoDiEmissione(rivenditore1);
        dao.SalvaPuntoDiEmissione(rivenditore2);
        dao.SalvaPuntoDiEmissione(rivenditore3);
        dao.SalvaPuntoDiEmissione(rivenditore4);
        dao.SalvaPuntoDiEmissione(rivenditore5);

        dao.SalvaPuntoDiEmissione(distributore1);
        dao.SalvaPuntoDiEmissione(distributore2);
        dao.SalvaPuntoDiEmissione(distributore3);
        dao.SalvaPuntoDiEmissione(distributore4);
        dao.SalvaPuntoDiEmissione(distributore5);

        System.out.println("Tutti i distributori e rivenditori sono stati elaborati con successo!");

        System.out.println("============CREAZIONE MEZZI===========");

        Mezzo m1 = new Mezzo(50, StatoMezzo.FERMO, TipoMezzo.AUTOBUS);
        mezzoDAO.save(m1);
        Mezzo m2 = new Mezzo(50, StatoMezzo.MANUTENZIONE, TipoMezzo.AUTOBUS);
        mezzoDAO.save(m2);
        Mezzo m3 = new Mezzo(50, StatoMezzo.SERVIZIO, TipoMezzo.AUTOBUS);
        mezzoDAO.save(m3);
        Mezzo m4 = new Mezzo(50, StatoMezzo.SERVIZIO, TipoMezzo.AUTOBUS);
        mezzoDAO.save(m4);
        Mezzo m5 = new Mezzo(50, StatoMezzo.SERVIZIO, TipoMezzo.AUTOBUS);
        mezzoDAO.save(m5);
        Mezzo m6 = new Mezzo(80, StatoMezzo.MANUTENZIONE, TipoMezzo.TRAM);
        mezzoDAO.save(m6);
        Mezzo m7 = new Mezzo(80, StatoMezzo.SERVIZIO, TipoMezzo.TRAM);
        mezzoDAO.save(m7);
        Mezzo m8 = new Mezzo(80, StatoMezzo.SERVIZIO, TipoMezzo.TRAM);
        mezzoDAO.save(m8);
        Mezzo m9 = new Mezzo(80, StatoMezzo.MANUTENZIONE, TipoMezzo.TRAM);
        mezzoDAO.save(m9);
        Mezzo m10 = new Mezzo(80, StatoMezzo.FERMO, TipoMezzo.TRAM);
        mezzoDAO.save(m10);

        System.out.println("10 Mezzi inseriti con successo");

// creazioni biglietti e abbonamenti
        Biglietto b1 = new Biglietto(java.time.LocalDate.now(), rivenditore1, 1.50);
        TdiViaggioDAO.save(b1);

        Biglietto b2 = new Biglietto(java.time.LocalDate.now(), distributore1, 1.50);
        TdiViaggioDAO.save(b2);

        Biglietto b3 = new Biglietto(java.time.LocalDate.now(), rivenditore2, 2.50);
        TdiViaggioDAO.save(b3);

        Biglietto b4 = new Biglietto(java.time.LocalDate.now().minusDays(1), rivenditore3, 1.50);
        TdiViaggioDAO.save(b4);

        Biglietto b5 = new Biglietto(java.time.LocalDate.now().minusDays(2), distributore2, 1.50);
        TdiViaggioDAO.save(b5);

        Biglietto b6 = new Biglietto(java.time.LocalDate.now(), rivenditore4, 1.00);
        TdiViaggioDAO.save(b6);

        Biglietto b7 = new Biglietto(java.time.LocalDate.now(), rivenditore5, 1.50);
        TdiViaggioDAO.save(b7);

        Biglietto b8 = new Biglietto(java.time.LocalDate.now(), distributore3, 1.50);
        TdiViaggioDAO.save(b8);

        Biglietto b9 = new Biglietto(java.time.LocalDate.now(), distributore4, 3.00);
        TdiViaggioDAO.save(b9);

        Biglietto b10 = new Biglietto(java.time.LocalDate.now(), rivenditore1, 1.50);
        TdiViaggioDAO.save(b10);

        Tessera t1 = tesseraDAO.creaTessera(rivenditore1, u1);
        tesseraDAO.save(t1);
        Abbonamento a1 = new Abbonamento(java.time.LocalDate.now(), rivenditore1, 12.00, TipoAbbonamento.SETTIMANALE, t1);
        TdiViaggioDAO.save(a1);

        Tessera t2 = tesseraDAO.creaTessera(rivenditore2, u2);
        tesseraDAO.save(t2);
        Abbonamento a2 = new Abbonamento(java.time.LocalDate.now(), rivenditore2, 35.00, TipoAbbonamento.MENSILE, t2);
        TdiViaggioDAO.save(a2);

        Tessera t3 = tesseraDAO.creaTessera(rivenditore3, u3);
        tesseraDAO.save(t3);
        Abbonamento a3 = new Abbonamento(java.time.LocalDate.now(), rivenditore3, 12.00, TipoAbbonamento.SETTIMANALE, t3);
        TdiViaggioDAO.save(a3);

        Tessera t4 = tesseraDAO.creaTessera(distributore1, u4);
        tesseraDAO.save(t4);
        Abbonamento a4 = new Abbonamento(java.time.LocalDate.now().minusDays(5), distributore1, 35.00, TipoAbbonamento.MENSILE, t4);
        TdiViaggioDAO.save(a4);

        Tessera t5 = tesseraDAO.creaTessera(distributore2, u5);
        tesseraDAO.save(t5);
        Abbonamento a5 = new Abbonamento(java.time.LocalDate.now(), distributore2, 12.00, TipoAbbonamento.SETTIMANALE, t5);
        TdiViaggioDAO.save(a5);

        Tessera t6 = tesseraDAO.creaTessera(rivenditore4, u6);
        tesseraDAO.save(t6);
        Abbonamento a6 = new Abbonamento(java.time.LocalDate.now(), rivenditore4, 35.00, TipoAbbonamento.MENSILE, t6);
        TdiViaggioDAO.save(a6);

        Tessera t7 = tesseraDAO.creaTessera(rivenditore5, u7);
        tesseraDAO.save(t7);
        Abbonamento a7 = new Abbonamento(java.time.LocalDate.now(), rivenditore5, 12.00, TipoAbbonamento.SETTIMANALE, t7);
        TdiViaggioDAO.save(a7);

        Tessera t8 = tesseraDAO.creaTessera(distributore3, u8);
        tesseraDAO.save(t8);
        Abbonamento a8 = new Abbonamento(java.time.LocalDate.now(), distributore3, 35.00, TipoAbbonamento.MENSILE, t8);
        TdiViaggioDAO.save(a8);

        Tessera t9 = tesseraDAO.creaTessera(distributore4, u9);
        tesseraDAO.save(t9);
        Abbonamento a9 = new Abbonamento(java.time.LocalDate.now(), distributore4, 12.00, TipoAbbonamento.SETTIMANALE, t9);
        TdiViaggioDAO.save(a9);

        Tessera t10 = tesseraDAO.creaTessera(rivenditore1, u10);
        tesseraDAO.save(t10);
        Abbonamento a10 = new Abbonamento(java.time.LocalDate.now(), rivenditore1, 35.00, TipoAbbonamento.MENSILE, t10);
        TdiViaggioDAO.save(a10);

        System.out.println("============CREAZIONE BIGLIETTI SUI MEZZI===========");

        // Biglietti Vidimati (Scaduti)
        Biglietto bv1 = new Biglietto(java.time.LocalDate.now(), rivenditore1, 1.50);
        bv1.setOrarioVidimazione(java.time.LocalTime.now().minusHours(3));
        bv1.setMezzo(m1);
        TdiViaggioDAO.save(bv1);

        Biglietto bv2 = new Biglietto(java.time.LocalDate.now(), distributore1, 1.50);
        bv2.setOrarioVidimazione(java.time.LocalTime.now().minusMinutes(45));
        bv2.setMezzo(m3);
        TdiViaggioDAO.save(bv2);

        Biglietto bv3 = new Biglietto(java.time.LocalDate.now().minusDays(1), rivenditore2, 1.50);
        bv3.setOrarioVidimazione(java.time.LocalTime.of(8, 15));
        bv3.setMezzo(m7);
        TdiViaggioDAO.save(bv3);

        Biglietto bv4 = new Biglietto(java.time.LocalDate.now(), distributore2, 2.50);
        bv4.setOrarioVidimazione(java.time.LocalTime.now().minusHours(1));
        bv4.setMezzo(m8);
        TdiViaggioDAO.save(bv4);

        Biglietto bv5 = new Biglietto(java.time.LocalDate.now(), rivenditore3, 1.50);
        bv5.setOrarioVidimazione(java.time.LocalTime.now().minusMinutes(10));
        bv5.setMezzo(m4);
        TdiViaggioDAO.save(bv5);

        //  Biglietti Non Vidimati (Ancora Utilizzabili)
        Biglietto bn1 = new Biglietto(java.time.LocalDate.now(), rivenditore1, 1.50);
        TdiViaggioDAO.save(bn1);

        Biglietto bn2 = new Biglietto(java.time.LocalDate.now(), distributore1, 1.50);
        TdiViaggioDAO.save(bn2);

        Biglietto bn3 = new Biglietto(java.time.LocalDate.now(), rivenditore4, 1.50);
        TdiViaggioDAO.save(bn3);

        Biglietto bn4 = new Biglietto(java.time.LocalDate.now(), distributore3, 2.50);
        TdiViaggioDAO.save(bn4);

        Biglietto bn5 = new Biglietto(java.time.LocalDate.now(), rivenditore5, 1.50);
        TdiViaggioDAO.save(bn5);



        System.out.println("Hello World!");
    }

    ;
}

