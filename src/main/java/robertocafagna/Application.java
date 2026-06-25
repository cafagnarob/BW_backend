package robertocafagna;

import Enum.*;
import dao.*;
import entities.*;
import exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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


        // INIZIO PROGRAMMA

        System.out.println("------ INIZIO PROGRAMMA -----");
        System.out.println("------ BENVENUTO-------");
        System.out.println("------ INSERISCI LA TUA EMAIL------");
        System.out.println("ADMIN: admin@system.com ");
        System.out.println("USER SEMPLICE: mario.rossi@mail.com ");

        Utente fromDB = null;

        while (fromDB == null) {
            email = scanner.nextLine().trim();
            if (!email.contains("@") || !email.contains(".")) {
                System.out.println("Email non valida");
                continue;
            }
            try {
                fromDB = utenteDAO.getUtenteByEmail(email);
            } catch (Exception e) {
                System.out.println("Utente non Trovato");
            }
        }


        System.out.println("------ BENVENUTO " + fromDB.getNome() + " " + fromDB.getCognome() + "-------");

        if (fromDB.getTipo() == TipoUtente.ADMIN) {
            menuAdmin();
        } else {
            menuUser();
        }

        System.out.println("=== FINE TEST COMPLETO ===");
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
                        " ha fatto una specifica tratta");
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
                                while (true) {
                                    try {
                                        System.out.println("----- VUOI SALVARLO?------");
                                        System.out.println("-----Digita Y per si N per no------");
                                        String sceltasav = scanner.nextLine().trim();
                                        if (sceltasav.equalsIgnoreCase("Y")) {
                                            mezzoDAO.save(newMezzo);
                                            System.out.println("Mezzo salvato con successo");
                                            break;
                                        } else if (sceltasav.equalsIgnoreCase("N")) {
                                            System.out.println("Salvataggio annullato");
                                            break;
                                        } else throw new RuntimeException();
                                    } catch (RuntimeException e) {
                                        System.out.println("------ INSERISCI UN VALORE VALIDO-----");
                                    }
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
                                    while (true) {
                                        try {
                                            System.out.println("-----CONFERMARE?-----");
                                            System.out.println("-----Digita Y per si N per no------");
                                            String conferma = scanner.nextLine().trim();
                                            if (conferma.equalsIgnoreCase("Y")) {
                                                assert mezzofromdb != null;
                                                if (mezzofromdb.getStato() == StatoMezzo.MANUTENZIONE) {
                                                    System.out.println("----- IL MEZZO " + mezzofromdb + " E' GIA IN MANUTENZIONE-----");
                                                }
                                                while (true) {

                                                    System.out.println("------SELEZIONARE IL TIPO DI MANUTENZIONE-----");
                                                    System.out.println("------ORDINARIA/STRAORDINARIA------");
                                                    String tipoManutenzione = scanner.nextLine().trim();
                                                    if (tipoManutenzione.equalsIgnoreCase(TipoManutenzione.ORDINARIA.toString())) {
                                                        Manutenzione man1 = new Manutenzione(LocalDate.now(), null,
                                                                TipoManutenzione.ORDINARIA, mezzofromdb);
                                                        manutenzioneDao.save(man1);
                                                        mezzofromdb.setStato(StatoMezzo.MANUTENZIONE);
                                                        mezzoDAO.update(mezzofromdb);

                                                        System.out.println("-----" + mezzofromdb + "E' STATO MANDATO IN MANUTENZIONE-----");
                                                        break;
                                                    } else if (tipoManutenzione.equalsIgnoreCase(TipoManutenzione.STRAORDINARIA.toString())) {
                                                        Manutenzione man1 = new Manutenzione(LocalDate.now(), null,
                                                                TipoManutenzione.STRAORDINARIA, mezzofromdb);
                                                        manutenzioneDao.save(man1);
                                                        mezzofromdb.setStato(StatoMezzo.MANUTENZIONE);
                                                        mezzoDAO.update(mezzofromdb);

                                                        System.out.println("-----" + mezzofromdb + "E' STATO MANDATO IN MANUTENZIONE-----");
                                                        break;
                                                    } else {
                                                        System.out.println("------ INSERIRE UN VALORE VALIDO-----");
                                                    }
                                                }
                                                break;
                                            } else if (conferma.equalsIgnoreCase("N")) {
                                                System.out.println("------ OPERAZIONE ANNULLATA------");
                                                break;
                                            } else throw new RuntimeException();
                                        } catch (RuntimeException e) {
                                            System.out.println("------ INSERISCI UN VALORE VALIDO-----");
                                        }
                                    }
                                    break;
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

                            boolean flagMezzo = true;
                            while (flagMezzo) {
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
                                        boolean flagTratta = true;
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

                                                            flagMezzo=false;
                                                            flagTratta=false;
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
                            System.out.println("----- BIGLIETTI VIDIMATI SU UN MEZZO PER FASCIA ORARIA -----");
                            try {
                                mezzoDAO.listaMezzi();
                                System.out.println("Inserisci l'ID del mezzo: ");
                                long idMezzo = Long.parseLong(scanner.nextLine().trim());

                                LocalTime oraInizio = null;
                                while (oraInizio== null){
                                    System.out.println("Inserisci l'ora di INIZIO (es. 08:30): ");
                                    String oraInizioInput = scanner.nextLine().trim();
                                    try{
                                        oraInizio = LocalTime.parse(oraInizioInput);
                                    }catch (DateTimeParseException e){
                                        System.out.println("Errore: Formato orario non valido! Usa il formato HH:mm");
                                    }
                                }

                                LocalTime oraFine = null;
                                while (oraFine == null) {
                                    System.out.println("Inserisci l'ora di FINE (es. 18:00):");
                                    String oraFineInput = scanner.nextLine().trim();
                                    try {
                                        oraFine = LocalTime.parse(oraFineInput);

                                        if (oraFine.isBefore(oraInizio)) {
                                            System.out.println("Errore: L'orario di fine non può essere precedente a quello di inizio!");
                                            oraFine = null;
                                        }
                                    } catch (java.time.format.DateTimeParseException e) {
                                        System.out.println("Errore: Formato orario non valido! Usa il formato HH:mm");
                                    }
                                }

                                LocalDate oggi = LocalDate.now();

                                //  LocalTime in LocalDateTime uniti alla data di oggi
                                LocalDateTime inizioCompleto = oraInizio.atDate(oggi);
                                LocalDateTime fineCompleto = oraFine.atDate(oggi);

                                titoloDiViaggioDAO.stampaListNumTDVVidimatiPerTempo(inizioCompleto, fineCompleto, idMezzo);

                            } catch (NumberFormatException e) {
                                System.out.println("Errore: L'ID del mezzo deve essere un numero intero!");
                            } catch (Exception e) {
                                System.out.println("Errore durante il recupero dei dati: " + e.getMessage());
                            }
                        }

                        case 3 -> {
                            System.out.println("\n----- CONTROLLO VALIDITÀ BIGLIETTO SINGOLO -----");
                            try {
                                System.out.println("Inserisci l'ID del BIGLIETTO:");
                                long idBiglietto = Long.parseLong(scanner.nextLine().trim());

                                // prendo il titolo dal DB tramite il getById del dao
                                TitoloDiViaggio tdv = titoloDiViaggioDAO.getById(idBiglietto);

                                System.out.println("\n=== VERIFICA VALIDITA' BIGLIETTO ===");
                                if (tdv instanceof Biglietto) {
                                    Biglietto b = (Biglietto) tdv;

                                    // controllo prima se è null, cioe se il biglietto non è mai stato convalidato
                                    if (b.getOrarioVidimazione() == null) {
                                        System.out.println("ID Biglietto: " + idBiglietto);
                                        System.out.println("STATO: VALIDO (Pronto da vidimare)");
                                    } else {
                                        // se il biglietto è convalidato verifico la finestra dei 120 minuti di vidimazione
                                        LocalDateTime momentoVidimazione = b.getOrarioVidimazione();
                                        LocalDateTime fineValidita = momentoVidimazione.plusMinutes(120);

                                        System.out.println("ID Biglietto: " + idBiglietto);
                                        System.out.println("Vidimato sul mezzo ID: " + b.getMezzo().getId());
                                        System.out.println("Orario Vidimazione: " + momentoVidimazione);
                                        System.out.println("Valido fino a: " + fineValidita);

                                        // confronto con l'orario attuale
                                        if (LocalDateTime.now().isBefore(fineValidita)) {
                                            System.out.println("STATO: ANCORA VALIDO (In corso di viaggio)");
                                        } else {
                                            System.out.println("STATO: SCADUTO (Oltre i 120 minuti concessi)");
                                        }
                                    }
                                } else {
                                    System.out.println("L'ID inserito non appartiene a un biglietto singolo (è un abbonamento).");
                                }
                                System.out.println("====================================");

                            } catch (NotFoundException e) {
                                System.out.println("Errore: " + e.getMessage()); // gestisco l'errore se l'id del biglietto non esiste
                            } catch (NumberFormatException e) {
                                System.out.println("Errore: Inserisci un ID numerico valido!");
                            } catch (Exception e) {
                                System.out.println("Errore durante il controllo del biglietto: " + e.getMessage());
                            }
                        }

                        default -> System.out.println("------- NON HAI INSERITO UN VALORE------");
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
        try {
            while (true) {
                System.out.println("----- 0 per uscire -------");
                System.out.println("----- 1 per Comprare biglietto ------");
                System.out.println("----- 2 per Crea/Rinnova la tessera ------");
                System.out.println("----- 3 per Comprare un nuovo abbonamento ------");


                int scelta = Integer.parseInt(scanner.nextLine());
                switch (scelta) {
                    case 0 -> {
                        System.out.println("-------- CHIUSURA DEL PROGRAMMA------");
                        break;
                    }
                    case 1 -> {
                        System.out.println("-----COMPRA UN BIGLIETTO-----");
                        puntoDao.listaPuntoDiEmissione();

                        PuntoDiEmissione punto = null;

                        while (punto==null){
                            System.out.println("------INSERISCI ID DI UN PUNTO DI EMISSIONI-------");
                            try {
                                Long idPunto = Long.valueOf(scanner.nextLine());
                                punto = puntoDao.getById(idPunto);

                                if (punto == null){
                                    System.out.println("Errore: Nessun punto di emissione trovato con questo ID. Riprova.");
                                    continue;
                                }

                                if (punto instanceof Distributore distributore) {
                                    if (distributore.getStato() == StatoDistributore.NON_DISPONIBILE) {
                                        System.out.println("Il distributore non è disponibile: " + distributore);
                                        punto = null;
                                        continue;
                                    }
                                    System.out.println("Acquisto in corso presso il distributore #" + punto.getId() + "...");
                            }
                        } catch (NumberFormatException e){
                                System.out.println("Errore: L'ID deve essere un numero valido!");
                            }catch (Exception e){
                                System.out.println("Si è verificato un errore: " + e.getMessage());
                            }
                        }
                        try {
                            Utente utente = utenteDAO.getUtenteByEmail(email);
                            Biglietto newBiglietto = new Biglietto(LocalDate.now(), punto, 1.50, null, null);
                            bigliettoDAO.compraBiglietto(utente, newBiglietto);
                        }catch (Exception e){
                            System.out.println("Errore durante l'acquisto del biglietto: " + e.getMessage());
                        }
                    }

                    case 2 -> {
                        Utente utente = utenteDAO.getUtenteByEmail(email);
                        System.out.println("------RINNOVARE UNA TESSERA-----");
                        puntoDao.listaPuntoDiEmissione();
                        System.out.println("------INSERISCI L' ID DI PUNTO DI EMISSIONE------");
                        Long idPunto = Long.valueOf(scanner.nextLine());
                        PuntoDiEmissione punto = puntoDao.getById(idPunto);
                        if (punto instanceof Distributore distributore) {
                            if (distributore.getStato() == StatoDistributore.NON_DISPONIBILE) {
                                System.out.println("Il distributore non è disponibile: " + distributore);
                                break;
                            }

                            System.out.println("Procedura in corso presso il distributore #" + punto.getId() + "...");
                            Tessera tesseraEsistente = tesseraDAO.getTesseraByUtenteId(utente.getId());
                            if (tesseraEsistente != null) {
                                System.out.println("L'utente possiede già una tessera: " + tesseraEsistente);

                                // rinnovo
                                tesseraEsistente.setData_di_emissione(LocalDate.now());
                                tesseraEsistente.setDataDiScadenza(LocalDate.now().plusYears(1));
                                tesseraDAO.update(tesseraEsistente);

                                System.out.println("Tessera rinnovata.");
                            } else {
                                tesseraDAO.compraTessera(punto, utente);

                                System.out.println("Nuova tessera creata.");
                                break;
                            }
                        } else if (punto instanceof Rivenditore rivenditore) {
                            System.out.println("Procedura in corso presso il rivenditore #" + punto.getId() + "...");
                            Tessera tesseraEsistente = tesseraDAO.getTesseraByUtenteId(utente.getId());
                            if (tesseraEsistente != null) {
                                System.out.println("L'utente possiede già una tessera: " + tesseraEsistente);

                                // rinnovo
                                tesseraEsistente.setData_di_emissione(LocalDate.now());
                                tesseraDAO.update(tesseraEsistente);

                                System.out.println("Tessera rinnovata.");
                                System.out.println("----NUOVI DATI TESSERA: " + tesseraEsistente);
                            } else {
                                tesseraDAO.compraTessera(punto, utente);

                                System.out.println("Nuova tessera creata.");
                                System.out.println("----- DATI NUOVA TESSERRA------" + tesseraEsistente);
                                break;
                            }

                        } else {
                            System.out.println("----INSERISCI UN VALORE VALIDO------");
                        }

                    }
                    case 3 -> {
                        System.out.println("-----COMPRA ABBONAMENTO------");

                        try {
                            Utente utente = utenteDAO.getUtenteByEmail(email);
                            Tessera tessera = tesseraDAO.getTesseraByUtente(utente);

                            if (tessera == null) {
                                System.out.println("Errore: Non hai una tessera attiva associata al tuo profilo.");
                                break;
                            }

                            TipoAbbonamento tipo = null;
                            while (tipo==null){
                            System.out.println("Scegli tipo abbonamento (1 per SETTIMANALE, 2 per MENSILE): ");

                            try{
                                int tipoScelta = Integer.parseInt(scanner.nextLine());
                                if(tipoScelta==1){
                                    tipo = TipoAbbonamento.SETTIMANALE;
                                } else if (tipoScelta == 2){
                                    tipo = TipoAbbonamento.MENSILE;
                                } else {
                                    System.out.println("Opzione non valida! Inserisci 1 o 2.");
                                }
                            }catch (NumberFormatException e){
                                System.out.println("Inserisci un numero valido, 1 o 2");
                            }
                            }

                            puntoDao.listaPuntoDiEmissione();

                            PuntoDiEmissione punto = null;
                            while (punto==null){
                                System.out.println("------INSERISCI ID DI UN PUNTO DI EMISSIONI-------");
                                try {
                                    Long idPunto = Long.valueOf(scanner.nextLine());
                                    punto = puntoDao.getById(idPunto);

                                    if (punto==null){
                                        System.out.println("Errore: Nessun punto di emissione trovato con questo ID. Riprova.");
                                        continue;
                                    }

                                    if (punto instanceof Distributore distributore) {
                                        if (distributore.getStato() == StatoDistributore.NON_DISPONIBILE) {
                                            System.out.println("Il distributore non è disponibile: " + distributore);
                                            punto = null;
                                            continue;
                                        }
                                    }
                                }catch (NumberFormatException e){
                                    System.out.println("Errore: L'ID deve essere un numero valido!");
                                    punto = null;
                                }
                            }

                            Abbonamento nuovoAbbonamento = new Abbonamento(LocalDate.now(), punto, tipo, tessera);
                            double prezzo = nuovoAbbonamento.getPrezzo();

                            if (utente.getPortafoglio() < prezzo) {
                                System.out.println("Errore: Credito insufficiente! Il tuo saldo attuale è: " + utente.getPortafoglio() + " €, ma l'abbonamento costa: " + prezzo + " €");
                                break;
                            }

                            double nuovoSaldo = utente.getPortafoglio() - prezzo;
                            utente.setPortafoglio(nuovoSaldo);

                            utenteDAO.update(utente);
                            abbonamentoDAO.save(nuovoAbbonamento);

                            System.out.println("Abbonamento acquistato e associato alla tessera n. " + tessera.getId());
                            System.out.println("Nuovo saldo del portafoglio: " + utente.getPortafoglio() + " €");

                        } catch (NumberFormatException e) {
                            System.out.println("Errore: inserisci un numero valido!");
                        } catch (NotFoundException e) {
                            System.out.println("Errore: " + e.getMessage());
                        }
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

