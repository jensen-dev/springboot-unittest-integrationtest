package com.unittest.springBootTesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unittest.springBootTesting.model.Employee;
import com.unittest.springBootTesting.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@WebMvcTest
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

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
        given(employeeService.saveEmployee(any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

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
        employeeList.add(Employee.builder()
                .firstName("jensen")
                .lastName("alimukti")
                .email("jensen.alimukti@gmail.com")
                .build());
        employeeList.add(Employee.builder()
                .firstName("chelia")
                .lastName("kusumo")
                .email("chelia.kusumo@gmail.com")
                .build());
        given(employeeService.getAllEmployees()).willReturn(employeeList);

        // when - action or the behavior that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees/all"));

        // then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()",
                        is(employeeList.size())));
    }

    // positive scenario - valid employee id
    // JUnit test for get employee by id
    @DisplayName("JUnit test for get employee by id")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
        // given - setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("jensen")
                .lastName("alimukti")
                .email("jensen@gmail.com")
                .build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(employee);

        // when - action or the behavior that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

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
        given(employeeService.getEmployeeById(employeeId)).willReturn(null);

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
        long employeeId = 1L;
        Employee existingEmployee = Employee.builder()
                .firstName("jensen")
                .lastName("alimukti")
                .email("jensen@gmail.com")
                .build();
        Employee updatedEmployee = Employee.builder()
                .firstName("jensen")
                .lastName("lim")
                .email("jensen@hmail.com")
                .build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(existingEmployee);
        given(employeeService.updateEmployee(any(Employee.class)))
                .willAnswer((invocation -> invocation.getArgument(0)));

        // when - action or the behavior that we are going to test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        // then - verify the output
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName",
                        is(updatedEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(updatedEmployee.getLastName())))
                .andExpect(jsonPath("$.email",
                        is(updatedEmployee.getEmail())));
    }

    // JUnit test for update employee negative scenario
    @DisplayName("JUnit test for update employee negative scenario")
    @Test
    public void givenEmployee_whenUpdateEmployee_thenReturnEmpty() throws Exception {
        // given - setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("jensen")
                .lastName("lim")
                .email("jensen@hmail.com")
                .build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(null);

        // when - action or the behavior that we are going to test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then - verify the output
        response.andDo(print())
                .andExpect(status().isNotFound());
    }

    // JUnit test for delete employee
    @DisplayName("JUnit test for delete employee")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {
        // given - setup
        long employeeId = 1L;
        willDoNothing().given(employeeService).deleteEmployee(employeeId);

        // when - action or the behavior that we are going to test
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", employeeId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }
}
