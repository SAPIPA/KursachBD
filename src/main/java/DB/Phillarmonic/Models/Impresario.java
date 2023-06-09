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
public class Impresario {
    @OneToMany(mappedBy = "impresario", cascade = CascadeType.REMOVE)
    private Set<Contract> contracts = new LinkedHashSet<Contract>();
    @OneToMany(mappedBy = "impresario", cascade = CascadeType.REMOVE)
    private Set<Agreement> agreements = new LinkedHashSet<Agreement>();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    private int rating;
    @NonNull
    private int work_experience;
    @NonNull
    @OneToOne
    @JoinColumn(name = "fk_person_id")
    private Person person;
}
