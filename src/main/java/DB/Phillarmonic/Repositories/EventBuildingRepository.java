package DB.Phillarmonic.Repositories;

import DB.Phillarmonic.Models.CulturalBuilding;
import DB.Phillarmonic.Models.EventBuilding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface EventBuildingRepository extends JpaRepository<EventBuilding, Long> {
    Iterable<EventBuilding> findByDateBetween(LocalDate minDate, LocalDate maxDate);
    Iterable<EventBuilding> findByCulturalBuilding(CulturalBuilding culturalBuilding);
}
