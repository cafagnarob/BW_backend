package dao;

import Enum.StatoDistributore;
import entities.Distributore;
import entities.PuntoDiEmissione;
import entities.Rivenditore;
import entities.Utente;
import exception.FuoriServizioException;
import exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;


public class PuntoDiEmissioneDAO {
    private final EntityManager em;

    public PuntoDiEmissioneDAO(EntityManager em) {
        this.em = em;
    }

    public List<PuntoDiEmissione> stampaPuntiDiEmissioneDisponibili() {
        try {
            TypedQuery<PuntoDiEmissione> query = em.createQuery(
                    "SELECT p FROM PuntoDiEmissione p " +
                            "WHERE TYPE(p) = Rivenditore " +
                            "OR (TYPE(p) = Distributore AND TREAT(p AS Distributore).stato = :stato)",
                    PuntoDiEmissione.class);
            query.setParameter("stato", StatoDistributore.DISPONIBILE);

            List<PuntoDiEmissione> lista = query.getResultList();
            lista.forEach(System.out::println);
            return lista;
        } catch (Exception ex) {
            System.out.println("Errore nel recupero dei punti di emissione: " + ex.getMessage());
            return List.of();
        }
    }

    //metodo per salvare un punto di emissione
    public void SalvaPuntoDiEmissione(PuntoDiEmissione puntoDiEmissione) {
        EntityTransaction transaction = em.getTransaction();

        // controllo se il punto di emisssione è il distributore allora lancio exception fuori servizio
        if (puntoDiEmissione instanceof Distributore) {
            Distributore distributore = (Distributore) puntoDiEmissione;
            if (distributore.getStato() == StatoDistributore.NON_DISPONIBILE) {
                System.out.println("Il distributore selezionato non è disponibile" + " " + distributore);
            }
        }

        try {
            transaction.begin();
            em.persist(puntoDiEmissione);
            transaction.commit();
            System.out.println("Hai salvato salvato con successo questo " + puntoDiEmissione);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Errore nel salvataggio del DB " + e.getMessage());
        }
    }

    //dopo metodo per istance of distributore, se abbiamo un'istance of rivenditore settiamo anch'esso

    //metodo per aggiornare i dati specifici di un rivenditore
    public void AggiornaRivenditore(long id, String nuovoNome, String nuovoIndirizzo) {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            PuntoDiEmissione trovato = em.find(PuntoDiEmissione.class, id);

            if (trovato == null) {
                throw new NotFoundException("Impossibile aggiornare: il punto di emissione con ID " + id + " non esiste.");
            }

            // controllo se il punto di emissione è effettivamente un Rivenditore
            if (trovato instanceof entities.Rivenditore) {
                entities.Rivenditore rivenditore = (entities.Rivenditore) trovato;


                System.out.println("Rivenditore aggiornato con successo: " + rivenditore);
            } else {
                System.out.println("L'ID fornito non appartiene a un Rivenditore, ma a un Distributore.");
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Errore durante l'aggiornamento del rivenditore: " + e.getMessage());
        }
    }


    //metodo per eliminarne punto di emissione tramite suo id
    public void CancellaPuntoDiEmissione(long id) {
        PuntoDiEmissione trovato = em.find(PuntoDiEmissione.class, id);

        if (trovato == null) {
            throw new NotFoundException("Impossibile completare l'operazione: il punto di emissione con ID " + id + " non è stato trovato.");
        }

        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.remove(trovato);
            transaction.commit();
            System.out.println("Hai eliminato con successo questo punto di emissione: " + trovato);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Errore durante l'eliminazione dal DB: " + e.getMessage());
        }
    }

    //trova p.emissione tramite id

    public PuntoDiEmissione getById(long id) {
        PuntoDiEmissione trovato = em.find(PuntoDiEmissione.class, id);

        if (trovato == null) {
            throw new NotFoundException("Impossibile trovare il punto di emissione: l'ID " + id + " non esiste nel database.");
        }

        return trovato;
    }

    //metodo per aggiornare un punto di emissione(se magari ha chiuso..ecc)

    public void AggiornaPuntoDiEmissione(PuntoDiEmissione puntoDiEmissione) {
        // verifico prima se l'entità esiste usando il suo id
        PuntoDiEmissione esistente = em.find(PuntoDiEmissione.class, puntoDiEmissione.getId());

        if (esistente == null) {
            throw new NotFoundException("Impossibile aggiornare: il punto di emissione con ID " + puntoDiEmissione.getId() + " non esiste nel database.");
        }

        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(puntoDiEmissione);
            transaction.commit();
            System.out.println("Hai aggiornato con successo questo punto di emissione: " + puntoDiEmissione);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Errore durante l'aggiornamento nel DB: " + e.getMessage());
        }
    }

    public void popolaSeVuoto() {
        long count = em.createQuery("SELECT COUNT(p) FROM PuntoDiEmissione p", Long.class).getSingleResult();

        if (count == 0) {

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

            PuntoDiEmissione distributore1 = new Distributore(StatoDistributore.DISPONIBILE, "Stazione Termini, Roma");
            PuntoDiEmissione distributore2 = new Distributore(StatoDistributore.DISPONIBILE, "Stazione Cadorna, Milano");
            PuntoDiEmissione distributore3 = new Distributore(StatoDistributore.DISPONIBILE, "Piazza Aldo Moro, Bari");
            PuntoDiEmissione distributore4 = new Distributore(StatoDistributore.DISPONIBILE, "Via Toledo 110, Napoli");
            PuntoDiEmissione distributore5 = new Distributore(StatoDistributore.NON_DISPONIBILE, "Fermata Tram 14, Torino");

            SalvaPuntoDiEmissione(rivenditore1);
            SalvaPuntoDiEmissione(rivenditore2);
            SalvaPuntoDiEmissione(rivenditore3);
            SalvaPuntoDiEmissione(rivenditore4);
            SalvaPuntoDiEmissione(rivenditore5);

            SalvaPuntoDiEmissione(distributore1);
            SalvaPuntoDiEmissione(distributore2);
            SalvaPuntoDiEmissione(distributore3);
            SalvaPuntoDiEmissione(distributore4);
            SalvaPuntoDiEmissione(distributore5);

            System.out.println("Punti di emissione aggiunti!");
        } else {
            System.out.println("Tabella Punto di emissione piena");
        }
    }

}