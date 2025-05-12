package villanidev.demo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class EmployeeTrainingPk implements Serializable {

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "training_id")
    private Long trainingId;

    public EmployeeTrainingPk(final Long employeeId, final Long trainingId) {
        this.employeeId = employeeId;
        this.trainingId = trainingId;
    }
}
