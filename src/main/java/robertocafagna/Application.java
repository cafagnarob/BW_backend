package robertocafagna;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Application {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("u4-w3-d5pu");


    public static void main(String[] args) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        System.out.println("Hello World!");
    }
}
