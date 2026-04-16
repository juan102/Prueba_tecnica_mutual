package coding.personal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private String nombres;
    private String apellidos;
    private String sexo;
    private int edad;
    private String correoElectronico;
}