package com.unittest.springBootTesting.service;

import com.unittest.springBootTesting.exception.ResourceNotFoundException;
import com.unittest.springBootTesting.model.Employee;
import com.unittest.springBootTesting.repository.EmployeeRepository;
import com.unittest.springBootTesting.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup() {
//        employeeRepository = Mockito.mock(EmployeeRepository.class);
//        employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder()
                .id(1L)
                .firstName("jensen")
                .lastName("alimukti")
                .email("jensen@gmail.com")
                .build();
    }

    // JUnit test for save employee method
    @DisplayName("JUnit test for save employee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        // given - setup
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

            // when - action or the behavior that we are going to test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // JUnit test for save employee method throw exception
    @DisplayName("JUnit test for save employee method throw exception")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenThrowsException() {
        // given - setup
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

        // when - action or the behavior that we are going to test
        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.saveEmployee(employee);
        });

        // then - verify the output
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    // JUnit test for get all employees method
    @DisplayName("JUnit test for get all employees method")
    @Test
    public void givenEmployeeList_whenGetAllEmployees_thenReturnEmployeeList() {
        // given - setup
        given(employeeRepository.findAll()).willReturn(List.of(employee));

        // when - action or the behavior that we are going to test
        List<Employee> employeeList = employeeService.getAllEmployees();

        // then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(1);
    }

    // JUnit test for get all employees method (negative scenario)
    @DisplayName("JUnit test for get all employees method (negative scenario)")
    @Test
    public void givenEmptyEmployeeList_whenGetAllEmployees_thenReturnEmptyEmployeeList() {
        // given - setup
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        // when - action or the behavior that we are going to test
        List<Employee> employeeList = employeeService.getAllEmployees();

        // then - verify the output
        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);
    }

    // JUnit test for get employee by id
    @DisplayName("JUnit test for get employee by id")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
        // given - setup
        long employeeId = 1L;
        given(employeeRepository.findById(employeeId)).willReturn(Optional.of(employee));

        // when - action or the behavior that we are going to test
        Employee existingEmployee = employeeService.getEmployeeById(employeeId);

        // then - verify the output
        assertThat(existingEmployee).isNotNull();
    }

    // JUnit test for get employee by id negative scenario
    @DisplayName("JUnit test for get employee by id negative scenario")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmptyEmployeeObject() {
        // given - setup
        long employeeId = 1L;
        given(employeeRepository.findById(employeeId)).willReturn(Optional.empty());

        // when - action or the behavior that we are going to test
        Employee existingEmployee = employeeService.getEmployeeById(employeeId);

        // then - verify the output
        assertThat(existingEmployee).isNull();
    }

    // JUnit test for update employee
    @DisplayName("JUnit test for update employee")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        // given - setup
        employee.setEmail("mail@mail.co");
        employee.setFirstName("first");
        given(employeeRepository.save(employee)).willReturn(employee);

        // when - action or the behavior that we are going to test
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        // then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("mail@mail.co");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("first");
    }

    // JUnit test for delete employee
    @DisplayName("JUnit test for delete employee")
    @Test
    public void given_when_then() {
        // given - setup
        long employeeId = 1L;
        willDoNothing().given(employeeRepository).deleteById(employeeId);

        // when - action or the behavior that we are going to test
        employeeService.deleteEmployee(employeeId);

        // then - verify the output
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }
}
