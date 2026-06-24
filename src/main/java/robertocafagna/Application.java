package robertocafagna;

import dao.PuntoDiEmissioneDAO;
import dao.TesseraDAO;
import dao.TitoloDiViaggioDAO;
import dao.UtenteDAO;
import entities.Distributore;
import entities.PuntoDiEmissione;
import entities.Rivenditore;
import entities.Utente;
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
        PuntoDiEmissioneDAO puntoDao = new PuntoDiEmissioneDAO(entityManager);
        TitoloDiViaggioDAO TdiViaggioDAO = new TitoloDiViaggioDAO(entityManager);
        TesseraDAO tesseraDAO = new TesseraDAO(entityManager);

        //Ripopolamento delle tabelle
        utenteDAO.popolaSeVuoto();
        puntoDao.popolaSeVuoto();
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

        System.out.println("=== FINE TEST COMPLETO ===");*/


        System.out.println("Hello World!");
    }


}
