package unittest.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unittest.assignment.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
