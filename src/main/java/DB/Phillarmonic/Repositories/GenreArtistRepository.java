package DB.Phillarmonic.Repositories;

import DB.Phillarmonic.Models.Artist;
import DB.Phillarmonic.Models.Genre;
import DB.Phillarmonic.Models.GenreArtist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreArtistRepository extends JpaRepository<GenreArtist, Long> {
    @Query("SELECT ga.artist FROM GenreArtist ga GROUP BY ga.artist HAVING COUNT(DISTINCT ga.genre) > 1")
    Iterable<GenreArtist> findArtistsInMultipleGenres();
    Iterable<GenreArtist> findByGenre(Genre genre);
}
