package DB.Phillarmonic.Controllers;

import DB.Phillarmonic.Models.CulturalEvent;
import DB.Phillarmonic.Models.Organizer;
import DB.Phillarmonic.Models.OrganizerEvent;
import DB.Phillarmonic.Repositories.CulturalEventRepository;
import DB.Phillarmonic.Repositories.OrganizerEventRepository;
import DB.Phillarmonic.Repositories.OrganizerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("organizer-event")
public class OrganizerEventController {
    private final OrganizerEventRepository organizerEventRepository;
    private final OrganizerRepository organizerRepository;
    private final CulturalEventRepository culturalEventRepository;
    public OrganizerEventController(OrganizerEventRepository organizerEventRepository, OrganizerRepository organizerRepository, CulturalEventRepository culturalEventRepository) {
        this.organizerEventRepository = organizerEventRepository;
        this.organizerRepository = organizerRepository;
        this.culturalEventRepository = culturalEventRepository;
    }
    @GetMapping
    public String all(Model model) {
        Iterable<OrganizerEvent> organizerEvents = organizerEventRepository.findAll();
        Iterable<Organizer> organizers = organizerRepository.findAll();
        Iterable<CulturalEvent> culturalEvents = culturalEventRepository.findAll();
        model.addAttribute("organizerEvents", organizerEvents);
        model.addAttribute("organizers", organizers);
        model.addAttribute("culturalEvents", culturalEvents);
        return "organizer-event";
    }
    @PostMapping("add")
    public String add(@RequestParam int fee,
                      @RequestParam Organizer organizer,
                      @RequestParam CulturalEvent culturalEvent) {
        OrganizerEvent organizerEvent = new OrganizerEvent(fee, organizer, culturalEvent);
        organizerEventRepository.save(organizerEvent);
        return "redirect:/organizer-event";
    }
    @PostMapping("delete/{id}")
    public String delete(@PathVariable(value = "id") Long id) {
        OrganizerEvent organizerEvent = organizerEventRepository.findById(id).orElseThrow();
        organizerEventRepository.delete(organizerEvent);
        return "redirect:/organizer-event";
    }
    @GetMapping("{id}")
    public String one(@PathVariable(value = "id") Long id,
                      Model model) {
        OrganizerEvent organizerEvent = organizerEventRepository.findById(id).orElseThrow();
        Iterable<Organizer> organizers = organizerRepository.findAll();
        Iterable<CulturalEvent> culturalEvents = culturalEventRepository.findAll();
        model.addAttribute("organizerEvent", organizerEvent);
        model.addAttribute("organizers", organizers);
        model.addAttribute("culturalEvents", culturalEvents);
        return "organizer-event-edit";
    }
    @PostMapping("{id}")
    public String edit(@PathVariable(value = "id") Long id,
                       @RequestParam int fee,
                       @RequestParam Organizer organizer,
                       @RequestParam CulturalEvent culturalEvent) {
        OrganizerEvent organizerEvent = organizerEventRepository.findById(id).orElseThrow();
        organizerEvent.setFee(fee);
        organizerEvent.setOrganizer(organizer);
        organizerEvent.setCulturalEvent(culturalEvent);
        organizerEventRepository.save(organizerEvent);
        return "redirect:/organizer-event";
    }
    @GetMapping("/filter")
    public String filter(@RequestParam(required = false) Organizer organizer,
                         Model model) {
        Iterable<OrganizerEvent> organizerEvents = organizerEventRepository.findAll();
        Iterable<Organizer> organizers = organizerRepository.findAll();
        Iterable<CulturalEvent> culturalEvents = culturalEventRepository.findAll();
        if(organizer != null) {
            organizerEvents = organizerEventRepository.findByOrganizer(organizer);
        }
        model.addAttribute("organizerEvents", organizerEvents);
        model.addAttribute("organizers", organizers);
        model.addAttribute("culturalEvents", culturalEvents);
        return "organizer-event";
    }
}
