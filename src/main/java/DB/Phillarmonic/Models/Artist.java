package DB.Phillarmonic.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Artist {
    @OneToMany(mappedBy = "artist", cascade = CascadeType.REMOVE)
    private Set<Contract> contracts = new LinkedHashSet<Contract>();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    private boolean national_artist;
    @NonNull
    private String foreign_language;
    @NonNull
    private String favorite_musical_instrument;
    @NonNull
    @OneToOne
    @JoinColumn(name = "fk_person_id")
    private Person person;
}