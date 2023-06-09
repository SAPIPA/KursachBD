package DB.Phillarmonic.Controllers;

import DB.Phillarmonic.Models.Artist;
import DB.Phillarmonic.Models.Genre;
import DB.Phillarmonic.Models.GenreArtist;
import DB.Phillarmonic.Repositories.ArtistRepository;
import DB.Phillarmonic.Repositories.GenreArtistRepository;
import DB.Phillarmonic.Repositories.GenreRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("genre-artist")
public class GenreArtistController {
    private final GenreArtistRepository genreArtistRepository;
    private final GenreRepository genreRepository;
    private final ArtistRepository artistRepository;
    public GenreArtistController(GenreArtistRepository genreArtistRepository, GenreRepository genreRepository, ArtistRepository artistRepository) {
        this.genreArtistRepository = genreArtistRepository;
        this.genreRepository = genreRepository;
        this.artistRepository = artistRepository;
    }
    @GetMapping
    public String all(Model model) {
        Iterable<GenreArtist> genreArtists = genreArtistRepository.findAll();
        Iterable<Genre> genres = genreRepository.findAll();
        Iterable<Artist> artists = artistRepository.findAll();
        model.addAttribute("genreArtists", genreArtists);
        model.addAttribute("genres", genres);
        model.addAttribute("artists", artists);
        return "genre-artist";
    }
    @PostMapping("add")
    public String add(@RequestParam int experience,
                      @RequestParam Genre genre,
                      @RequestParam Artist artist) {
        GenreArtist genreArtist = new GenreArtist(experience, genre, artist);
        genreArtistRepository.save(genreArtist);
        return "redirect:/genre-artist";
    }
    @PostMapping("delete/{id}")
    public String delete(@PathVariable(value = "id") Long id) {
        GenreArtist genreArtist = genreArtistRepository.findById(id).orElseThrow();
        genreArtistRepository.delete(genreArtist);
        return "redirect:/genre-artist";
    }
    @GetMapping("{id}")
    public String one(@PathVariable(value = "id") Long id,
                      Model model) {
        GenreArtist genreArtist = genreArtistRepository.findById(id).orElseThrow();
        Iterable<Genre> genres = genreRepository.findAll();
        Iterable<Artist> artists = artistRepository.findAll();
        model.addAttribute("genreArtist", genreArtist);
        model.addAttribute("genres", genres);
        model.addAttribute("artists", artists);
        return "genre-artist-edit";
    }
    @PostMapping("{id}")
    public String edit(@PathVariable(value = "id") Long id,
                       @RequestParam int experience,
                       @RequestParam Genre genre,
                       @RequestParam Artist artist) {
        GenreArtist genreArtist = genreArtistRepository.findById(id).orElseThrow();
        genreArtist.setExperience(experience);
        genreArtist.setGenre(genre);
        genreArtist.setArtist(artist);
        genreArtistRepository.save(genreArtist);
        return "redirect:/genre-artist";
    }
    @GetMapping("/filter")
    public String filter(@RequestParam(required = false) Genre genre,
                         Model model) {
        Iterable<GenreArtist> genreArtists = genreArtistRepository.findAll();
        Iterable<Genre> genres = genreRepository.findAll();
        Iterable<Artist> artists = artistRepository.findAll();
        if (genre != null) {
            genreArtists = genreArtistRepository.findByGenre(genre);
        }
        model.addAttribute("genres", genres);
        model.addAttribute("artists", artists);
        model.addAttribute("genreArtists", genreArtists);

        return "genre-artist";
    }
    @GetMapping("/many")
    public String many(Model model) {
        Iterable<GenreArtist> genreArtists = genreArtistRepository.findArtistsInMultipleGenres();
        Iterable<Genre> genres = genreRepository.findAll();
        Iterable<Artist> artists = artistRepository.findAll();

        model.addAttribute("genres", genres);
        model.addAttribute("artists", artists);
        model.addAttribute("genreArtists", genreArtists);

        return "genre-artist";
    }
}