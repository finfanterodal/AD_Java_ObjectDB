/*
 The main method creates an ObjectDB database file, 
 stores 1000 Point objects in the database, and then run a few queries, 
 including a query that retrieves all the 1000 Point objects from the database. 
 */
package java_objectdb;

import javax.persistence.*;
import java.util.*;

/**
 *
 * @author oracle
 */
public class Java_ObjectDB {

    public static void main(String[] args) {
        // Open a database connection
        // (create a new database if it doesn't exist yet):
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("objectdb/db/p2.odb");
        EntityManager em = emf.createEntityManager();

        System.out.println(" 1) almacenar 10 puntos na base ( non 1000 como di o exemplo do titorial)");
        insercionPuntos(em);
        System.out.println("2) listar todos os puntos ( o seu id , e demais atributos)");
        listarPuntos(em);

        System.out.println("3) amosar os atributos do punto con id =10 ");
        mostrarID(em);

        System.out.println("4) actualizar o punto de id =10 , coordenada y,  ao valor que ten mais 2,\n"
                + "     e dicir se o atributo y do punto de id=10  valia 4 , debe pasar a valer 6. ");
        modificarID(em);
        mostrarID(em);
      
         System.out.println("5) eliminar punto de id=5 ");
         borrarPunto(em);
         listarPuntos(em);

         System.out.println("6)  actualizacion masiva selectiva : ");
         modificacionMasiva(em, 6);
         listarPuntos(em);

         System.out.println("7) borrado masivo selectivo masivo(delete queries).");
         deleteMasivo(em, 5);
         listarPuntos(em);
         /*
         // Store 1000 Point objects in the database:
         em.getTransaction().begin();
         for (int i = 0; i < 1000; i++) {
         Point p = new Point(i, i);
         em.persist(p);
         }
         em.getTransaction().commit();
         */ // Find the number of Point objects in the database:

        /*
         // Find the average X value:
         Query q2 = em.createQuery("SELECT AVG(p.x) FROM Point p");
         System.out.println("Average X: " + q2.getSingleResult());
         */
        // Close the database connection:
        em.close();
        emf.close();
    }
    /*
     1) almacenar 10 puntos na base ( non 1000 como di o exemplo do titorial)
     */

    public static void insercionPuntos(EntityManager em) {
        em.getTransaction().begin();
        for (int i = 0; i <= 10; i++) {
            Point p = new Point(i, i);
            em.persist(p);
        }
        em.getTransaction().commit();
    }
    /*
     2) listar todos os puntos ( o seu id , e demais atributos)
     */

    public static void listarPuntos(EntityManager em) {
        TypedQuery<Point> query = em.createQuery("SELECT p FROM Point p", Point.class);
        List<Point> results = query.getResultList();
        for (Point p : results) {
            System.out.println(p);
        }
    }
    /*
     3) amosar os atributos do punto con id =10 
     */

    public static void mostrarID(EntityManager em) {
        TypedQuery<Point> query = em.createQuery("SELECT p FROM Point p where p.id=10", Point.class);
        List<Point> results = query.getResultList();
        for (Point p : results) {
            System.out.println(p);
        }
    }
    /*
     4) actualizar o punto de id =10 , coordenada y,  ao valor que ten mais 2,
     e dicir se o atributo y do punto de id=10  valia 4 , debe pasar a valer 6. 
     */

    public static void modificarID(EntityManager em) {
        //TypedQuery<Point> query = em.createQuery("UPDATE Point p SET p.x=2 WHERE p.id=10", Point.class)
        Point p1 = null;
        em.getTransaction().begin();
        try {
            /* p1 = em.find(Point.class, 10);
             p1.setX(p1.getX() + 2);
             em.persist(p1);*/
            em.createQuery("UPDATE Point SET x=x+2 WHERE id=10", Point.class).executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }

    }
    /*
     5) eliminar punto de id=5 
     */

    public static void borrarPunto(EntityManager em) {

        Point p1 = null;
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            p1 = em.find(Point.class, 5);
            em.remove(p1);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }

    /*
     6)  actualizacion masiva selectiva : 
     actualizar coordenada y ao valor 1000 para todos os puntos que teñan un  valor de 
     y  inferior a un valor pasado por parametro (por exemplo facelo para o valor  6).
     */
    public static void modificacionMasiva(EntityManager em, int valor) {
        //em.createQuery("UPDATE Point p SET p.y=1000 WHERE p.id<=6", Point.class);

        TypedQuery<Point> query = em.createQuery("SELECT p FROM Point p", Point.class);
        List<Point> results = query.getResultList();
        for (Point p : results) {
            if (p.getY() < valor) {
                Point p1 = null;
                EntityTransaction tx = em.getTransaction();
                tx.begin();
                try {
                    p1 = em.find(Point.class, p.getId());
                    p1.setY(1000);
                    em.persist(p1);
                    tx.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    tx.rollback();
                }
            }

        }
    }

    /*
     7) borrado masivo selectivo masivo(delete queries).
     Eliminar todos os puntos cuxo valor do atributo y sexa inferior a un valor
     pasado por parametro ( por exemplo facelo para o valor 3 )
     */
    public static void deleteMasivo(EntityManager em, int valor) {

        TypedQuery<Point> query = em.createQuery("SELECT p FROM Point p", Point.class);
        List<Point> results = query.getResultList();
        for (Point p : results) {
            if (p.getX() < valor) {

                Point p1 = null;
                EntityTransaction tx = em.getTransaction();
                tx.begin();
                try {
                    p1 = em.find(Point.class, p.getId());
                    em.remove(p1);
                    tx.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    tx.rollback();
                }

            }

        }

    }

}
