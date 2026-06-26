package dao;

import entities.Mezzo;
import entities.Percorrenza;
import entities.Tratta;
import exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PercorrenzaDAO {

    private final EntityManager entityManager;

    public PercorrenzaDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    // SAVE
    public void save(Percorrenza p) {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist(p);
        transaction.commit();
        System.out.println("Nuova Percorrenza " + p + " aggiunta con successo al DB");

    }

    // GET
    public Percorrenza getById(long id) {
        Percorrenza p = entityManager.find(Percorrenza.class, id);
        if (p == null) {
            throw new NotFoundException("Non esistono percorrenze con questo id: " + id);
        }
        return p;
    }

    //DELETE
    public void delete(long id) {
        Percorrenza fromDB = this.getById(id);
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();
        this.entityManager.remove(fromDB);
        transaction.commit();
        System.out.println("La Percorrenza " + fromDB + "è stato rimosso dal DB");
    }

    // Tempo effettivo da ID
    public int getTempoEffettivoDaId(long id) {

        Percorrenza p = this.getById(id);
        return p.getTempoPercorrenzaEffettivo();
    }

    // Nuova percorrenza assegnando una tratta ad un mezzo

    public void assegnaMezzoATratta(Mezzo mezzo, Tratta tratta, LocalDate data) {

        Percorrenza nuovaPercorrenza = new Percorrenza(data, mezzo, tratta);
        this.save(nuovaPercorrenza);
    }

    // Informazioni di percorreze da id di un mezzo

    public void stampaInfoDaIdMezzo(long idMezzo) {

        TypedQuery<Percorrenza> query = entityManager.createQuery(
                "SELECT p FROM Percorrenza p WHERE p.mezzo.id = :idMezzo",
                Percorrenza.class
        );
        query.setParameter("idMezzo", idMezzo);

        List<Percorrenza> listaPercorrenze = query.getResultList();

        if (listaPercorrenze.isEmpty()) {
            throw new NotFoundException("Non esistono percorrenze con questo id: " + idMezzo);
        } else {
            System.out.println("Storico percorrenze registrate per questo mezzo: " + idMezzo);
            for (Percorrenza p : listaPercorrenze) {
                System.out.println("Data: " + p.getData() +
                        " Tempo effettivo: " + p.getTempoPercorrenzaEffettivo() + " minuti" +
                        " Tratta: ID " + p.getTratta().getId() +
                        " (" + p.getTratta().getPartenza() + " -> " + p.getTratta().getCapolinea() + ")");
            }
        }
    }

    public void listaPercorrenzePerMezzo(Long idMezzo, Long idTratta) {
        TypedQuery<Percorrenza> query = entityManager.createQuery(
                "SELECT p FROM Percorrenza p WHERE p.mezzo.id = :param AND p.tratta.id = :param2"
                , Percorrenza.class);
        query.setParameter("param", idMezzo);
        query.setParameter("param2", idTratta);
        List<Percorrenza> listRes = query.getResultList();
        System.out.println("------ IL NUMERO DI VOLTE CHE IL MEZZO" +
                idMezzo + "HA EFFETTUATO LA TRATTA" + idTratta + " E': " + listRes.size());
        listRes.forEach(System.out::println);
    }


    public List<Percorrenza> listaPercorrenzePerTrattaOggi(Long idTratta) {
        TypedQuery<Percorrenza> query = entityManager.createQuery(
                "SELECT p FROM Percorrenza p WHERE p.mezzo.id= :param AND p.data=CURRENT_DATE", Percorrenza.class
        );
        query.setParameter("param", idTratta);
        List<Percorrenza> res = query.getResultList();
        res.forEach(System.out::println);
        return res;
    }

    public void popolaSeVuoto() {
        long count = entityManager.createQuery("SELECT COUNT(p) FROM Percorrenza p", Long.class).getSingleResult();
        if (count == 0) {

            List<Mezzo> mezzi = entityManager.createQuery("SELECT m FROM Mezzo m", Mezzo.class).getResultList();
            List<Tratta> tratte = entityManager.createQuery("SELECT t FROM Tratta t", Tratta.class).getResultList();

            Mezzo m1 = mezzi.get(0);
            Mezzo m2 = mezzi.get(1);
            Mezzo m3 = mezzi.get(2);
            Mezzo m4 = mezzi.get(3);
            Mezzo m5 = mezzi.get(4);
            Mezzo m6 = mezzi.get(5);
            Mezzo m7 = mezzi.get(6);
            Mezzo m8 = mezzi.get(7);
            Mezzo m9 = mezzi.get(8);

            Tratta t1 = tratte.get(0);
            Tratta t2 = tratte.get(1);
            Tratta t3 = tratte.get(2);
            Tratta t4 = tratte.get(3);
            Tratta t5 = tratte.get(4);
            Tratta t6 = tratte.get(5);
            Tratta t7 = tratte.get(6);
            Tratta t8 = tratte.get(7);
            Tratta t9 = tratte.get(8);
            Tratta t10 = tratte.get(9);
            Tratta t11 = tratte.get(10);
            Tratta t12 = tratte.get(11);
            Tratta t13 = tratte.get(12);
            Tratta t14 = tratte.get(13);
            Tratta t15 = tratte.get(14);
            List<Percorrenza> percorrenze = new ArrayList<>();

            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 17), m1, t1, 48));
            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 17), m2, t2, 58));
            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 18), m3, t3, 125));
            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 18), m4, t4, 180));
            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 18), m5, t5, 162));
            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 19), m6, t6, 88));
            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 20), m7, t7, 70));
            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 20), m8, t8, 205));
            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 22), m9, t9, 238));
            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 22), m1, t10, 315));
            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 23), m2, t11, 49));
            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 23), m3, t12, 35));
            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 23), m4, t13, 112));
            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 23), m5, t14, 93));
            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 23), m6, t15, 76));

            for (Percorrenza p : percorrenze) {
                try {
                    save(p);
                } catch (Exception e) {
                    System.err.println("Errore nel salvare la percorrenza");
                }
            }


            System.out.println("Percorrenze aggiunte!");
        } else {
            System.out.println("Tabella Percorrenza piena");
        }
    }
}

