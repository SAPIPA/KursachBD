package DB.Phillarmonic.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class GenreArtist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    private int experience;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "fk_genre_id")
    private Genre genre;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "fk_artist_id")
    private Artist artist;
}