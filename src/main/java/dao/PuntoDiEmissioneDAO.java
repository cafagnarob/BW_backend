package dao;

import Enum.StatoDistributore;
import entities.Distributore;
import entities.PuntoDiEmissione;
import exception.FuoriServizioException;
import exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;


public class PuntoDiEmissioneDAO {
    private final EntityManager em;

    public PuntoDiEmissioneDAO(EntityManager em) {
        this.em = em;
    }

    //metodo per salvare un punto di emissione
    public void SalvaPuntoDiEmissione(PuntoDiEmissione puntoDiEmissione) {
        EntityTransaction transaction = em.getTransaction();

        // controllo se il punto di emisssione è il distributore allora lancio exception fuori servizio
        if (puntoDiEmissione instanceof Distributore) {
            Distributore distributore = (Distributore) puntoDiEmissione;
            if (distributore.getStato() == StatoDistributore.NON_DISPONIBILE) {
                throw new FuoriServizioException("Impossibile salvare o utilizzare il punto di emissione: il distributore è FUORI SERVIZIO.");
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

    public PuntoDiEmissione TrovaPuntoDiEmissionePerId(long id) {
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


}