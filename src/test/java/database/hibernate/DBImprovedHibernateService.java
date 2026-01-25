package database.hibernate;

import database.hibernate.models.Animal;
import database.hibernate.models.Places;
import database.hibernate.models.Workman;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class DBImprovedHibernateService {

    private static final SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();

    private Session openSession() {
        return sessionFactory.openSession();
    }

    /**
     * Пример HQL с параметром: выбор животного по имени
     */
    public Animal getAnimalByName(String name) {
        try (Session session = openSession()) {
            return session.createQuery(
                            "SELECT a FROM Animal a WHERE a.name = :name",
                            Animal.class
                    )
                    .setParameter("name", name)
                    .getSingleResult();
        }
    }

    /**
     * Пример получения сущности по первичному ключу
     * (используем Session#find вместо устаревшего Session#get).
     */
    public Workman getWorkmanById(int id) {
        try (Session session = openSession()) {
            return session.find(Workman.class, id);
        }
    }

    /**
     * HQL COUNT по сущности Animal.
     */
    public int getAnimalCount() {
        try (Session session = openSession()) {
            Long count = session.createQuery(
                            "SELECT COUNT(a) FROM Animal a",
                            Long.class
                    )
                    .getSingleResult();
            System.out.printf("Table public.animal has exact %s rows%n", count);
            return count != null ? count.intValue() : 0;
        }
    }

    /**
     * HQL COUNT по сущности Zoo.
     */
    public int getZooCount() {
        try (Session session = openSession()) {
            Long count = session.createQuery(
                            "SELECT COUNT(z) FROM Zoo z",
                            Long.class
                    )
                    .getSingleResult();
            return count != null ? count.intValue() : 0;
        }
    }

    /**
     * HQL COUNT по сущности Places.
     */
    public int getPlacesCount() {
        try (Session session = openSession()) {
            Long count = session.createQuery(
                            "SELECT COUNT(p) FROM Places p",
                            Long.class
                    )
                    .getSingleResult();
            return count != null ? count.intValue() : 0;
        }
    }

    /**
     * Пример вставки сущности через Hibernate + транзакция.
     */
    public void insertWorkman(Workman workman) {
        try (Session session = openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(workman);
                tx.commit();
            } catch (RuntimeException e) {
                if (tx.getStatus().canRollback()) {
                    tx.rollback();
                }
                throw e;
            }
        }
    }

    public void insertAnimal(Animal animal) {
        try (Session session = openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(animal);
                tx.commit();
            } catch (RuntimeException e) {
                if (tx.getStatus().canRollback()) {
                    tx.rollback();
                }
                throw e;
            }
        }
    }

    public void insertPlaces(Places places) {
        try (Session session = openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.persist(places);
                tx.commit();
            } catch (RuntimeException e) {
                if (tx.getStatus().canRollback()) {
                    tx.rollback();
                }
                throw e;
            }
        }
    }

    /**
     * HQL с выборкой только нужного поля (name) из Zoo.
     */
    public List<String> getZooNameData() {
        try (Session session = openSession()) {
            return session.createQuery(
                            "SELECT z.name FROM Zoo z",
                            String.class
                    )
                    .getResultList();
        }
    }

    /**
     * Удаление Workman по id.
     * Здесь возможен ConstraintViolationException при нарушении FK.
     */
    public void deleteWorkmanById(int id) {
        try (Session session = openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                Workman workman = session.find(Workman.class, id);
                if (workman != null) {
                    session.remove(workman);
                }
                tx.commit();
            } catch (RuntimeException e) {
                if (tx.getStatus().canRollback()) {
                    tx.rollback();
                }
                throw e;
            }
        }
    }
}