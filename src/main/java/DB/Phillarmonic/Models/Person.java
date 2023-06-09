package DB.Phillarmonic.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Person {
    @OneToOne(mappedBy = "person", cascade = CascadeType.REMOVE)
    private Artist artist;
    @OneToOne(mappedBy = "person", cascade = CascadeType.REMOVE)
    private Impresario impresario;
    @OneToOne(mappedBy = "person", cascade = CascadeType.REMOVE)
    private Organizer organizer;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String surname;
    @NonNull
    private String patronymic;
    @NonNull
    private String email;
}