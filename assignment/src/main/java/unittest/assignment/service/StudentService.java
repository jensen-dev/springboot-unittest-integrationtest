package unittest.assignment.service;

import org.springframework.http.ResponseEntity;
import unittest.assignment.dto.StudentDto;
import unittest.assignment.entity.Student;

import java.util.List;

public interface StudentService {
    public Student createStudent(StudentDto student);
    public List<Student> getAllStudents();
}
