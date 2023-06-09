package DB.Phillarmonic.Controllers;

import DB.Phillarmonic.Models.Genre;
import DB.Phillarmonic.Repositories.GenreRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("genre")
public class GenreController {
    private final GenreRepository genreRepository;

    public GenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }
    @GetMapping
    public String all(Model model) {
        Iterable<Genre> genres = genreRepository.findAll();
        model.addAttribute("genres", genres);
        return "genre";
    }
    @PostMapping("add")
    public String add(@RequestParam String title,
                      @RequestParam String main_musical_instrument) {
        Genre genre = new Genre(title, main_musical_instrument);
        genreRepository.save(genre);
        return "redirect:/genre";
    }
    @PostMapping("delete/{id}")
    public String delete(@PathVariable(value = "id") Long id) {
        Genre genre = genreRepository.findById(id).orElseThrow();
        genreRepository.delete(genre);
        return "redirect:/genre";
    }
    @GetMapping("{id}")
    public String one(@PathVariable(value = "id") Long id,
                      Model model) {
        Genre genre = genreRepository.findById(id).orElseThrow();
        Iterable<Genre> genres = genreRepository.findAll();
        model.addAttribute("genre", genre);
        model.addAttribute("genres", genres);
        return "genre-edit";
    }
    @PostMapping("{id}")
    public String edit(@PathVariable(value = "id") Long id,
                       @RequestParam String title,
                       @RequestParam String main_musical_instrument) {
        Genre genre = genreRepository.findById(id).orElseThrow();
        genre.setTitle(title);
        genre.setMain_musical_instrument(main_musical_instrument);
        genreRepository.save(genre);
        return "redirect:/genre";
    }
}