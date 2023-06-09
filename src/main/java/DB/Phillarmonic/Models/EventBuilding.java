package DB.Phillarmonic.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class EventBuilding {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    @Column(columnDefinition = "DATE")
    private LocalDate date;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "fk_culturalBuilding_id")
    private CulturalBuilding culturalBuilding;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "fk_culturalEvent_id")
    private CulturalEvent culturalEvent;
}