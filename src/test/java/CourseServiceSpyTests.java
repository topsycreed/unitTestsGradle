import org.fpmi.course.CourseRepository;
import org.fpmi.course.CourseService;
import org.fpmi.course.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CourseServiceSpyTests {
    @InjectMocks
    private CourseService courseService;
    @Spy
    private CourseRepository courseRepository;

    @Test
    void findStudentByNameMockitoSpyTest() {
        String name = "Ivan";

        //возьмет студента из реального репозитория несмотря на то, что мы не мокали студентов в репозитории
        Student actualStudent = courseService.findStudentByName(name);
        Student expectedStudent = new Student(name, actualStudent.getScore());
        assertEquals(expectedStudent, actualStudent);
    }
}
