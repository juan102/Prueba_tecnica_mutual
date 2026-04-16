package coding.personal.service.impl;

import coding.personal.model.EmployeeDTO;
import coding.personal.entities.Employee;
import coding.personal.exception.BusinessException;
import coding.personal.mapper.EmployeeMapper;
import coding.personal.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .id(1L)
                .nombres("Juan")
                .apellidos("Perez")
                .sexo("M")
                .edad(30)
                .correoElectronico("juan@email.com")
                .build();

        employeeDTO = new EmployeeDTO("Juan", "Perez", "M", 30, "juan@email.com");
    }

    @Test
    void shouldSaveEmployee() {
        // Arrange
        when(employeeRepository.findByCorreoElectronico(anyString())).thenReturn(Optional.empty());
        when(employeeMapper.toEntity(any(EmployeeDTO.class))).thenReturn(employee);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(employeeMapper.toDto(any(Employee.class))).thenReturn(employeeDTO);

        // Act
        EmployeeDTO result = employeeService.saveEmployee(employeeDTO);

        // Assert
        assertNotNull(result);
        assertEquals(employeeDTO.getNombres(), result.getNombres());
        verify(employeeRepository).save(any(Employee.class));
        verify(employeeMapper).toDto(any(Employee.class));
        verify(employeeMapper).toEntity(any(EmployeeDTO.class));
    }

    @Test
    void shouldThrowExceptionWhenSavingEmployeeWithDuplicateEmail() {
        // Arrange
        when(employeeRepository.findByCorreoElectronico(anyString())).thenReturn(Optional.of(employee));

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () ->
                employeeService.saveEmployee(employeeDTO)
        );

        assertEquals("EMAIL_DUPLICATED", exception.getCode());
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void shouldGetEmployeeById() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeMapper.toDto(any(Employee.class))).thenReturn(employeeDTO);

        // Act
        EmployeeDTO result = employeeService.getEmployeeById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(employeeDTO.getNombres(), result.getNombres());
        verify(employeeRepository).findById(1L);
        verify(employeeMapper).toDto(any(Employee.class));
    }

    @Test
    void shouldThrowExceptionWhenEmployeeNotFound() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () ->
                employeeService.getEmployeeById(1L)
        );

        assertEquals("EMPLOYEE_NOT_FOUND", exception.getCode());
    }

    @Test
    void shouldUpdateEmployee() {
        // Arrange
        EmployeeDTO updatedDto = new EmployeeDTO("Carlos", "Lopez", "M", 35, "carlos@email.com");
        Employee updatedEmployee = Employee.builder().id(1L).nombres("Carlos").build();

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);
        when(employeeMapper.toDto(any(Employee.class))).thenReturn(updatedDto);

        // Act
        EmployeeDTO result = employeeService.updateEmployee(1L, updatedDto);

        // Assert
        assertNotNull(result);
        assertEquals("Carlos", result.getNombres());
        verify(employeeRepository).findById(1L);
        verify(employeeRepository).save(any(Employee.class));
        verify(employeeMapper).toDto(any(Employee.class));
    }

    @Test
    void shouldDeleteEmployee() {
        // Arrange
        when(employeeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(employeeRepository).deleteById(1L);

        // Act
        employeeService.deleteEmployee(1L);

        // Assert
        verify(employeeRepository).deleteById(1L);
    }
    
    @Test
    void shouldThrowExceptionWhenDeletingNonExistentEmployee() {
        // Arrange
        when(employeeRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        BusinessException exception = assertThrows(BusinessException.class, () ->
            employeeService.deleteEmployee(1L)
        );
        
        assertEquals("EMPLOYEE_NOT_FOUND", exception.getCode());
        verify(employeeRepository, never()).deleteById(1L);
    }

    @Test
    void shouldGetAllEmployees() {
        // Arrange
        when(employeeRepository.findAll()).thenReturn(List.of(employee));
        when(employeeMapper.toDto(any(Employee.class))).thenReturn(employeeDTO);

        // Act
        List<EmployeeDTO> result = employeeService.getAllEmployees();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(employeeRepository).findAll();
    }
}