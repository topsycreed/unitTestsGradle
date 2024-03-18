import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AdditionalTests {
    @BeforeEach
    void setUp() {
        //Здесь размещаем код подготовки перед каждым тестом
        System.out.println("BeforeEach");
    }

    @AfterEach
    void tearDown() {
        // Здесь размещаем код очистки после каждого теста
        System.out.println("AfterEach");
    }

    @BeforeAll
    void setUpAll() {
        // Здесь размещаем код подготовки перед всеми тестами
        System.out.println("BeforeAll");
    }

    @AfterAll
    void tearDownAll() {
        // Здесь размещаем код очистки после всех тестов
        System.out.println("AfterAll");
    }

    @Test
    void test() {
        System.out.println("Test");
        assertEquals(1, 2);
    }
}
