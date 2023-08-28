package unittest.assignment.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import unittest.assignment.dto.StudentDto;
import unittest.assignment.entity.Student;
import unittest.assignment.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentIntegrationContainerTest extends AbstractContainerBaseTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        studentRepository.deleteAll();
    }

    // JUnit test for create student
    @DisplayName("JUnit test for create student")
    @Test
    public void givenStudentObject_whenCreateStudent_thenReturnSavedStudent() throws Exception {
        // given - setup
        Student student = Student.builder().firstName("Budi").lastName("Putra").email("budi@imail.com")
                .build();

        StudentDto studentDto = new StudentDto();
        studentDto.setFn(student.getFirstName());
        studentDto.setLn(student.getLastName());
        studentDto.setEmail(student.getEmail());

        // when - action or the behavior that we are going to test
        ResultActions response = mockMvc.perform(post("/api/student/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentDto)));

        // then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    // JUnit test for get all students
    @DisplayName("JUnit test for get all students")
    @Test
    public void givenListOfStudents_whenGetAllStudents_thenReturnStudentList() throws Exception {
        // given - setup
        List<Student> studentList = new ArrayList<>();
        studentList.add(Student.builder().firstName("adam").lastName("smith").email("adam@fmail.com").build());
        studentList.add(Student.builder().firstName("budi").lastName("smith").email("budi@fmail.com").build());
        studentRepository.saveAll(studentList);

        // when - action or the behavior that we are going to test
        ResultActions response = mockMvc.perform(get("/api/student/getAll"));

        // then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(studentList.size())));
    }
}
