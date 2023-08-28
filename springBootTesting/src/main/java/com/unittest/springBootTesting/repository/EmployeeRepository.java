package com.unittest.springBootTesting.repository;

import com.unittest.springBootTesting.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    // define custom query using JPQL with index parameters
    @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
    Employee findByJPQL(String firstName, String lastName);

    // define custom query using JPQL with named parameters
    @Query("select e from Employee e where e.firstName =:firstName and e.lastName =:lastName")
    Employee findByJPQLNamedParams(@Param("firstName") String firstName, @Param("lastName") String lastName);

    // define custom query using native sql query with index param
    @Query(value = "select * from employees e where e.first_name =?1 and e.last_name =?2", nativeQuery = true)
    Employee findByNativeSqlQuery(String firstName, String lastName);

    // define custom query using native sql query with named param
    @Query(value = "select * from employees e where e.first_name =:firstName and e.last_name =:lastName",
            nativeQuery = true)
    Employee findByNativeSqlQueryNamedParam(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
