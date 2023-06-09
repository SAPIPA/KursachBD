package DB.Phillarmonic.Controllers;

import DB.Phillarmonic.Models.Artist;
import DB.Phillarmonic.Models.Impresario;
import DB.Phillarmonic.Models.Organizer;
import DB.Phillarmonic.Models.Person;
import DB.Phillarmonic.Repositories.ArtistRepository;
import DB.Phillarmonic.Repositories.ImpresarioRepository;
import DB.Phillarmonic.Repositories.OrganizerRepository;
import DB.Phillarmonic.Repositories.PersonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("organizer")
public class OrganizerController {
    private final OrganizerRepository organizerRepository;
    private final ImpresarioRepository impresarioRepository;
    private final ArtistRepository artistRepository;
    private final PersonRepository personRepository;

    public OrganizerController(OrganizerRepository organizerRepository, ImpresarioRepository impresarioRepository, ArtistRepository artistRepository, PersonRepository personRepository) {
        this.organizerRepository = organizerRepository;
        this.impresarioRepository = impresarioRepository;
        this.artistRepository = artistRepository;
        this.personRepository = personRepository;
    }
    @GetMapping
    public String all(Model model) {
        Iterable<Organizer> organizers = organizerRepository.findAll();
        Iterable<Person> people = personRepository.findAll();

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

        model.addAttribute("organizers",organizers);
        model.addAttribute("people", unusedPeople);
        return "organizer";
    }
    @PostMapping("add")
    public String add(@RequestParam(defaultValue = "false")boolean logistics_specialist,
                      @RequestParam String face,
                      @RequestParam Person person) {
        Organizer organizer = new Organizer(logistics_specialist, face, person);
        organizerRepository.save(organizer);
        return "redirect:/organizer";
    }
    @PostMapping("delete/{id}")
    public String delete(@PathVariable(value = "id") Long id) {
        Organizer organizer = organizerRepository.findById(id).orElseThrow();
        organizerRepository.delete(organizer);
        return "redirect:/organizer";
    }
    @GetMapping("{id}")
    public String one(@PathVariable(value = "id") Long id,
                      Model model) {
        Organizer organizer = organizerRepository.findById(id).orElseThrow();
        Iterable<Person> people = personRepository.findAll();

        Set<Person> usedPeople = new HashSet<>();
        for (Artist artist : artistRepository.findAll()) {
            usedPeople.add(artist.getPerson());
        }
        for (Impresario impresario : impresarioRepository.findAll()) {
            usedPeople.add(impresario.getPerson());
        }
        for (Organizer organizer1 : organizerRepository.findAll()) {
            usedPeople.add(organizer1.getPerson());
        }

        List<Person> unusedPeople = new ArrayList<>();
        for (Person person : people) {
            if (!usedPeople.contains(person)) {
                unusedPeople.add(person);
            }
        }

        model.addAttribute("people", unusedPeople);
        model.addAttribute("organizer", organizer);
        return "organizer-edit";
    }
    @PostMapping("{id}")
    public String edit(@PathVariable(value = "id") Long id,
                       @RequestParam(defaultValue = "false")boolean logistics_specialist,
                       @RequestParam String face,
                       @RequestParam Person person) {
        Organizer organizer = organizerRepository.findById(id).orElseThrow();
        organizer.setLogistics_specialist(logistics_specialist);
        organizer.setFace(face);
        organizer.setPerson(person);
        organizerRepository.save(organizer);
        return "redirect:/organizer";
    }
}