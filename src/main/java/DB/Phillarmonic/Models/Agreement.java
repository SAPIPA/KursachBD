package DB.Phillarmonic.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Agreement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    @Column(columnDefinition = "DATE")
    private LocalDate date;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "fk_impresario_id")
    private Impresario impresario;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "fk_organizer_id")
    private Organizer organizer;
}
