package com.unittest.springBootTesting.repository;

import com.unittest.springBootTesting.model.Employee;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setup() {
        employee = Employee.builder()
                .firstName("jensen")
                .lastName("alimukti")
                .email("jensen.alimukti@email.com")
                .build();
    }

    // JUnit test for save employee operation
    @DisplayName("JUnit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
        // given - setup

        // when - action or the behavior that we are going to test
        Employee savedEmployee = this.employeeRepository.save(employee);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    // JUnit test for get all employees operation
    @DisplayName("JUnit test for get all employees operation")
    @Test
    public void givenEmployeesList_whenFindAll_thenEmployeesList() {
        // given - setup
        Employee employee2 = Employee.builder()
                .firstName("chelia")
                .lastName("kusumo")
                .email("chelia.kusumo@email.com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee2);

        // when - action or the behavior that we are going to test
        List<Employee> employeeList = employeeRepository.findAll();

        // then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    // JUnit test for get employee by id operation
    @DisplayName("JUnit test for get employee by id operation")
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {
        // given - setup
        employeeRepository.save(employee);

        // when - action or the behavior that we are going to test
        Employee employeeDb = employeeRepository.findById(employee.getId()).get();

        // then - verify the output
        assertThat(employeeDb).isNotNull();
    }

    // JUnit test for get employee by email operation
    @DisplayName("JUnit test for get employee by email operation")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {
        // given - setup
        employeeRepository.save(employee);

        // when - action or the behavior that we are going to test
        Employee employeeDb = employeeRepository.findByEmail(employee.getEmail()).get();

        // then - verify the output
        assertThat(employeeDb).isNotNull();
    }

    // JUnit test for update employee operation
    @DisplayName("JUnit test for update employee operation")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        // given - setup
        employeeRepository.save(employee);

        // when - action or the behavior that we are going to test
        Employee employee1 = employeeRepository.findById(employee.getId()).get();
        employee1.setEmail("jensen@gmeil.com");
        employee1.setFirstName("Jason");
        Employee updatedEmployee = employeeRepository.save(employee1);

        // then - verify the output
        assertThat(updatedEmployee.getEmail().equals("jensen@gmeil.com"));
        assertThat(updatedEmployee.getFirstName().equals("Jason"));
    }

    // JUnit test for delete employee operation
    @DisplayName("JUnit test for delete employee operation")
    @Test
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee() {
        // given - setup
        employeeRepository.save(employee);

        // when - action or the behavior that we are going to test
        employeeRepository.delete(employee);
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        // then - verify the output
        assertThat(employeeOptional).isEmpty();
    }

    // JUnit test for custom query using JPQL index
    @DisplayName("JUnit test for custom query using JPQL index")
    @Test
    public void givenFirstNameLastName_whenFindByJPQL_thenReturnEmployeeObject() {
        // given - setup
        employeeRepository.save(employee);

        // when - action or the behavior that we are going to test
        Employee savedEmployee = employeeRepository.findByJPQL(employee.getFirstName(), employee.getLastName());

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // JUnit test for custom query using JPQL with named parameters
    @DisplayName("JUnit test for custom query using JPQL with named parameters")
    @Test
    public void given_when_then() {
        // given - setup
        employeeRepository.save(employee);

        // when - action or the behavior that we are going to test
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(employee.getFirstName(), employee.getLastName());

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // JUnit test for custom query using native SQL with index
    @DisplayName("JUnit test for custom query using native SQL with index")
    @Test
    public void givenFirstNameLastName_whenFindByNativeSQL_thenReturnEmployeeObject() {
        // given - setup
        employeeRepository.save(employee);

        // when - action or the behavior that we are going to test
        Employee savedEmployee = employeeRepository.findByNativeSqlQuery(employee.getFirstName(), employee.getLastName());

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // JUnit test for custom query using native SQL with named params
    @DisplayName("JUnit test for custom query using native SQL with named params")
    @Test
    public void givenFirstNameLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject() {
        // given - setup
        employeeRepository.save(employee);

        // when - action or the behavior that we are going to test
        Employee savedEmployee = employeeRepository.findByNativeSqlQueryNamedParam(employee.getFirstName(), employee.getLastName());

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }
}