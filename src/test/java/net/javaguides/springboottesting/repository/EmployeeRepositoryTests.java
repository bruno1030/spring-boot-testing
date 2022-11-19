package net.javaguides.springboottesting.repository;

import net.javaguides.springboottesting.model.Employee;
import net.javaguides.springboottesting.repository.EmployeeRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

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


}
