package com.unittest.springBootTesting.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unittest.springBootTesting.model.Employee;
import com.unittest.springBootTesting.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        employeeRepository.deleteAll();
    }

    // JUnit test for create employee
    @DisplayName("JUnit test for create employee")
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        // given - setup
        Employee employee = Employee.builder()
                .firstName("jensen")
                .lastName("alimukti")
                .email("jensen@gmail.com")
                .build();

        // when - action or the behavior that we are going to test
        ResultActions response = mockMvc.perform(post("/api/employees/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then - verify the output
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName",
                        is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(employee.getLastName())))
                .andExpect(jsonPath("$.email",
                        is(employee.getEmail())));
    }

    // JUnit test for get all employees
    @DisplayName("JUnit test for get all employees")
    @Test
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeeList() throws Exception {
        // given - setup
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(Employee.builder().firstName("jensen").lastName("alimukti").email("jensen.alimukti@gmail.com").build());
        employeeList.add(Employee.builder().firstName("chelia").lastName("kusumo").email("chelia.kusumo@gmail.com").build());
        employeeRepository.saveAll(employeeList);

        // when - action or the behavior that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees/all"));

        // then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(employeeList.size())));
    }

    // JUnit test for get employee by id
    @DisplayName("JUnit test for get employee by id")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
        // given - setup
        Employee employee = Employee.builder().firstName("jensen").lastName("alimukti").email("jensen@gmail.com").build();
        employeeRepository.save(employee);

        // when - action or the behavior that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employee.getId()));

        // then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    // negative scenario - valid employee id
    // JUnit test for get employee by id
    @DisplayName("JUnit test for get employee by id")
    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception {
        // given - setup
        long employeeId = 1L;
        Employee employee = Employee.builder().firstName("jensen").lastName("alimukti").email("jensen@gmail.com").build();
        employeeRepository.save(employee);

        // when - action or the behavior that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

        // then - verify the output
        response.andDo(print())
                .andExpect(status().is4xxClientError());
    }

    // JUnit test for update employee positive scenario
    @DisplayName("JUnit test for update employee positive scenario")
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee() throws Exception {
        // given - setup
        Employee existingEmployee = Employee.builder().firstName("jensen").lastName("alimukti").email("jensen@gmail.com").build();
        employeeRepository.save(existingEmployee);
        Employee updatedEmployee = Employee.builder().firstName("jensen").lastName("lim").email("jensen@hmail.com").build();

        // when - action or the behavior that we are going to test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", existingEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        // then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())))
                .andExpect(jsonPath("$.email", is(updatedEmployee.getEmail())));
    }

    // JUnit test for update employee negative scenario
    @DisplayName("JUnit test for update employee negative scenario")
    @Test
    public void givenEmployee_whenUpdateEmployee_thenReturnEmpty() throws Exception {
        // given - setup
        long employeeId = 1L;
        Employee employee = Employee.builder().firstName("jensen").lastName("lim").email("jensen@hmail.com").build();
        employeeRepository.save(employee);

        // when - action or the behavior that we are going to test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then - verify the output
        response.andDo(print()).andExpect(status().isNotFound());
    }

    // JUnit test for delete employee
    @DisplayName("JUnit test for delete employee")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {
        // given - setup
        Employee employee = Employee.builder().firstName("jensen").lastName("lim").email("jensen@hmail.com").build();
        employeeRepository.save(employee);

        // when - action or the behavior that we are going to test
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", employee.getId()));

        // then - verify the output
        response.andExpect(status().isOk()).andDo(print());
    }
}
