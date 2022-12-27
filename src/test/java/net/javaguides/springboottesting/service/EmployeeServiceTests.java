package net.javaguides.springboottesting.service;

import net.javaguides.springboottesting.exception.ResourceNotFoundException;
import net.javaguides.springboottesting.model.Employee;
import net.javaguides.springboottesting.repository.EmployeeRepository;
import net.javaguides.springboottesting.service.impl.EmployeeServiceImpl;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup(){
        employee = Employee.builder()
                .id(1L)
                .firstName("Bruno")
                .lastName("Martins")
                .email("bruno@bruno")
                .build();
    }

    // JUnit test for saveEmployee method
    @DisplayName("JUnit test for saveEmployee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject(){
        // given - precondition of setup

        // returning empty so then it will be possible to test the saveEmployee method
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());

        //testing the save method
        given(employeeRepository.save(employee))
                .willReturn(employee);

        // when - action or the behaviour that we are going test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // then - verify the output
        assertThat(savedEmployee.getFirstName()).isEqualTo("Bruno");
    }

    // JUnit test for saveEmployee method
    @DisplayName("JUnit test for saveEmployee method which throws exception")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException(){
        // given - precondition of setup
        // returning empty so then it will be possible to test the saveEmployee method
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        //testing the save method ... nesse caso nao preciso testar o save pois o findByEmail retornou um employee, portanto nao irei salvar novamente no banco com o mesmo email, irei apenas jogar a excecao
        //given(employeeRepository.save(employee)).willReturn(employee);

        // when - action or the behaviour that we are going test
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.saveEmployee(employee);
        });

        // then
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    // JUnit test for testing getAllEmployees method - positive scenario
    @DisplayName("JUnit test for testing getAllEmployees method - positive scenario")
    @Test
    public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList(){
        // given - precondition of setup

        Employee employee1 = Employee.builder()
                .id(1L)
                .firstName("Lucas")
                .lastName("Oliveira")
                .email("lucas@lucas")
                .build();

        given(employeeRepository.findAll())
                .willReturn(List.of(employee, employee1));

        // when - action or the behaviour that we are going test

        List<Employee> employeeListReturned = employeeService.getAllEmployees();

        // then - verify the output
        assertThat(employeeListReturned.size()).isEqualTo(2);
    }

    // JUnit test for testing getAllEmployees method - negative scenario
    @DisplayName("JUnit test for testing getAllEmployees method - negative scenario")
    @Test
    public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList(){
        // given - precondition of setup

        Employee employee1 = Employee.builder()
                .id(1L)
                .firstName("Lucas")
                .lastName("Oliveira")
                .email("lucas@lucas")
                .build();

        given(employeeRepository.findAll())
                .willReturn(Collections.emptyList());

        // when - action or the behaviour that we are going test

        List<Employee> employeeListReturned = employeeService.getAllEmployees();

        // then - verify the output
        assertThat(employeeListReturned).isEmpty();
        assertThat(employeeListReturned.size()).isEqualTo(0);
    }

    // JUnit test for get employee by id
    @Test
    @DisplayName("JUnit test for get employee by id")
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject(){
        //given
        given(employeeRepository.findById(1L))
                .willReturn(Optional.of(employee));

        //when
        Employee employeeReturned = employeeService.getEmployeeById(employee.getId()).get();

        //then
        assertThat(employeeReturned.getFirstName()).isEqualTo("Bruno");

    }

    // JUnit test for updateEmployee method
    @Test
    @DisplayName("JUnit test for updateEmployee method")
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
        // given - precondition of setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("martins@martins.com");

        // when - action or the behaviour that we are going test
        Employee employeeUpdated = employeeService.updateEmployee(employee);

        // then - verify the output
        assertThat(employeeUpdated.getEmail()).isEqualTo("martins@martins.com");
    }

}
