import org.fpmi.course.CourseRepository;
import org.fpmi.course.CourseService;
import org.fpmi.course.Student;
import org.fpmi.course.StudentNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CourseServiceTests {
    @InjectMocks
    private CourseService courseService;
    @Mock
    private CourseRepository courseRepository;

    @Test
    void numberOfStudentsWithoutMockitoTest() {
        CourseService courseService = new CourseService(new CourseRepository());
        assertEquals(3, courseService.getNumberOfAllStudents());
    }

    @Test
    void numberOfStudentsMockitoTest() {
        //arrange
        Student student1 = new Student("Ivan", 5.0);
        Student student2 = new Student("Elena", 4.9);

        //act
        Mockito.when(courseRepository.findAll()).thenReturn(List.of(student1, student2));

        //assert
        int actualNumberOfStudents = courseService.getNumberOfAllStudents();
        assertEquals(2, actualNumberOfStudents);
        Mockito.verify(courseRepository, Mockito.times(1)).findAll();
    }

    @Test
    void findStudentByNameMockitoTest() {
        //arrange
        Student student1 = new Student("Igor", 5.0);

        //act
        Mockito.when(courseRepository.findByName(Mockito.anyString())).thenReturn(Optional.of(student1));

        //assert
        Student actualStudent = courseService.findStudentByName("Igor");
        assertEquals(student1, actualStudent);
    }

    @Test
    void findStudentByNameMockitoExceptionTest() {
        //arrange
        String name = "Maksim";

        //act
        Mockito.doThrow(new StudentNotFoundException(String.format("Student with name %s not found", name)))
                .when(courseRepository).findByName(Mockito.anyString());

        //assert
        Exception thrown = assertThrows(StudentNotFoundException.class, () -> courseService.findStudentByName(name));
        assertEquals(String.format("Student with name %s not found", name), thrown.getMessage());
    }

    @Test
    void findStudentByNameMockitoNoSpyTest() {
        String name = "Ivan";

        //не сможет найти Ивана, так как мы не имитировали это поведение и не используем spy чтобы вернуть реальное значение
        Exception thrown = assertThrows(StudentNotFoundException.class,
                () -> courseService.findStudentByName(name));
        assertEquals(String.format("Student with name %s not found", name), thrown.getMessage());
    }

    @Test
    void averageScoreMockitoTest() {
        //need to implement
    }
}
