package DB.Phillarmonic.Repositories;

import DB.Phillarmonic.Models.Organizer;
import DB.Phillarmonic.Models.OrganizerEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizerEventRepository extends JpaRepository<OrganizerEvent, Long> {
    Iterable<OrganizerEvent> findByOrganizer(Organizer organizer);

}