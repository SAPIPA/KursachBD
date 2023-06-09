package DB.Phillarmonic.Repositories;

import DB.Phillarmonic.Models.CulturalBuilding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CulturalBuildingRepository extends JpaRepository<CulturalBuilding, Long> {
    Iterable<CulturalBuilding> findByType(String type);
    Iterable<CulturalBuilding> findByNumberOfHallsGreaterThanEqual(int number_of_halls);
}
