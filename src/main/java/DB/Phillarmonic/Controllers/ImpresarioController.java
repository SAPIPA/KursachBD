package DB.Phillarmonic.Controllers;

import DB.Phillarmonic.Models.*;
import DB.Phillarmonic.Repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("impresario")
public class ImpresarioController {
    private final ContractRepository contractRepository;
    private final GenreRepository genreRepository;
    private final GenreArtistRepository genreArtistRepository;
    private final ImpresarioRepository impresarioRepository;
    private final OrganizerRepository organizerRepository;
    private final ArtistRepository artistRepository;
    private final PersonRepository personRepository;
    public ImpresarioController(ContractRepository contractRepository, GenreRepository genreRepository, GenreArtistRepository genreArtistRepository, ImpresarioRepository impresarioRepository, OrganizerRepository organizerRepository, ArtistRepository artistRepository, PersonRepository personRepository) {
        this.contractRepository = contractRepository;
        this.genreRepository = genreRepository;
        this.genreArtistRepository = genreArtistRepository;
        this.impresarioRepository = impresarioRepository;
        this.organizerRepository = organizerRepository;
        this.artistRepository = artistRepository;
        this.personRepository = personRepository;
    }
    @GetMapping
    public String all(Model model) {
        Iterable<Impresario> impresarios = impresarioRepository.findAll();
        Iterable<Person> people = personRepository.findAll();
        Iterable<Genre> genres = genreRepository.findAll();

        Set<Person> usedPeople = new HashSet<>();
        for (Artist artist : artistRepository.findAll()) {
            usedPeople.add(artist.getPerson());
        }
        for (Impresario impresario : impresarioRepository.findAll()) {
            usedPeople.add(impresario.getPerson());
        }
        for (Organizer organizer : organizerRepository.findAll()) {
            usedPeople.add(organizer.getPerson());
        }

        List<Person> unusedPeople = new ArrayList<>();
        for (Person person : people) {
            if (!usedPeople.contains(person)) {
                unusedPeople.add(person);
            }
        }

        model.addAttribute("genres", genres);
        model.addAttribute("impresarios", impresarios);
        model.addAttribute("people", unusedPeople);
        return "impresario";
    }
    @PostMapping("add")
    public String add(@RequestParam int rating,
                      @RequestParam int work_experience,
                      @RequestParam Person person) {
        Impresario impresario = new Impresario(rating, work_experience, person);
        impresarioRepository.save(impresario);
        return "redirect:/impresario";
    }
    @PostMapping("delete/{id}")
    public String delete(@PathVariable(value = "id") Long id) {
        Impresario impresario = impresarioRepository.findById(id).orElseThrow();
        impresarioRepository.delete(impresario);
        return "redirect:/impresario";
    }
    @GetMapping("{id}")
    public String one(@PathVariable(value = "id") Long id,
                      Model model) {
        Impresario impresario = impresarioRepository.findById(id).orElseThrow();
        Iterable<Person> people = personRepository.findAll();

        Set<Person> usedPeople = new HashSet<>();
        for (Artist artist : artistRepository.findAll()) {
            usedPeople.add(artist.getPerson());
        }
        for (Impresario impresario1 : impresarioRepository.findAll()) {
            usedPeople.add(impresario1.getPerson());
        }
        for (Organizer organizer : organizerRepository.findAll()) {
            usedPeople.add(organizer.getPerson());
        }

        List<Person> unusedPeople = new ArrayList<>();
        for (Person person : people) {
            if (!usedPeople.contains(person)) {
                unusedPeople.add(person);
            }
        }

        model.addAttribute("people", unusedPeople);
        model.addAttribute("impresario", impresario);
        return "impresario-edit";
    }
    @PostMapping("{id}")
    public String edit(@PathVariable(value = "id") Long id,
                       @RequestParam int rating,
                       @RequestParam int work_experience,
                       @RequestParam Person person) {
        Impresario impresario = impresarioRepository.findById(id).orElseThrow();
        impresario.setRating(rating);
        impresario.setWork_experience(work_experience);
        impresario.setPerson(person);
        impresarioRepository.save(impresario);
        return "redirect:/impresario";
    }
    @GetMapping("/filter")
    public String filter(@RequestParam(required = false) Genre genre,
                         Model model) {
        Iterable<GenreArtist> genreArtists = genreArtistRepository.findByGenre(genre);
        Iterable<Impresario> impresarios = impresarioRepository.findAll();
        if(genre != null) {
            impresarios = new ArrayList<>();

            for (GenreArtist genreArtist : genreArtists) {
                Iterable<Contract> contracts = contractRepository.findByArtist(genreArtist.getArtist());

                for (Contract contract : contracts) {
                    Impresario impresario = contract.getImpresario();
                    ((ArrayList<Impresario>) impresarios).add(impresario);
                }
            }
        }

        Iterable<Person> people = personRepository.findAll();
        Iterable<Genre> genres = genreRepository.findAll();


        Set<Person> usedPeople = new HashSet<>();
        for (Artist artist : artistRepository.findAll()) {
            usedPeople.add(artist.getPerson());
        }
        for (Impresario impresario : impresarioRepository.findAll()) {
            usedPeople.add(impresario.getPerson());
        }
        for (Organizer organizer : organizerRepository.findAll()) {
            usedPeople.add(organizer.getPerson());
        }

        List<Person> unusedPeople = new ArrayList<>();
        for (Person person : people) {
            if (!usedPeople.contains(person)) {
                unusedPeople.add(person);
            }
        }

        model.addAttribute("genres", genres);
        model.addAttribute("impresarios", impresarios);
        model.addAttribute("people", unusedPeople);
        return "impresario";
    }
}
