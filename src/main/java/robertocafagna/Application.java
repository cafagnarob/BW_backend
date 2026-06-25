package robertocafagna;

import Enum.*;
import dao.*;
import entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Application {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BW_backendpu");

    static Scanner scanner = new Scanner(System.in);
    static EntityManager entityManager = entityManagerFactory.createEntityManager();
    static UtenteDAO utenteDAO = new UtenteDAO(entityManager);
    static MezzoDAO mezzoDAO = new MezzoDAO(entityManager);

    static PuntoDiEmissioneDAO puntoDao = new PuntoDiEmissioneDAO(entityManager);
    static AbbonamentoDAO abbonamentoDAO = new AbbonamentoDAO(entityManager, puntoDao);
    static TitoloDiViaggioDAO titoloDiViaggioDAO = new TitoloDiViaggioDAO(entityManager);
    static TesseraDAO tesseraDAO = new TesseraDAO(entityManager);
    static TrattaDAO trattaDAO = new TrattaDAO(entityManager);
    static PercorrenzaDAO percorrenzaDAO = new PercorrenzaDAO(entityManager);
    static ManutenzioneDAO manutenzioneDao = new ManutenzioneDAO(entityManager);
    static BigliettoDAO bigliettoDAO = new BigliettoDAO(entityManager);
    static String email = null;

    public static void main(String[] args) {


        //Ripopolamento delle tabelle
        utenteDAO.popolaSeVuoto();
        puntoDao.popolaSeVuoto();
        mezzoDAO.popolaSeVuoto();
        trattaDAO.popolaSeVuoto();
        manutenzioneDao.popolaSeVuoto();
        percorrenzaDAO.popolaSeVuoto();
        tesseraDAO.popolaSeVuoto();
        abbonamentoDAO.popolaSeVuoto();
        bigliettoDAO.popolaSeVuoto();


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


        Utente fromDB = null;
        while (fromDB == null) {
            email = scanner.nextLine().trim();
            if (email == null || !email.contains("@") || !email.contains(".")) {
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
        System.out.println("----- 0 per uscire -------");
        System.out.println("----- 1 per la GESTIONE DEI MEZZI ------");
        System.out.println("----- 2 per la GESTIONE DEI BIGLIETTI ED ABBONAMENTI ------");
        try {
            boolean flag = true;
            while (flag) {
                try {
                    int scelta = Integer.parseInt(scanner.nextLine().trim());

                    switch (scelta) {
                        case 0 -> {
                            System.out.println("---- CHIUSURA DEL PROGRAMMA ------");
                            flag = false;
                        }
                        case 1 -> {
                            System.out.println("----- GESTIONE MEZZI------");
                            menuMezzi();
                        }
                        case 2 -> {
                            System.out.println("----- GESTIONE TITOLI DI VIAGGIO ------");
                            menuTitoloDiViaggio();
                        }
                        default -> System.out.println("------- INSERISCI UN VALORE VALIDO------");
                    }
                } catch (Exception e) {
                    System.out.println("------- INSERISCI UN VALORE VALIDO------");
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public static void menuMezzi() {


        try {
            boolean flag2 = true;
            while (flag2) {
                System.out.println("----- 0 per uscire -------");
                System.out.println("----- 1 per Aggiungere un nuovo mezzo ------");
                System.out.println("----- 2 per Mandare un mezzo in manutenzione ------");
                System.out.println("----- 3 per Visualizzare il numero di volte in cui un mezzo" +
                        "ha fatto una specifica tratta");
                System.out.println("----- 4 per Assegnare un mezzo ad una tratta-------");
                try {
                    int scelta = Integer.parseInt(scanner.nextLine().trim());

                    switch (scelta) {
                        case 0 -> {
                            System.out.println("---- CHIUSURA DEL PROGRAMMA ------");
                            flag2 = false;
                        }
                        case 1 -> {
                            System.out.println("----- CREAZIONE NUOVO MEZZO------");
                            Mezzo newMezzo = menuCreazioneMezzo();
                            if (newMezzo != null) {
                                System.out.println("----- ECCO IL MEZZO CREATO------");
                                System.out.println(newMezzo);
                                System.out.println("----- VUOI SALVARLO?------");
                                System.out.println("-----Digita Y per si N per no------");
                                String sceltasav = scanner.nextLine().trim();

                                if (sceltasav.equalsIgnoreCase("Y")) {
                                    mezzoDAO.save(newMezzo);
                                    System.out.println("Mezzo salvato con successo");
                                } else {
                                    System.out.println("Salvataggio annullato");
                                }
                            } else {
                                System.out.println("nessun mezzo trovato");
                            }
                        }
                        case 2 -> {
                            while (true) {
                                System.out.println("----- ASSEGNAMO UN MEZZO ALLA MANUTENZIONE ------");
                                System.out.println("----LISTA MEZZI FERMI-----");
                                mezzoDAO.listaMezzoPerStato(StatoMezzo.FERMO);
                                System.out.println("---- INSERISCI L'ID DEL MEZZO-----");
                                try {
                                    Long idScelto = Long.valueOf(scanner.nextLine().trim());
                                    Mezzo mezzofromdb = mezzoDAO.getById(idScelto);
                                    if (mezzofromdb == null) {
                                        System.out.println("---NESSUN MEZZO TROVATO----");
                                    }
                                    System.out.println("-----CONFERMARE?-----");
                                    System.out.println("-----Digita Y per si N per no------");
                                    String conferma = scanner.nextLine().trim();
                                    if (conferma.equalsIgnoreCase("Y")) {
                                        assert mezzofromdb != null;
                                        if (mezzofromdb.getStato() == StatoMezzo.MANUTENZIONE) {
                                            System.out.println("----- IL MEZZO " + mezzofromdb + " E' GIA IN MANUTENZIONE-----");
                                        }
                                        System.out.println("------SELEZIONARE IL TIPO DI MANUTENZIONE-----");
                                        System.out.println("------ORDINARIA/STRAORDINARIA------");
                                        String tipoManutenzione = scanner.nextLine().trim();
                                        if (tipoManutenzione.equalsIgnoreCase(TipoManutenzione.ORDINARIA.toString())) {
                                            Manutenzione man1 = new Manutenzione(LocalDate.now(), null,
                                                    TipoManutenzione.ORDINARIA, mezzofromdb);
                                            manutenzioneDao.save(man1);
                                        } else if (tipoManutenzione.equalsIgnoreCase(TipoManutenzione.STRAORDINARIA.toString())) {
                                            Manutenzione man1 = new Manutenzione(LocalDate.now(), null,
                                                    TipoManutenzione.STRAORDINARIA, mezzofromdb);
                                            manutenzioneDao.save(man1);
                                        }
                                        mezzofromdb.setStato(StatoMezzo.MANUTENZIONE);
                                        mezzoDAO.update(mezzofromdb);

                                        System.out.println("-----" + mezzofromdb + "E' STATO MANDATO IN MANUTENZIONE-----");
                                        break;
                                    } else {
                                        System.out.println("------ OPERAZIONE ANNULLATA------");
                                        break;
                                    }
                                } catch (Exception e) {
                                    System.out.println("mezzo non trovato");
                                }
                            }
                        }
                        case 3 -> {
                            System.out.println("----- VISUALIZZIAMO IL NUMERO DI VOLTE CHE " +
                                    "UN MEZZO HA FATTO UNA TRATTA ------");
                            mezzoDAO.listaMezzi();
                            System.out.println("-----INSERISCI L'ID DEL MEZZO-----");
                            try {
                                Long idMezzo = Long.valueOf(scanner.nextLine());
                                Mezzo mezzoFromDB = mezzoDAO.getById(idMezzo);
                                if (mezzoFromDB != null) {
                                    System.out.println("-----CONFERMARE?-----");
                                    System.out.println("-----Digita Y per si N per no------");
                                    String conferma = scanner.nextLine().trim();
                                    if (conferma.equalsIgnoreCase("Y")) {
                                        System.out.println("-----MEZZO SELEZIONATO-----");
                                        trattaDAO.listaTratte();
                                        try {
                                            System.out.println("----- INSERISCI L'ID DELLA TRATTA------");
                                            Long idTratta = Long.valueOf(scanner.nextLine());
                                            Tratta trattaFromDB = trattaDAO.getById(idTratta);
                                            if (trattaFromDB != null) {
                                                percorrenzaDAO.listaPercorrenzePerMezzo(idMezzo, idTratta);
                                            }
                                        } catch (Exception e) {
                                            System.out.println("inserisci un valore valido");
                                        }
                                    }
                                } else {
                                    System.out.println("mezzo non trovato");
                                }
                            } catch (Exception e) {
                                System.out.println("inserisci un valore valido");
                            }
                        }
                        case 4 -> {

                            System.out.println("----- ASSEGNAMO UN MEZZO AD UNA TRATTA ------");
                            System.out.println("----LISTA MEZZI FERMI-----");
                            mezzoDAO.listaMezzoPerStato(StatoMezzo.FERMO);
                            while (true) {
                                System.out.println("---- INSERISCI L'ID DEL MEZZO-----");
                                try {
                                    Long idScelto = Long.valueOf(scanner.nextLine());
                                    Mezzo mezzofromdb = mezzoDAO.getById(idScelto);
                                    if (mezzofromdb == null) {
                                        System.out.println("---NESSUN MEZZO TROVATO----");
                                    }
                                    System.out.println("-----CONFERMARE?-----");
                                    System.out.println("-----Digita Y per si N per no------");
                                    String conferma = scanner.nextLine().trim();
                                    if (conferma.equalsIgnoreCase("Y")) {
                                        System.out.println("------- SELEZIONA UNA TRATTA-------");
                                        System.out.println("------- LISTA TRATTA DISPONIBILE-----");
                                        trattaDAO.listaTratte();
                                        while (true) {
                                            System.out.println("------ INSERISCI L' ID DELLA TRATTA DA SELEZIONARE-------");
                                            try {
                                                Long IdTratta = Long.valueOf(scanner.nextLine());
                                                Tratta trattaFromDB = trattaDAO.getById(IdTratta);
                                                if (trattaFromDB == null) {
                                                    System.out.println("----NESSUNA TRATTA TROVATA----");
                                                }
                                                System.out.println("-----CONFERMARE?-----");
                                                System.out.println("-----Digita Y per si N per no------");
                                                String confermaTratta = scanner.nextLine().trim();
                                                if (confermaTratta.equalsIgnoreCase("Y")) {
                                                    System.out.println("------INSERIRE LA DATA IN CUI IL MEZZO COMPIRA LA TRATTA------");
                                                    LocalDate dataPercorrenza = null;
                                                    while (dataPercorrenza == null) {
                                                        System.out.println("Inserisci data (yyyy-MM-dd): ");
                                                        System.out.println("Data oggi: " + LocalDate.now());

                                                        String inputData = scanner.nextLine();
                                                        System.out.println(mezzofromdb);
                                                        System.out.println(trattaFromDB);
                                                        System.out.println(inputData);
                                                        if (inputData == null || inputData.isBlank()) {
                                                            System.out.println("inserisci un valore valido");
                                                            continue;
                                                        }
                                                        try {
                                                            LocalDate parsed = LocalDate.parse(inputData);

                                                            if (parsed.isBefore(LocalDate.now())) {
                                                                System.out.println("Non puoi usare una data nel passato");
                                                            } else {
                                                                dataPercorrenza = parsed;
                                                            }

                                                        } catch (DateTimeParseException e) {
                                                            System.out.println("Formato non valido. Usa yyyy-MM-dd");
                                                        }
                                                    }
                                                    try {
                                                        System.out.println("------ ASSEGNAZIONE MEZZO/TRATTA IN CORSO-----");
                                                        Percorrenza newPercorrenza = new Percorrenza(dataPercorrenza, mezzofromdb, trattaFromDB);
                                                        System.out.println("------CONFERMARE?------");
                                                        System.out.println("-----Digita Y per si N per no------");
                                                        String confermaSalvataggio = scanner.nextLine().trim();
                                                        if (confermaSalvataggio.equalsIgnoreCase("Y")) {
                                                            mezzofromdb.setStato(StatoMezzo.SERVIZIO);
                                                            mezzoDAO.update(mezzofromdb);
                                                            percorrenzaDAO.save(newPercorrenza);
                                                            break;
                                                        }
                                                    } catch (Exception e) {
                                                        System.out.println("ERRORE " + e.getMessage());
                                                    }
                                                }
                                            } catch (Exception e) {
                                                System.out.println("----- INSERISCI UN ID VALIDO------");
                                            }
                                        }


                                    } else {
                                        break;
                                    }


                                } catch (Exception e) {
                                    System.out.println("mezzo non trovato");
                                }
                            }

                        }
                        default -> System.out.println("------- INSERISCI UN VALORE VALIDO------");
                    }
                } catch (Exception e) {
                    System.out.println("------- INSERISCI UN VALORE VALIDO------");
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static Mezzo menuCreazioneMezzo() {
        while (true) {
            System.out.println("------ SELEZIONA IL TIPO DI MEZZO DA AGGIUNGERE--------");
            System.out.println("------- AUTOBUS/TRAM------");
            Mezzo newMezzo = null;
            try {
                String scelta = scanner.nextLine().trim();
                if (scelta.equalsIgnoreCase(TipoMezzo.AUTOBUS.toString())) {
                    return newMezzo = new Mezzo(StatoMezzo.FERMO, TipoMezzo.AUTOBUS);
                } else if (scelta.equalsIgnoreCase(TipoMezzo.TRAM.toString())) {
                    return newMezzo = new Mezzo(StatoMezzo.FERMO, TipoMezzo.TRAM);
                } else {
                    System.out.println("inserire un valore valido");
                }
            } catch (Exception e) {
                System.out.println("inserire un valore valido");
            }
            if (newMezzo != null) {
                return newMezzo;
            }
            System.out.println("non è stato possibile creare un nuovo mezzo");
        }
    }

    public static void menuTitoloDiViaggio() {

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            boolean flag3 = true;
            while (flag3) {
                System.out.println("----- GESTIONE TITOLI DI VIAGGIO -----");
                System.out.println("----- 0 per uscire e tornare indietro -------");
                System.out.println("----- 1 Visualizzare uno storico dei biglietti " +
                        "e abbonamenti emessi in un determinato periodo di tempo ------");
                System.out.println("----- 2 per Visualizzare i biglietti vidimati in un mezzo ------");
                System.out.println("----- 3 Controllare la validità di un biglietto------");

                try {
                    int scelta = Integer.parseInt(scanner.nextLine().trim());

                    switch (scelta) {
                        case 0 -> {
                            System.out.println("---- RITORNO AL MENU PRINCIPALE ------");
                            flag3 = false;
                        }
                        case 1 -> {
                            System.out.println("----- STORICO EMISSIONI PER PERIODO -----");
                            try {
                                System.out.println("Inserisci la data di INIZIO periodo (YYYY-MM-DD):");
                                String inputInizio = scanner.nextLine().trim();
                                LocalDate dataInizio = LocalDate.parse(inputInizio, formato);

                                System.out.println("Inserisci la data di FINE periodo (YYYY-MM-DD):");
                                String inputFine = scanner.nextLine().trim();
                                LocalDate dataFine = LocalDate.parse(inputFine, formato);

                                if (dataFine.isBefore(dataInizio)) {
                                    System.out.println("La data di fine non può essere precedente alla data di inizio!");
                                } else {
                                    titoloDiViaggioDAO.stampaNumListTDVPerTempo(dataInizio, dataFine);
                                }
                            } catch (Exception e) {
                                System.out.println("Formato data non valido! Usa YYYY-MM-DD (es. 2026-06-24).");
                            }
                        }
                        case 2 -> {
                            System.out.println();
                        }
                        case 3 -> {
                            System.out.println();
                        }
                        default -> System.out.println("------- INSERISCI UN VALORE VALIDO------");
                    }
                } catch (Exception e) {
                    System.out.println("------- INSERISCI UN VALORE VALIDO------");
                }
            }
        } catch (Exception e) {
            System.out.println("ERRORE" + e.getMessage());
        }
    }

    public static void menuUser() {
        System.out.println("----- 0 per uscire -------");
        System.out.println("----- 1 per Comprare biglietto ------");
        System.out.println("----- 2 per Comprare la tessera ------");
        System.out.println("----- 3 per Comprare un nuovo abbonamento------");
        try {
            int scelta = Integer.parseInt(scanner.nextLine());
            while (true) {
                switch (scelta) {
                    case 0 -> {
                        System.out.println("-------- CHIUSURA DEL PROGRAMMA------");
                        break;
                    }
                    case 1 -> {
                        System.out.println("-----COMPRA UN BIGLIETTO-----");
                        puntoDao.listaPuntoDiEmissione();
                        System.out.println("------INSERISCI ID DI UN PUNTO DI EMISSIONI-------");
                        Long idPunto = Long.valueOf(scanner.nextLine());
                        PuntoDiEmissione punto = puntoDao.getById(idPunto);
                        if (punto instanceof Distributore distributore) {
                            if (distributore.getStato() == StatoDistributore.NON_DISPONIBILE) {
                                System.out.println("Il distributore non è disponibile: " + distributore);
                                return; // ← stop here
                            }
                            System.out.println("Acquisto in corso presso il distributore #" + punto.getId() + "...");
                        }
                        Utente utente = utenteDAO.getUtenteByEmail(email);
                        Biglietto newBiglietto = new Biglietto(LocalDate.now(), punto, 1.50, null, null);
                        bigliettoDAO.compraBiglietto(utente, newBiglietto);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ;
        entityManager.close();
        scanner.close();
    }

}

