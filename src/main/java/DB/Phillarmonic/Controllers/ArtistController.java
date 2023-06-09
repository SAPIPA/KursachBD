package DB.Phillarmonic.Controllers;

import DB.Phillarmonic.Models.*;
import DB.Phillarmonic.Repositories.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("artist")
public class ArtistController {
    private final ArtistRepository artistRepository;
    private final ArtistEventRepository artistEventRepository;
    private final EventBuildingRepository eventBuildingRepository;
    private final ImpresarioRepository impresarioRepository;
    private final OrganizerRepository organizerRepository;
    private final PersonRepository personRepository;
    public ArtistController(ArtistRepository artistRepository, ArtistEventRepository artistEventRepository, EventBuildingRepository eventBuildingRepository, ImpresarioRepository impresarioRepository, OrganizerRepository organizerRepository, PersonRepository personRepository) {
        this.artistRepository = artistRepository;
        this.artistEventRepository = artistEventRepository;
        this.eventBuildingRepository = eventBuildingRepository;
        this.impresarioRepository = impresarioRepository;
        this.organizerRepository = organizerRepository;
        this.personRepository = personRepository;
    }
    @GetMapping
    public String all(Model model) {
        Iterable<Artist> artists = artistRepository.findAll();
        Iterable<Person> people = personRepository.findAll();

        Set<Person> usedPeople = new HashSet<>();
        for (Artist artist : artists) {
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

        model.addAttribute("artists", artists);
        model.addAttribute("people", unusedPeople);
        return "artist";
    }
    @PostMapping("add")
    public String add(@RequestParam(defaultValue = "false") boolean national_artist,
                      @RequestParam String foreign_language,
                      @RequestParam String favorite_musical_instrument,
                      @RequestParam Person person) {
        Artist artist = new Artist(national_artist, foreign_language, favorite_musical_instrument, person);
        artistRepository.save(artist);
        return "redirect:/artist";
    }
    @PostMapping("delete/{id}")
    public String delete(@PathVariable(value = "id") Long id) {
        Artist artist = artistRepository.findById(id).orElseThrow();
        artistRepository.delete(artist);
        return "redirect:/artist";
    }
    @GetMapping("{id}")
    public String one(@PathVariable(value = "id") Long id,
                       Model model) {
        Artist artist = artistRepository.findById(id).orElseThrow();
        Iterable<Person> people = personRepository.findAll();
        Iterable<Artist> artists = artistRepository.findAll();

        Set<Person> usedPeople = new HashSet<>();
        for (Artist artist1 : artists) {
            usedPeople.add(artist1.getPerson());
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

        model.addAttribute("people", unusedPeople);
        model.addAttribute("artist", artist);
        return "artist-edit";
    }
    @PostMapping("{id}")
    public String edit(@PathVariable(value = "id") Long id,
                       @RequestParam(defaultValue = "false") boolean national_artist,
                       @RequestParam String foreign_language,
                       @RequestParam String favorite_musical_instrument,
                       @RequestParam Person person) {
        Artist artist = artistRepository.findById(id).orElseThrow();
        artist.setNational_artist(national_artist);
        artist.setForeign_language(foreign_language);
        artist.setFavorite_musical_instrument(favorite_musical_instrument);
        artist.setPerson(person);
        artistRepository.save(artist);
        return "redirect:/artist";
    }
    @GetMapping("/filter")
    public String filter(@RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate minDate,
                         @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate maxDate,
                         Model model) {
        Iterable<Artist> artists = artistRepository.findAll();
        Iterable<Person> people = personRepository.findAll();
        Iterable<EventBuilding> eventBuildings = eventBuildingRepository.findByDateBetween(minDate, maxDate);

        if(minDate != null && maxDate != null) {
            artists = new ArrayList<>();

            for (EventBuilding eventBuilding : eventBuildings) {
                Iterable<ArtistEvent> artistEvents = artistEventRepository.findByCulturalEventAndPrizeIsFalse(eventBuilding.getCulturalEvent());

                for(ArtistEvent artistEvent : artistEvents) {
                    Artist artist = artistEvent.getArtist();
                    ((ArrayList<Artist>) artists).add(artist);
                }
            }
        }

        Set<Person> usedPeople = new HashSet<>();
        for (Artist artist : artists) {
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

        model.addAttribute("artists", artists);
        model.addAttribute("people", unusedPeople);
        return "artist";
    }
}