package coding.personal.service.impl;

import coding.personal.entities.Employee;
import coding.personal.model.EmployeeDTO;
import coding.personal.exception.BusinessException;
import coding.personal.mapper.EmployeeMapper;
import coding.personal.repository.EmployeeRepository;
import coding.personal.service.IemployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements IemployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        employeeRepository.findByCorreoElectronico(employeeDTO.getCorreoElectronico()).ifPresent(e -> {
            throw new BusinessException("EMAIL_DUPLICATED", "El correo electrónico ya está en uso.");
        });

        Employee employee = employeeMapper.toEntity(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.toDto(savedEmployee);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new BusinessException("EMPLOYEE_NOT_FOUND", "Empleado no encontrado"));
        return employeeMapper.toDto(employee);
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO dto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new BusinessException("EMPLOYEE_NOT_FOUND", "Empleado no encontrado"));

        employee.setNombres(dto.getNombres());
        employee.setApellidos(dto.getApellidos());
        employee.setSexo(dto.getSexo());
        employee.setEdad(dto.getEdad());
        employee.setCorreoElectronico(dto.getCorreoElectronico());

        Employee updatedEmployee = employeeRepository.save(employee);
        return employeeMapper.toDto(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new BusinessException("EMPLOYEE_NOT_FOUND", "Empleado no encontrado para eliminar.");
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .limit(30)
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> getEmployeesAgeGreaterOrEqual40() {
        return employeeRepository.findByEdadGreaterThanEqual(40)
                .stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeDTO> getFemaleEmployees() {
        return employeeRepository.findBySexo("F")
                .stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }
}