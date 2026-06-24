package dao;

import entities.Tratta;
import entities.Utente;
import exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import Enum.TipoUtente;

public class UtenteDAO {
    private final EntityManager entityManager;

    public UtenteDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //save
    public void save(Utente newUtente) {
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();
        this.entityManager.persist(newUtente);
        transaction.commit();
        System.out.println("L' UTENTE " + newUtente + "è stato aggiungo al DB");
    }

    //get
    public Utente getById(Long id) {
        Utente fromDB = this.entityManager.find(Utente.class, id);
        if (fromDB == null) throw new NotFoundException("Utente non trovato");
        System.out.println("UTENTE RICHIESTO" + fromDB);
        return fromDB;

    }

    // delete
    public void delete(Long id) {
        Utente fromDB = this.getById(id);
        EntityTransaction transaction = this.entityManager.getTransaction();
        transaction.begin();
        this.entityManager.remove(fromDB);
        transaction.commit();
        System.out.println("L' UTENTE " + fromDB + "è stato rimosso dal DB");


    }

//
//    public void stampaInfoPercorrenza(Long idMezzo) {
//    }
//
//
//    public Tratta calcolaTempoEffettivo(Long idTratta) {
//    }


    public void popolaSeVuoto(){
        long count = entityManager.createQuery("SELECT COUNT(u) FROM Utente u", long.class).getSingleResult();
        if(count==0){
            Utente u1 = new Utente("Super", "Admin", "admin@system.com", 99, 9999.99, TipoUtente.ADMIN);
            Utente u2 = new Utente("Mario", "Rossi", "mario.rossi@mail.com", 25, 120.50, TipoUtente.VIAGGIATORE);
            Utente u3 = new Utente("Luca", "Bianchi", "luca.bianchi@mail.com", 30, 80.00, TipoUtente.VIAGGIATORE);
            Utente u4 = new Utente("Giulia", "Verdi", "giulia.verdi@mail.com", 22, 200.00, TipoUtente.VIAGGIATORE);
            Utente u5 = new Utente("Anna", "Neri", "anna.neri@mail.com", 28, 50.00, TipoUtente.VIAGGIATORE);
            Utente u6 = new Utente("Marco", "Gialli", "marco.gialli@mail.com", 35, 300.00, TipoUtente.VIAGGIATORE);
            Utente u7 = new Utente("Francesca", "Blu", "francesca.blu@mail.com", 27, 90.00, TipoUtente.VIAGGIATORE);
            Utente u8 = new Utente("Davide", "Rossi", "davide.rossi@mail.com", 31, 110.00, TipoUtente.VIAGGIATORE);
            Utente u9 = new Utente("Elena", "Ferrari", "elena.ferrari@mail.com", 24, 60.00, TipoUtente.VIAGGIATORE);
            Utente u10 = new Utente("Simone", "Romano", "simone.romano@mail.com", 29, 140.00, TipoUtente.VIAGGIATORE);
            Utente u11 = new Utente("Chiara", "Conti", "chiara.conti@mail.com", 26, 75.00, TipoUtente.VIAGGIATORE);
            Utente u12 = new Utente("Paolo", "Marino", "paolo.marino@mail.com", 40, 500.00, TipoUtente.VIAGGIATORE);
            Utente u13 = new Utente("Sara", "Greco", "sara.greco@mail.com", 23, 95.00, TipoUtente.VIAGGIATORE);
            Utente u14 = new Utente("Alessandro", "Fontana", "alessandro.fontana@mail.com", 33, 180.00, TipoUtente.VIAGGIATORE);
            Utente u15 = new Utente("Martina", "Costa", "martina.costa@mail.com", 21, 45.00, TipoUtente.VIAGGIATORE);
            Utente u16 = new Utente("Giorgio", "Lombardi", "giorgio.lombardi@mail.com", 38, 220.00, TipoUtente.VIAGGIATORE);
            Utente u17 = new Utente("Valentina", "Moretti", "valentina.moretti@mail.com", 27, 130.00, TipoUtente.VIAGGIATORE);
            Utente u18 = new Utente("Riccardo", "Barbieri", "riccardo.barbieri@mail.com", 32, 160.00, TipoUtente.VIAGGIATORE);
            Utente u19 = new Utente("Federica", "De Luca", "federica.deluca@mail.com", 26, 85.00, TipoUtente.VIAGGIATORE);
            Utente u20 = new Utente("Andrea", "Gallo", "andrea.gallo@mail.com", 29, 210.00, TipoUtente.VIAGGIATORE);

            save(u1);  save(u2);  save(u3);  save(u4);  save(u5);save(u6);  save(u7);  save(u8);  save(u9);  save(u10); save(u11); save(u12); save(u13);
            save(u14); save(u15); save(u16); save(u17); save(u18); save(u19); save(u20);
            System.out.println("Utenti aggiunti!");
        } else {
            System.out.println("Tabella utenti piena");
        }
    }

}
