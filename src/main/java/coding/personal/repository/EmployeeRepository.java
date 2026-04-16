package coding.personal.repository;

import coding.personal.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByCorreoElectronico(String correoElectronico);

    List<Employee> findByEdadGreaterThanEqual(int edad);

    List<Employee> findBySexo(String sexo);
}