package DB.Phillarmonic.Repositories;

import DB.Phillarmonic.Models.Artist;
import DB.Phillarmonic.Models.Contract;
import DB.Phillarmonic.Models.Impresario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    Iterable<Contract> findByImpresario(Impresario impresario);
    Iterable<Contract> findByArtist(Artist artist);
}
