package net.javaguides.springboottesting.repository;

import net.javaguides.springboottesting.model.Employee;
import net.javaguides.springboottesting.repository.EmployeeRepository;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setup(){
        employee = Employee.builder()
                .firstName("Bruno")
                .lastName("Oliveira")
                .email("bruno@bruno")
                .build();
    }

    // JUnit test for save employee operation    *** given_when_then ***
    @DisplayName("JUnit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){
        // given - precondition of setup
//        Employee employee = Employee.builder()
//                .firstName("Bruno")
//                .lastName("Martins")
//                .email("bruno@bruno")
//                .build();

        // when - action or the behaviou that we are going test
        Employee savedEmployee = employeeRepository.save(employee);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    // JUnit test for
    @DisplayName("JUnit test for getting a list of employee")
    @Test
    public void givenEmployeeList_whenFindAll_thenEmployeesList(){
        // given - precondition of setup
//        Employee employee1 = Employee.builder()
//                .firstName("Bruno")
//                .lastName("Martins")
//                .email("bruno@bruno")
//                .build();

        Employee employee2 = Employee.builder()
                .firstName("Lucas")
                .lastName("Oliveira")
                .email("lucas@lucas")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee2);

        // when - action or the behaviour that we are going test
        List<Employee> employeeList = employeeRepository.findAll();

        // then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.get(0).getFirstName()).isEqualTo("Bruno");
        assertThat(employeeList.size()).isEqualTo(2);
    }

    // JUnit test for find employee by id
    @DisplayName("JUnit test for find employee by id")
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject(){
        // given - precondition of setup
//        Employee employee1 = Employee.builder()
//                .firstName("Bruno")
//                .lastName("Martins")
//                .email("bruno@bruno")
//                .build();
        employeeRepository.save(employee);

        Employee employee2 = Employee.builder()
                .firstName("Lucas")
                .lastName("Oliveira")
                .email("lucas@lucas")
                .build();
        employeeRepository.save(employee2);

        // when - action or the behaviour that we are going test
        Optional<Employee> employeeDB = employeeRepository.findById(employee.getId());

        // then - verify the output
        assertThat(employeeDB).isNotNull();
        assertThat(employeeDB.get().getFirstName()).isEqualTo("Bruno");
    }

    // JUnit test for get Employee by email
    @DisplayName("JUnit test for get Employee by email")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployedObject(){
        // given - precondition of setup
//        Employee employee1 = Employee.builder()
//                .firstName("Bruno")
//                .lastName("Martins")
//                .email("bruno@bruno")
//                .build();
        employeeRepository.save(employee);

        Employee employee2 = Employee.builder()
                .firstName("Lucas")
                .lastName("Oliveira")
                .email("lucas@lucas")
                .build();
        employeeRepository.save(employee2);

        // when - action or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();

        // then - verify the output
        assertThat(employeeDB.getFirstName()).isEqualTo("Bruno");
        assertThat(employeeDB).isNotNull();
    }

    @DisplayName("JUnit test for update Employee")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
        // given - precondition of setup
//        Employee employee = Employee.builder()
//                .firstName("Bruno")
//                .lastName("Martins")
//                .email("bruno@bruno")
//                .build();
        employeeRepository.save(employee);

        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setLastName("Oliveira");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        // then - verify the output
        assertThat(updatedEmployee.getLastName()).isEqualTo("Oliveira");
    }

    @DisplayName("JUnit test for test delete an Employee object")
    @Test
    public void givenEmployee_whenDelete_thenRemoveEmployeeAndReturnNullObject(){
        // given - precondition of setup
//        Employee employee = Employee.builder()
//                .firstName("Bruno")
//                .lastName("Martins")
//                .email("bruno@bruno")
//                .build();
        employeeRepository.save(employee);

        // when - action or the behaviour that we are going test
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        // then - verify the output
        assertThat(employeeOptional).isEmpty();

    }


    // JUnit test for custom query using JPQL with Index params
    @DisplayName("JUnit test for custom query using JPQL with Index params")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject(){
        // given - precondition of setup
//        Employee employee = Employee.builder()
//                .firstName("Bruno")
//                .lastName("Martins")
//                .email("bruno@bruno")
//                .build();
        employeeRepository.save(employee);

        String firstName = "Bruno";
        String lastName = "Oliveira";

        // when - action or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findByJPQLIndexParams(firstName, lastName);

        // then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    // JUnit test for custom query using JPQL with Named params
    @DisplayName("JUnit test for custom query using JPQL with Named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject(){
        // given - precondition of setup
//        Employee employee = Employee.builder()
//                .firstName("Bruno")
//                .lastName("Martins")
//                .email("bruno@bruno")
//                .build();
        employeeRepository.save(employee);

        String firstName = "Bruno";
        String lastName = "Oliveira";

        // when - action or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findByJPQLNamedParams(firstName, lastName);

        // then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    // JUnit test for custom query using Native SQL with index parameters
    @DisplayName("JUnit test for custom query using Native SQL with index parameters")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQLIndexParams_thenReturnEmployeeObject(){
        // given - precondition of setup
//        Employee employee = Employee.builder()
//                .firstName("Bruno")
//                .lastName("Martins")
//                .email("bruno@bruno")
//                .build();
        employeeRepository.save(employee);

        //String firstName = "Bruno";
        //String lastName = "Martins";

        // when - action or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findByNativeSqlIndexParams(employee.getFirstName(), employee.getLastName());

        // then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    // JUnit test for custom query using Native SQL with Named parameters
    @DisplayName("JUnit test for custom query using Native SQL with Named parameters")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject(){
        // given - precondition of setup
//        Employee employee = Employee.builder()
//                .firstName("Bruno")
//                .lastName("Martins")
//                .email("bruno@bruno")
//                .build();
        employeeRepository.save(employee);

        //String firstName = "Bruno";
        //String lastName = "Martins";

        // when - action or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findByNativeSqlNamedParams(employee.getFirstName(), employee.getLastName());

        // then - verify the output
        assertThat(employeeDB).isNotNull();
    }

}
