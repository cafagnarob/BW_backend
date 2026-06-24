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


    public void popolaSeVuoto() {
        long count = entityManager.createQuery("SELECT COUNT(p) FROM Percorrenza p", Long.class).getSingleResult();
        if (count == 0) {

            List<Mezzo> mezzi = entityManager.createQuery("SELECT m FROM Mezzo m", Mezzo.class).getResultList();
            List<Tratta> tratte = entityManager.createQuery("SELECT t FROM Tratta t", Tratta.class).getResultList();

            Mezzo m1 = mezzi.get(0);
            Mezzo m2 = mezzi.size() > 1 ? mezzi.get(1) : m1;
            Mezzo m3 = mezzi.size() > 2 ? mezzi.get(2) : m1;
            Mezzo m4 = mezzi.size() > 3 ? mezzi.get(3) : m1;
            Mezzo m5 = mezzi.size() > 4 ? mezzi.get(4) : m1;
            Mezzo m6 = mezzi.size() > 5 ? mezzi.get(5) : m1;
            Mezzo m7 = mezzi.size() > 6 ? mezzi.get(6) : m1;
            Mezzo m8 = mezzi.size() > 7 ? mezzi.get(7) : m1;
            Mezzo m9 = mezzi.size() > 8 ? mezzi.get(8) : m1;

            Tratta t1 = tratte.get(0);
            Tratta t2 = tratte.size() > 1 ? tratte.get(1) : t1;
            Tratta t3 = tratte.size() > 2 ? tratte.get(2) : t1;
            Tratta t4 = tratte.size() > 3 ? tratte.get(3) : t1;
            Tratta t5 = tratte.size() > 4 ? tratte.get(4) : t1;
            Tratta t6 = tratte.size() > 5 ? tratte.get(5) : t1;
            Tratta t7 = tratte.size() > 6 ? tratte.get(6) : t1;
            Tratta t8 = tratte.size() > 7 ? tratte.get(7) : t1;
            Tratta t9 = tratte.size() > 8 ? tratte.get(8) : t1;
            Tratta t10 = tratte.size() > 9 ? tratte.get(9) : t1;
            Tratta t11 = tratte.size() > 10 ? tratte.get(10) : t1;
            Tratta t12 = tratte.size() > 11 ? tratte.get(11) : t1;
            Tratta t13 = tratte.size() > 12 ? tratte.get(12) : t1;
            Tratta t14 = tratte.size() > 13 ? tratte.get(13) : t1;
            Tratta t15 = tratte.size() > 14 ? tratte.get(14) : t1;

            List<Percorrenza> percorrenze = new ArrayList<>();

            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 20), m1, t1, 48));
            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 20), m2, t2, 58));
            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 21), m3, t3, 125));
            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 21), m4, t4, 180));
            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 22), m5, t5, 162));
            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 22), m6, t6, 88));
            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 23), m7, t7, 70));
            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 23), m8, t8, 205));
            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 24), m9, t9, 238));
            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 24), m1, t10, 315));
            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 25), m2, t11, 49));
            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 25), m3, t12, 35));
            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 26), m4, t13, 112));
            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 26), m5, t14, 93));
            percorrenze.add(new Percorrenza(LocalDate.of(2026, 6, 26), m6, t15, 76));

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

