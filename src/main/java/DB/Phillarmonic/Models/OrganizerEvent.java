package DB.Phillarmonic.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class OrganizerEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    private int fee;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "fk_organizer_id")
    private Organizer organizer;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "fk_culturalEvent_id")
    private CulturalEvent culturalEvent;
}