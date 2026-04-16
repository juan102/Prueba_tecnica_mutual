package coding.personal.mapper;

import coding.personal.entities.Employee;
import coding.personal.model.EmployeeDTO;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeDTO toDto(Employee employee) {
        if (employee == null) {
            return null;
        }
        return new EmployeeDTO(
                employee.getNombres(),
                employee.getApellidos(),
                employee.getSexo(),
                employee.getEdad(),
                employee.getCorreoElectronico()
        );
    }

    public Employee toEntity(EmployeeDTO dto) {
        if (dto == null) {
            return null;
        }
        return Employee.builder()
                .nombres(dto.getNombres())
                .apellidos(dto.getApellidos())
                .sexo(dto.getSexo())
                .edad(dto.getEdad())
                .correoElectronico(dto.getCorreoElectronico())
                .build();
    }
}