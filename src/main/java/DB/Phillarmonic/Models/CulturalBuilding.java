package DB.Phillarmonic.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class CulturalBuilding {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String address;
    @NonNull
    private String type;
    @NonNull
    private int numberOfHalls;
    @NonNull
    private int area;
    @NonNull
    private int screen_size;
}