package robertocafagna;

import Enum.StatoDistributore;
import dao.PuntoDiEmissioneDAO;
import entities.Distributore;
import entities.PuntoDiEmissione;
import entities.Rivenditore;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class Application {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("BW_backendpu");


    public static void main(String[] args) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        System.out.println("Hello World!");

        // creo punti di emissioni
        //persisto prima punto di emissione
        PuntoDiEmissioneDAO dao = new PuntoDiEmissioneDAO(entityManager);

        try {
            System.out.println("Inizio creazione dei distributori e rivenditori...");

            // rivenditori
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

            // distributori
            PuntoDiEmissione distributore1 = new Distributore(StatoDistributore.DISPONIBILE, "Stazione Termini, Roma");
            PuntoDiEmissione distributore2 = new Distributore(StatoDistributore.DISPONIBILE, "Stazione Cadorna, Milano");
            PuntoDiEmissione distributore3 = new Distributore(StatoDistributore.DISPONIBILE, "Piazza Aldo Moro, Bari");
            PuntoDiEmissione distributore4 = new Distributore(StatoDistributore.DISPONIBILE, "Via Toledo 110, Napoli");
            PuntoDiEmissione distributore5 = new Distributore(StatoDistributore.NON_DISPONIBILE, "Fermata Tram 14, Torino");

            // metodo fatto nel dao per salvare i rivenditori
            dao.SalvaPuntoDiEmissione(rivenditore1);
            dao.SalvaPuntoDiEmissione(rivenditore2);
            dao.SalvaPuntoDiEmissione(rivenditore3);
            dao.SalvaPuntoDiEmissione(rivenditore4);
            dao.SalvaPuntoDiEmissione(rivenditore5);

            // metodo fatto nel dao per salvare i distributori
            dao.SalvaPuntoDiEmissione(distributore1);
            dao.SalvaPuntoDiEmissione(distributore2);
            dao.SalvaPuntoDiEmissione(distributore3);
            dao.SalvaPuntoDiEmissione(distributore4);
            dao.SalvaPuntoDiEmissione(distributore5);

            System.out.println("Tutti i distributori e rivenditori sono stati elaborati!");

        } catch (Exception e) {
            System.err.println("Errore nel main durante il popolamento: " + e.getMessage());
            e.printStackTrace();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }

    }

}