package coding.personal.controller;

import coding.personal.model.EmployeeDTO;
import coding.personal.entities.Employee;
import coding.personal.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;

    @PostMapping
    public ResponseEntity<EmployeeDTO> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeServiceImpl.saveEmployee(employeeDTO));
    }


    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeServiceImpl.getEmployeeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id,
                                                   @RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeServiceImpl.updateEmployee(id, employeeDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeServiceImpl.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeServiceImpl.getAllEmployees());
    }

    @GetMapping("/age/40")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesAgeGreaterOrEqual40() {
        return ResponseEntity.ok(employeeServiceImpl.getEmployeesAgeGreaterOrEqual40());
    }

    @GetMapping("/female")
    public ResponseEntity<List<EmployeeDTO>> getFemaleEmployees() {
        return ResponseEntity.ok(employeeServiceImpl.getFemaleEmployees());
    }
}
