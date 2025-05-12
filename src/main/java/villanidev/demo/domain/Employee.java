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

    /* owner of the relationship (bidirectional) */
    @ManyToMany
    @JoinTable(
            name = "enrolment",
            joinColumns = { @JoinColumn(name = "employee_id") },
            inverseJoinColumns = { @JoinColumn(name = "training_id") }
    )
    @Builder.Default
    @Setter(value = AccessLevel.NONE)
    private Set<Training> trainings = new HashSet<>();

    @Column(nullable = false, updatable = false)
    @CreationTimestamp(source = SourceType.DB)
    @Setter(value = AccessLevel.NONE)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp(source = SourceType.DB)
    private LocalDateTime updatedAt;

    /* Link the Many-to-Many relationship to maintain consistency */
    public void addTraining(final Training training) {
        this.trainings.add(training);
        training.getEmployees().add(this);
    }

    public void removeTraining(final Training training) {
        this.trainings.remove(training);
        training.getEmployees().remove(this);
    }

}
