import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParametrizedTests {
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void sourceTest(int parameter) {
        int actualSum = parameter + parameter;
        int expectedSum = 2 * parameter;
        assertEquals(expectedSum, actualSum, "Суммы должны быть разными");
    }

    @ParameterizedTest
    @CsvSource({"John, Doe", "Alice, Smith"})
    void csvTest(String firstName, String lastName) {
        List<String> expectedPeople = Arrays.asList("John Doe1", "Alice Smith");
        assertTrue(expectedPeople.contains(firstName + " " + lastName));
    }

    static Stream<String> provideParameters() {
        return Stream.of("One", "Two", "Three");
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    void customParametersTest(String parameter) {
        List<String> expectedList = Arrays.asList("one", "two", "three");
        assertTrue(expectedList.contains(parameter.toLowerCase(Locale.ROOT)));
    }

}
