package DB.Phillarmonic.Repositories;

import DB.Phillarmonic.Models.CulturalEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CulturalEventRepository extends JpaRepository<CulturalEvent, Long> {
}
