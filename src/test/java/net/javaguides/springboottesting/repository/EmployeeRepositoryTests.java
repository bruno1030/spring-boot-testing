package net.javaguides.springboottesting.repository;

import net.javaguides.springboottesting.model.Employee;
import net.javaguides.springboottesting.repository.EmployeeRepository;
import static org.assertj.core.api.Assertions.assertThat;
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

    // JUnit test for save employee operation    *** given_when_then ***
    @DisplayName("JUnit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){
        // given - precondition of setup
        Employee employee = Employee.builder()
                .firstName("Bruno")
                .lastName("Martins")
                .email("bruno@bruno")
                .build();

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
        Employee employee1 = Employee.builder()
                .firstName("Bruno")
                .lastName("Martins")
                .email("bruno@bruno")
                .build();

        Employee employee2 = Employee.builder()
                .firstName("Lucas")
                .lastName("Oliveira")
                .email("lucas@lucas")
                .build();

        employeeRepository.save(employee1);
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
        Employee employee1 = Employee.builder()
                .firstName("Bruno")
                .lastName("Martins")
                .email("bruno@bruno")
                .build();
        employeeRepository.save(employee1);

        Employee employee2 = Employee.builder()
                .firstName("Lucas")
                .lastName("Oliveira")
                .email("lucas@lucas")
                .build();
        employeeRepository.save(employee2);

        // when - action or the behaviour that we are going test
        Optional<Employee> employeeDB = employeeRepository.findById(employee1.getId());

        // then - verify the output
        assertThat(employeeDB).isNotNull();
        assertThat(employeeDB.get().getFirstName()).isEqualTo("Bruno");
    }

    // JUnit test for get Employee by email
    @DisplayName("JUnit test for get Employee by email")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployedObject(){
        // given - precondition of setup
        Employee employee1 = Employee.builder()
                .firstName("Bruno")
                .lastName("Martins")
                .email("bruno@bruno")
                .build();
        employeeRepository.save(employee1);

        Employee employee2 = Employee.builder()
                .firstName("Lucas")
                .lastName("Oliveira")
                .email("lucas@lucas")
                .build();
        employeeRepository.save(employee2);

        // when - action or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findByEmail(employee1.getEmail()).get();

        // then - verify the output
        assertThat(employeeDB.getFirstName()).isEqualTo("Bruno");
        assertThat(employeeDB).isNotNull();
    }

    @DisplayName("JUnit test for update Employee")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
        // given - precondition of setup
        Employee employee1 = Employee.builder()
                .firstName("Bruno")
                .lastName("Martins")
                .email("bruno@bruno")
                .build();
        employeeRepository.save(employee1);

        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findById(employee1.getId()).get();
        savedEmployee.setLastName("Oliveira");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        // then - verify the output
        assertThat(updatedEmployee.getLastName()).isEqualTo("Oliveira");
    }

    @DisplayName("JUnit test for test delete an Employee object")
    @Test
    public void givenEmployee_whenDelete_thenRemoveEmployeeAndReturnNullObject(){
        // given - precondition of setup
        Employee employee1 = Employee.builder()
                .firstName("Bruno")
                .lastName("Martins")
                .email("bruno@bruno")
                .build();
        employeeRepository.save(employee1);

        // when - action or the behaviour that we are going test
        employeeRepository.deleteById(employee1.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employee1.getId());

        // then - verify the output
        assertThat(employeeOptional).isEmpty();

    }


    // JUnit test for custom query using JPQL with index
    @DisplayName("JUnit test for custom query using JPQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject(){
        // given - precondition of setup
        Employee employee1 = Employee.builder()
                .firstName("Bruno")
                .lastName("Martins")
                .email("bruno@bruno")
                .build();
        employeeRepository.save(employee1);

        String firstName = "Bruno";
        String lastName = "Martins";

        // when - action or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findByJPQL(firstName, lastName);

        // then - verify the output
        assertThat(employeeDB).isNotNull();
    }

}
