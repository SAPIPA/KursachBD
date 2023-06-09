package DB.Phillarmonic.Models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    @Column(columnDefinition = "DATE")
    private LocalDate date;
    @NonNull
    private int duration;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "fk_artist_id")
    private Artist artist;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "fk_impresario_id")
    private Impresario impresario;
}