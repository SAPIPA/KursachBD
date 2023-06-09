package DB.Phillarmonic.Repositories;

import DB.Phillarmonic.Models.ArtistEvent;
import DB.Phillarmonic.Models.CulturalEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistEventRepository extends JpaRepository<ArtistEvent, Long> {
    Iterable<ArtistEvent> findByCulturalEventAndPrizeIsTrue(CulturalEvent culturalEvent);
    Iterable<ArtistEvent> findByCulturalEventAndPrizeIsFalse(CulturalEvent culturalEvent);
}