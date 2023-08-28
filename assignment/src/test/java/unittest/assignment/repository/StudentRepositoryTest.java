package unittest.assignment.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import unittest.assignment.entity.Student;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

    private Student student;

    @BeforeEach
    public void setup() {
        student = Student.builder()
                .firstName("jensen")
                .lastName("alimukti")
                .email("jensen.alimukti@email.com")
                .build();
    }

    // JUnit test for save student operation
    @DisplayName("JUnit test for save student operation")
    @Test
    public void givenStudentObject_whenSave_thenReturnSavedStudent() {
        // given - setup

        // when - action or the behavior that we are going to test
        Student savedStudent = this.studentRepository.save(student);

        // then - verify the output
        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent.getId()).isGreaterThan(0);
    }

    // JUnit test for get all students operation
    @DisplayName("JUnit test for get all students operation")
    @Test
    public void givenStudentsList_whenFindAll_thenStudentsList() {
        // given - setup
        Student student2 = Student.builder()
                .firstName("chelia")
                .lastName("kusumo")
                .email("chelia.kusumo@email.com")
                .build();

        studentRepository.save(student);
        studentRepository.save(student2);

        // when - action or the behavior that we are going to test
        List<Student> studentList = studentRepository.findAll();

        // then - verify the output
        assertThat(studentList).isNotNull();
        assertThat(studentList.size()).isEqualTo(2);
    }
}
