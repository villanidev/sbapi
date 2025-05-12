package villanidev.demo.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    /* unidirectional */
    @OneToOne
    @JoinColumn(nullable = false, name = "address_id")
    private Address address;

    /* unidirectional */
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    /* turns into a one to many */
    @OneToMany(mappedBy = "employee")
    @Builder.Default
    private Set<TrainingReview> trainingReviews = new HashSet<>();

    @Column(nullable = false, updatable = false)
    @CreationTimestamp(source = SourceType.DB)
    @Setter(value = AccessLevel.NONE)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp(source = SourceType.DB)
    private LocalDateTime updatedAt;

    /* Link the Many-to-Many relationship to maintain consistency */
    public void addTrainingReview(final Training training) {
        TrainingReview trainingReview = new TrainingReview(this, training);
        trainingReviews.add(trainingReview);
        training.getTrainingReviews().add(trainingReview);
    }

    public void removeTrainingReview(final Training training) {
        for (TrainingReview trainingReview : this.trainingReviews) {

            if (trainingReview.getEmployee().equals(this)
                    && trainingReview.getTraining().equals(training)) {

                trainingReview.getEmployee().getTrainingReviews().remove(trainingReview);
                trainingReview.getTraining().getTrainingReviews().remove(trainingReview);

                trainingReview.setEmployee(null);
                trainingReview.setTraining(null);
                break;
            }
        }
    }
}
