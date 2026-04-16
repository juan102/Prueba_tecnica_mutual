package coding.personal.service;

import coding.personal.model.EmployeeDTO;


import java.util.List;

public interface IemployeeService {
    EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO getEmployeeById(Long id);
    EmployeeDTO updateEmployee(Long id, EmployeeDTO dto);
    void deleteEmployee(Long id);
    List<EmployeeDTO> getAllEmployees();
    List<EmployeeDTO> getEmployeesAgeGreaterOrEqual40();
    List<EmployeeDTO> getFemaleEmployees();
}