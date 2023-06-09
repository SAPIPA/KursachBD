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
public class Organizer {
    @OneToMany(mappedBy = "organizer", cascade = CascadeType.REMOVE)
    private Set<Agreement> agreements = new LinkedHashSet<Agreement>();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    private boolean logistics_specialist;
    @NonNull
    private String face;
    @NonNull
    @OneToOne
    @JoinColumn(name = "fk_person_id")
    private Person person;
}