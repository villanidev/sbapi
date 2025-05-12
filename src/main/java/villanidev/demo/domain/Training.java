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
@Table(name = "tb_trainings")
public class Training {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private LocalDateTime period;

    private Boolean mandatory;

    @ManyToMany(mappedBy = "trainings")
    @Builder.Default
    @Setter(value = AccessLevel.NONE)
    private Set<Employee> employees = new HashSet<>();

    @Column(nullable = false, updatable = false)
    @CreationTimestamp(source = SourceType.DB)
    @Setter(value = AccessLevel.NONE)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp(source = SourceType.DB)
    private LocalDateTime updatedAt;
}
