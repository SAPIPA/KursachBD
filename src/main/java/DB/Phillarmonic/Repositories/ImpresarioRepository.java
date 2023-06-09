package DB.Phillarmonic.Repositories;

import DB.Phillarmonic.Models.Impresario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImpresarioRepository extends JpaRepository<Impresario, Long> {
}
