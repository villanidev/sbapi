package villanidev.demo.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_training_reviews")
public class TrainingReview {

    @EmbeddedId
    private EmployeeTrainingPk id;

    @ManyToOne
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @MapsId("trainingId")
    @JoinColumn(name = "training_id")
    private Training training;

    private String comment;

    private Integer rating;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp(source = SourceType.DB)
    @Setter(value = AccessLevel.NONE)
    private LocalDateTime attendedAt;

    public TrainingReview(final Employee employee, final Training training) {
        this.employee = employee;
        this.training = training;
        this.id = new EmployeeTrainingPk(employee.getId(), training.getId());
    }
}
