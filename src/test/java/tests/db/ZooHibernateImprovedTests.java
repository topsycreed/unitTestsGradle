package tests.db;

import database.jdbc.DatabaseUtils;
import database.hibernate.DBImprovedHibernateService;
import database.hibernate.models.*;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Тесты с использованием функционала Hibernate через singleton SessionFactory, Session
 * private static final SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
 * private Session openSession() {
 *     return sessionFactory.openSession();
 * }
 * Использованием параметризации SQL, закрытием сессий (и транзакций)
 * Лучше использовать try-with-resources
 * try (Session session = openSession()) {
 *     // действия
 * }
 * Использование HQL (Hibernate Query Language) — работает с именами сущностей и полей, а не с названиями таблиц и колонок в БД.
 * Было: return session.createNativeQuery("SELECT id, \"name\", age, \"position\" from workman where id = '88'", Workman.class).getResultList().get(0);
 * Стало: Workman workman = session.get(Workman.class, 88);
 * Или: return session.createQuery("FROM Workman WHERE id = :id", Workman.class)
 *               .setParameter("id", 88)
 *               .getSingleResult();
 */
@Feature("database")
@Story("Hibernate")
class ZooHibernateImprovedTests {
    static DBImprovedHibernateService dbHibernateService;

    @BeforeAll
    static void init() {
        dbHibernateService = new DBImprovedHibernateService();
        DatabaseUtils.insertTestData();
    }

    /**
     * В таблице public.animal ровно 10 записей
     */
    @Test
    void countRowAnimal() {
        Assertions.assertEquals(10, dbHibernateService.getAnimalCount());
    }

    static Stream<Animal> animalProvider() {
        List<Animal> animals = new ArrayList<>();
        for (int id = 1; id <= 10; id++) {
            Animal animal = new Animal();
            animal.setId(id);
            animal.setName("Sharik");
            animal.setAge(10);

            AnimalType type = new AnimalType();
            type.setId(1);
            animal.setType(type);

            Sex sex = new Sex();
            sex.setId(1);
            animal.setSex(sex);

            Places place = new Places();
            place.setId(1);
            animal.setPlace(place);

            animals.add(animal);
        }
        return animals.stream();
    }

    /**
     * В таблицу public.animal нельзя добавить строку с индексом от 1 до 10 включительно
     */
    @ParameterizedTest
    @MethodSource("animalProvider")
    void insertIndexAnimal(Animal animal) {
        assertThrows(PersistenceException.class, () -> dbHibernateService.insertAnimal(animal));
    }

    /**
     * В таблицу public.workman нельзя добавить строку с name = null
     */
    @Test
    void insertNullToWorkman() {
        Workman workman = new Workman();
        workman.setId(88);
        workman.setName(null);
        workman.setAge(12);

        Position position = new Position();
        position.setId(1);
        workman.setPosition(position);

        assertThrows(PersistenceException.class,
                () -> dbHibernateService.insertWorkman(workman));
    }

    /**
     * Если в таблицу public.places добавить еще одну строку, то в ней будет 6 строк
     */
    @Test
    void insertPlacesCountRow() {
        int sizeBefore = dbHibernateService.getPlacesCount();
        Places places = new Places();
        places.setId(6);
        places.setRow(1);
        places.setPlace_num(185);
        places.setName("Загон 1");
        dbHibernateService.insertPlaces(places);
        Assertions.assertEquals(sizeBefore + 1, dbHibernateService.getPlacesCount());
    }

    /**
     * В таблице public.zoo всего три записи с name 'Центральный', 'Северный', 'Западный'
     */
    @Test
    void countRowZoo() {
        List<String> expectedNames = Arrays.asList("Центральный", "Северный", "Западный");

        int actualZooCountRow = dbHibernateService.getZooCount();
        Assertions.assertEquals(3, actualZooCountRow);

        List<String> actualNames = dbHibernateService.getZooNameData();
        assertThat(actualNames, containsInAnyOrder(expectedNames.toArray()));
    }

    @Test
    void deleteWorkmanByIdTest() {
        int workmanId = 1;

        assertThrows(ConstraintViolationException.class, () -> dbHibernateService.deleteWorkmanById(workmanId));
    }
}
