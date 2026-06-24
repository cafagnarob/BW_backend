package dao;

import Enum.TipoManutenzione;
import entities.Manutenzione;
import entities.Mezzo;
import exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ManutenzioneDAO {
    private final EntityManager entityManager;

    public ManutenzioneDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //save
    public void save(Manutenzione newManutenzione) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        try {
            transaction.begin();
            this.entityManager.persist(newManutenzione);
            transaction.commit();
            System.out.println("LA MANUTENZIONE " + newManutenzione + "è stato aggiungo al DB");
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Errore nel salvataggio del DB " + e.getMessage());
        }
    }

    //get
    //
    public Manutenzione getById(Long id) {
        Manutenzione fromDB = this.entityManager.find(Manutenzione.class, id);
        if (fromDB == null) throw new NotFoundException("Manutenzione non trovata");
        System.out.println("MANUTENZIONE RICHIESTA" + fromDB);
        return fromDB;

    }

    // delete
    public void delete(Long id) {
        Manutenzione fromDB = this.getById(id);
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();
        this.entityManager.remove(fromDB);
        transaction.commit();
        System.out.println("MANUTENZIONE " + fromDB + "è stato rimosso dal DB");
    }

    // STAMPA INFO MANUTENZIONI DATO ID MEZZO

    public List<Manutenzione> stampaInfoManutenzione(Long idMezzo) {
        TypedQuery<Manutenzione> query = this.entityManager.createQuery(
                "SELECT m FROM Manutenzione m WHERE m.mezzo.id = :param", Manutenzione.class);
        query.setParameter("param", idMezzo);
        List<Manutenzione> risultati = query.getResultList();
        System.out.println("LISTA MANUTENZIONE DI" + idMezzo + ":" + risultati);
        return risultati;
    }

    public void popolaSeVuoto() {
        long count = entityManager.createQuery("SELECT COUNT(m) FROM Manutenzione m", Long.class).getSingleResult();
        if (count == 0) {

            List<Mezzo> mezzi = entityManager.createQuery("SELECT m FROM Mezzo m", Mezzo.class).getResultList();

            Mezzo m2 = mezzi.size() > 1 ? mezzi.get(1) : mezzi.get(0); // Autobus in MANUTENZIONE
            Mezzo m6 = mezzi.size() > 5 ? mezzi.get(5) : mezzi.get(0); // Tram in MANUTENZIONE
            Mezzo m9 = mezzi.size() > 8 ? mezzi.get(8) : mezzi.get(0); // Tram in MANUTENZIONE

            Mezzo m3 = mezzi.size() > 2 ? mezzi.get(2) : mezzi.get(0);

            List<Manutenzione> manutenzioni = new ArrayList<>();

            manutenzioni.add(new Manutenzione(
                    LocalDate.of(2026, 3, 10),
                    LocalDate.of(2026, 3, 15),
                    TipoManutenzione.ORDINARIA,
                    m3
            ));

            manutenzioni.add(new Manutenzione(
                    LocalDate.of(2026, 6, 18),
                    LocalDate.of(2026, 6, 28),
                    TipoManutenzione.STRAORDINARIA,
                    m2
            ));

            manutenzioni.add(new Manutenzione(
                    LocalDate.of(2026, 6, 24),
                    LocalDate.of(2026, 6, 26),
                    TipoManutenzione.STRAORDINARIA,
                    m6
            ));

            manutenzioni.add(new Manutenzione(
                    LocalDate.of(2026, 6, 23),
                    LocalDate.of(2026, 7, 5),
                    TipoManutenzione.ORDINARIA,
                    m9
            ));

            for (Manutenzione m : manutenzioni) {
                try {
                    save(m);
                } catch (Exception e) {
                    System.err.println("Errore nel salvare la manutenzione");
                }
            }

            System.out.println("Manutenzioni aggiunte!");
        } else {
            System.out.println("Tabella Manutenzione piena");
        }
    }

}
