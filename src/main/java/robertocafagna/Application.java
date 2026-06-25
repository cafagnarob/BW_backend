package robertocafagna;

import Enum.StatoMezzo;
import Enum.TipoMezzo;
import Enum.TipoUtente;
import dao.*;
import entities.*;
import exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        PercorrenzaDAO percorrenzaDAO = new PercorrenzaDAO(entityManager);
        ManutenzioneDAO manutenzioneDao = new ManutenzioneDAO(entityManager);
        BigliettoDAO bigliettoDAO = new BigliettoDAO(entityManager);

        // Ripopolamento delle tabelle
        utenteDAO.popolaSeVuoto();
        puntoDao.popolaSeVuoto();
        mezzoDAO.popolaSeVuoto();
        trattaDAO.popolaSeVuoto();
        manutenzioneDao.popolaSeVuoto();
        percorrenzaDAO.popolaSeVuoto();
        tesseraDAO.popolaSeVuoto();
        abbonamentoDAO.popolaSeVuoto();
        bigliettoDAO.popolaSeVuoto();

        // INIZIO PROGRAMMA
        System.out.println("------ INIZIO PROGRAMMA -----");
        System.out.println("------ BENVENUTO -------");
        System.out.println("------ INSERISCI LA TUA EMAIL ------");
        System.out.println("ADMIN: admin@system.com ");
        System.out.println("USER SEMPLICE: mario.rossi@mail.com ");

        String email;
        Utente fromDB = null;
        while (fromDB == null) {
            email = scanner.nextLine().trim();
            if (email.isEmpty() || !email.contains("@") || !email.contains(".")) {
                System.out.println("Formato email non valido, riprova.");
                continue;
            }
            try {
                fromDB = utenteDAO.getUtenteByEmail(email);
            } catch (NoResultException e) {
                System.out.println("Email non trovata, riprova.");
            }
        }

        System.out.println("------ BENVENUTO " + fromDB.getNome() + " " + fromDB.getCognome() + " -------");

        if (fromDB.getTipo() == TipoUtente.ADMIN) {
            menuAdmin(entityManager);
        } else {
            menuUser(fromDB, bigliettoDAO, puntoDao, tesseraDAO, abbonamentoDAO);
        }

        // Chiusura risorse
        entityManager.close();
        entityManagerFactory.close();

        System.out.println("=== FINE PROGRAMMA ===");
    }


    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // ------------------------------ MENU ADMIN --------------------------------------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public static void menuAdmin(EntityManager entityManager) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("----- 0 per uscire -------");
        System.out.println("----- 1 per la GESTIONE DEI MEZZI ------");
        System.out.println("----- 2 per la GESTIONE DEI BIGLIETTI ED ABBONAMENTI ------");

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
                        System.out.println("----- GESTIONE MEZZI ------");
                        menuMezzi(entityManager);
                    }
                    case 2 -> {
                        System.out.println("----- GESTIONE TITOLI DI VIAGGIO ------");
                        menuTitoloDiViaggio(entityManager);
                    }
                    default -> System.out.println("------- INSERISCI UN VALORE VALIDO ------");
                }
            } catch (Exception e) {
                System.out.println("------- INSERISCI UN VALORE VALIDO ------");
            }
        }
    }


    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // ------------------------------ MENU MEZZI --------------------------------------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public static void menuMezzi(EntityManager entityManager) {
        MezzoDAO mezzoDAO = new MezzoDAO(entityManager);
        TrattaDAO trattaDAO = new TrattaDAO(entityManager);
        PercorrenzaDAO percorrenzaDAO = new PercorrenzaDAO(entityManager);
        Scanner scanner = new Scanner(System.in);

        boolean flag2 = true;
        while (flag2) {
            System.out.println("----- 0 per uscire -------");
            System.out.println("----- 1 per Aggiungere un nuovo mezzo ------");
            System.out.println("----- 2 per Mandare un mezzo in manutenzione ------");
            System.out.println("----- 3 per Visualizzare il numero di volte in cui un mezzo ha fatto una specifica tratta ------");
            System.out.println("----- 4 per Assegnare un mezzo ad una tratta -------");

            try {
                int scelta = Integer.parseInt(scanner.nextLine().trim());

                switch (scelta) {
                    case 0 -> {
                        System.out.println("---- RITORNO AL MENU PRECEDENTE ------");
                        flag2 = false;
                    }
                    case 1 -> {
                        System.out.println("----- CREAZIONE NUOVO MEZZO ------");
                        Mezzo newMezzo = menuCreazioneMezzo();
                        if (newMezzo != null) {
                            System.out.println("----- ECCO IL MEZZO CREATO ------");
                            System.out.println(newMezzo);
                            System.out.println("----- VUOI SALVARLO? ------");
                            System.out.println("----- Digita Y per si, N per no ------");
                            String sceltaSav = scanner.nextLine().trim();
                            if (sceltaSav.equalsIgnoreCase("Y")) {
                                mezzoDAO.save(newMezzo);
                                System.out.println("Mezzo salvato con successo.");
                            } else {
                                System.out.println("Salvataggio annullato.");
                            }
                        } else {
                            System.out.println("Nessun mezzo creato.");
                        }
                    }
                    case 2 -> {
                        while (true) {
                            System.out.println("----- ASSEGNAMO UN MEZZO ALLA MANUTENZIONE ------");
                            System.out.println("----- LISTA MEZZI FERMI -----");
                            mezzoDAO.listaMezzoPerStato(StatoMezzo.FERMO);
                            System.out.println("---- INSERISCI L'ID DEL MEZZO -----");
                            try {
                                Long idScelto = Long.valueOf(scanner.nextLine().trim());
                                Mezzo mezzofromdb = mezzoDAO.getById(idScelto);
                                if (mezzofromdb == null) {
                                    System.out.println("--- NESSUN MEZZO TROVATO ----");
                                    continue;
                                }
                                System.out.println("----- CONFERMARE? -----");
                                System.out.println("----- Digita Y per si, N per no ------");
                                String conferma = scanner.nextLine().trim();
                                if (conferma.equalsIgnoreCase("Y")) {
                                    if (mezzofromdb.getStato() == StatoMezzo.MANUTENZIONE) {
                                        System.out.println("----- IL MEZZO " + mezzofromdb + " E' GIA' IN MANUTENZIONE -----");
                                    } else {
                                        mezzofromdb.setStato(StatoMezzo.MANUTENZIONE);
                                        mezzoDAO.update(mezzofromdb);
                                        System.out.println("----- " + mezzofromdb + " E' STATO MANDATO IN MANUTENZIONE -----");
                                    }
                                    break;
                                } else {
                                    System.out.println("------ OPERAZIONE ANNULLATA ------");
                                    break;
                                }
                            } catch (Exception e) {
                                System.out.println("Mezzo non trovato, inserisci un ID valido.");
                            }
                        }
                    }
                    case 3 -> {
                        System.out.println("----- VISUALIZZIAMO IL NUMERO DI VOLTE CHE UN MEZZO HA FATTO UNA TRATTA ------");
                        mezzoDAO.listaMezzi();
                        System.out.println("----- INSERISCI L'ID DEL MEZZO -----");
                        try {
                            Long idMezzo = Long.valueOf(scanner.nextLine().trim());
                            Mezzo mezzoFromDB = mezzoDAO.getById(idMezzo);
                            if (mezzoFromDB != null) {
                                System.out.println("----- CONFERMARE? -----");
                                System.out.println("----- Digita Y per si, N per no ------");
                                String conferma = scanner.nextLine().trim();
                                if (conferma.equalsIgnoreCase("Y")) {
                                    System.out.println("----- MEZZO SELEZIONATO -----");
                                    trattaDAO.listaTratte();
                                    System.out.println("----- INSERISCI L'ID DELLA TRATTA ------");
                                    try {
                                        Long idTratta = Long.valueOf(scanner.nextLine().trim());
                                        Tratta trattaFromDB = trattaDAO.getById(idTratta);
                                        if (trattaFromDB != null) {
                                            percorrenzaDAO.listaPercorrenzePerMezzo(idMezzo, idTratta);
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Inserisci un valore valido.");
                                    }
                                }
                            } else {
                                System.out.println("Mezzo non trovato.");
                            }
                        } catch (Exception e) {
                            System.out.println("Inserisci un valore valido.");
                        }
                    }
                    case 4 -> {
                        System.out.println("----- ASSEGNAMO UN MEZZO AD UNA TRATTA ------");
                        System.out.println("----- LISTA MEZZI FERMI -----");
                        mezzoDAO.listaMezzoPerStato(StatoMezzo.FERMO);
                        while (true) {
                            System.out.println("---- INSERISCI L'ID DEL MEZZO -----");
                            try {
                                Long idScelto = Long.valueOf(scanner.nextLine().trim());
                                Mezzo mezzofromdb = mezzoDAO.getById(idScelto);
                                if (mezzofromdb == null) {
                                    System.out.println("--- NESSUN MEZZO TROVATO ----");
                                    continue;
                                }
                                System.out.println("----- CONFERMARE? -----");
                                System.out.println("----- Digita Y per si, N per no ------");
                                String conferma = scanner.nextLine().trim();
                                if (conferma.equalsIgnoreCase("Y")) {
                                    System.out.println("------- LISTA TRATTE DISPONIBILI -----");
                                    trattaDAO.listaTratte();
                                    System.out.println("------ INSERISCI L'ID DELLA TRATTA -------");
                                    while (true) {
                                        try {
                                            Long idTratta = Long.valueOf(scanner.nextLine().trim());
                                            Tratta trattaFromDB = trattaDAO.getById(idTratta);
                                            if (trattaFromDB == null) {
                                                System.out.println("---- NESSUNA TRATTA TROVATA ----");
                                                continue;
                                            }
                                            System.out.println("----- CONFERMARE? -----");
                                            System.out.println("----- Digita Y per si, N per no ------");
                                            String confermaTratta = scanner.nextLine().trim();
                                            if (confermaTratta.equalsIgnoreCase("Y")) {
                                                DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                                System.out.println("------ DATA DI OGGI: " + LocalDate.now() + " ----");
                                                System.out.println("------ INSERISCI LA DATA (YYYY-MM-DD) ------");
                                                String inputData = scanner.nextLine().trim();
                                                try {
                                                    LocalDate dataPercorrenza = LocalDate.parse(inputData, formato);
                                                    if (dataPercorrenza.isBefore(LocalDate.now())) {
                                                        System.out.println("Non puoi associare una tratta ad un mezzo nel passato.");
                                                    } else {
                                                        Percorrenza newPercorrenza = new Percorrenza(dataPercorrenza, mezzofromdb, trattaFromDB);
                                                        System.out.println("------ CONFERMARE IL SALVATAGGIO? (Y/N) ------");
                                                        String confermaSalvataggio = scanner.nextLine().trim();
                                                        if (confermaSalvataggio.equalsIgnoreCase("Y")) {
                                                            mezzofromdb.setStato(StatoMezzo.SERVIZIO);
                                                            mezzoDAO.update(mezzofromdb);
                                                            percorrenzaDAO.save(newPercorrenza);
                                                            System.out.println("Percorrenza salvata con successo.");
                                                        }
                                                        break;
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println("Data non valida, inserire una data valida.");
                                                }
                                            } else {
                                                break;
                                            }
                                        } catch (Exception e) {
                                            System.out.println("----- INSERISCI UN ID VALIDO ------");
                                        }
                                    }
                                    break;
                                } else {
                                    break;
                                }
                            } catch (Exception e) {
                                System.out.println("Mezzo non trovato, inserisci un ID valido.");
                            }
                        }
                    }
                    default -> System.out.println("------- INSERISCI UN VALORE VALIDO ------");
                }
            } catch (Exception e) {
                System.out.println("------- INSERISCI UN VALORE VALIDO ------");
            }
        }
    }


    public static Mezzo menuCreazioneMezzo() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("------ SELEZIONA IL TIPO DI MEZZO DA AGGIUNGERE --------");
            System.out.println("------- AUTOBUS / TRAM ------");
            try {
                String scelta = scanner.nextLine().trim();
                if (scelta.equalsIgnoreCase(TipoMezzo.AUTOBUS.toString())) {
                    return new Mezzo(StatoMezzo.FERMO, TipoMezzo.AUTOBUS);
                } else if (scelta.equalsIgnoreCase(TipoMezzo.TRAM.toString())) {
                    return new Mezzo(StatoMezzo.FERMO, TipoMezzo.TRAM);
                } else {
                    System.out.println("Inserire un valore valido (AUTOBUS o TRAM).");
                }
            } catch (Exception e) {
                System.out.println("Inserire un valore valido.");
            }
        }
    }


    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // ------------------------------ MENU TITOLO DI VIAGGIO --------------------------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public static void menuTitoloDiViaggio(EntityManager entityManager) {
        TitoloDiViaggioDAO titoloDiViaggioDAO = new TitoloDiViaggioDAO(entityManager);
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        boolean flag3 = true;
        while (flag3) {
            System.out.println("----- GESTIONE TITOLI DI VIAGGIO -----");
            System.out.println("----- 0 per uscire e tornare indietro -------");
            System.out.println("----- 1 Visualizzare uno storico dei biglietti e abbonamenti emessi in un determinato periodo ------");
            System.out.println("----- 2 per Visualizzare i biglietti vidimati in un mezzo ------");
            System.out.println("----- 3 Controllare la validità di un biglietto ------");

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
                            LocalDate dataInizio = LocalDate.parse(scanner.nextLine().trim(), formato);

                            System.out.println("Inserisci la data di FINE periodo (YYYY-MM-DD):");
                            LocalDate dataFine = LocalDate.parse(scanner.nextLine().trim(), formato);

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
                        System.out.println("--- funzionalità da implementare ---");
                    }
                    case 3 -> {
                        System.out.println("--- funzionalità da implementare ---");
                    }
                    default -> System.out.println("------- INSERISCI UN VALORE VALIDO ------");
                }
            } catch (Exception e) {
                System.out.println("------- INSERISCI UN VALORE VALIDO ------");
            }
        }
    }


    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // ------------------------------ MENU USER ---------------------------------------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public static void menuUser(Utente utente, BigliettoDAO bigliettoDAO, PuntoDiEmissioneDAO puntoDiEmissioneDAO, TesseraDAO tesseraDAO, AbbonamentoDAO abbonamentoDAO) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("----- 0 per uscire -------");
        System.out.println("----- 1 per Comprare biglietto ------");
        System.out.println("----- 2 per Comprare la tessera ------");
        System.out.println("----- 3 per Comprare un nuovo abbonamento ------");

        boolean flagUserMenu = true;
        while (flagUserMenu) {
            try {
                int scelta = Integer.parseInt(scanner.nextLine().trim());

                switch (scelta) {
                    case 0 -> {
                        System.out.println("---- CHIUSURA DEL PROGRAMMA ------");
                        flagUserMenu = false;
                    }
                    case 1 -> utenteCompraBiglietto(utente, bigliettoDAO, puntoDiEmissioneDAO);
                    case 2 -> utenteCompraTessera(utente, tesseraDAO, puntoDiEmissioneDAO);
                    case 3 -> System.out.println("--- funzionalità abbonamento da implementare ---");
                    default -> System.out.println("------- INSERISCI UN VALORE VALIDO ------");
                }
            } catch (Exception e) {
                System.out.println("------- INSERISCI UN VALORE VALIDO ------");
            }
        }
    }


    public static void utenteCompraBiglietto(Utente utente, BigliettoDAO bigliettoDAO, PuntoDiEmissioneDAO puntoDiEmissioneDAO) {
        PuntoDiEmissione puntoDiEmissioneScelto = null;
        Scanner scanner = new Scanner(System.in);

        while (puntoDiEmissioneScelto == null) {
            System.out.println("---- LISTA DEI PUNTI DI EMISSIONE DISPONIBILI ------");
            puntoDiEmissioneDAO.stampaPuntiDiEmissioneDisponibili();
            System.out.println("---- DIGITA L'ID DEL PUNTO DI EMISSIONE ------");
            try {
                long inputId = Long.parseLong(scanner.nextLine().trim());
                puntoDiEmissioneScelto = puntoDiEmissioneDAO.getById(inputId);
            } catch (NotFoundException e) {
                System.out.println("Punto di emissione non trovato, riprova.");
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un ID numerico valido.");
            }
        }

        Biglietto nuovoBiglietto = new Biglietto(LocalDate.now(), puntoDiEmissioneScelto, puntoDiEmissioneScelto.getPrezzoBiglietto());
        bigliettoDAO.save(nuovoBiglietto);
        bigliettoDAO.compraBiglietto(utente, nuovoBiglietto);

        System.out.println("---- CONFERMA DI ACQUISTO ------");
        System.out.println("---- L'UTENTE " + utente.getId() + " HA ACQUISTATO IL BIGLIETTO #" + nuovoBiglietto.getId() + " ------");
    }


    public static void utenteCompraTessera(Utente utente, TesseraDAO tesseraDAO, PuntoDiEmissioneDAO puntoDiEmissioneDAO) {
        PuntoDiEmissione puntoDiEmissioneScelto = null;
        Scanner scanner = new Scanner(System.in);

        while (puntoDiEmissioneScelto == null) {
            System.out.println("---- LISTA DEI PUNTI DI EMISSIONE DISPONIBILI ------");
            puntoDiEmissioneDAO.stampaPuntiDiEmissioneDisponibili();
            System.out.println("---- DIGITA L'ID DEL PUNTO DI EMISSIONE ------");
            try {
                long inputId = Long.parseLong(scanner.nextLine().trim());
                puntoDiEmissioneScelto = puntoDiEmissioneDAO.getById(inputId);
            } catch (NotFoundException e) {
                System.out.println("Punto di emissione non trovato, riprova.");
            } catch (NumberFormatException e) {
                System.out.println("Inserisci un ID numerico valido.");
            }
        }

        System.out.println("---- ACQUISTO DI TESSERA NOMINATIVA ------");
        System.out.println("---- PUNTO DI EMISSIONE: " + puntoDiEmissioneScelto.getId() + " - " + puntoDiEmissioneScelto.getIndirizzo());
        System.out.println("---- PREZZO: 49.99€ ------");
        System.out.println("---- PROCEDERE? (Y/N) ------");
        String conferma = scanner.nextLine().trim();

        if (conferma.equalsIgnoreCase("Y")) {
            tesseraDAO.compraTessera(puntoDiEmissioneScelto, utente);
        } else {
            System.out.println("----- ACQUISTO ANNULLATO ------");
        }
    }
}
