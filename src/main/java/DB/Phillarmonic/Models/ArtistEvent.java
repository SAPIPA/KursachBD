package DB.Phillarmonic.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class ArtistEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    private boolean prize;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "fk_artist_id")
    private Artist artist;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "fk_culturalEvent_id")
    private CulturalEvent culturalEvent;
}