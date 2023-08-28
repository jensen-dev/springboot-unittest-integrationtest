package unittest.assignment.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import unittest.assignment.dto.StudentDto;
import unittest.assignment.entity.Student;
import unittest.assignment.repository.StudentRepository;
import unittest.assignment.service.StudentService;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student createStudent(StudentDto studentDto) {
        Student student = new Student();
        student.setFirstName(studentDto.getFn());
        student.setLastName(studentDto.getLn());
        student.setEmail(studentDto.getEmail());

        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
