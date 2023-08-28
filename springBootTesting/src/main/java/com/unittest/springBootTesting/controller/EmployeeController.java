package com.unittest.springBootTesting.controller;

import com.unittest.springBootTesting.model.Employee;
import com.unittest.springBootTesting.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/create")
    @ResponseStatus(CREATED)
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(this.employeeService.saveEmployee(employee), CREATED);
    }

    @GetMapping("/all")
    public List<Employee> getAllEmployees() {
        return this.employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);

        if (Objects.nonNull(employee))
            return new ResponseEntity<>(employee, OK);

        return new ResponseEntity<>(NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long employeeId,
                                                   @RequestBody Employee employee) {
        Employee existingEmployee = employeeService.getEmployeeById(employeeId);
        if (Objects.nonNull(existingEmployee)) {
            existingEmployee.setFirstName(employee.getFirstName());
            existingEmployee.setLastName(employee.getLastName());
            existingEmployee.setEmail(employee.getEmail());
            Employee updatedEmployee = employeeService.updateEmployee(existingEmployee);
            return new ResponseEntity<>(updatedEmployee, OK);
        }
        return new ResponseEntity<>(NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") long employeeId) {
        employeeService.deleteEmployee(employeeId);

        return new ResponseEntity<>(OK);
    }
}
