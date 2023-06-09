package DB.Phillarmonic.Controllers;

import DB.Phillarmonic.Models.CulturalEvent;
import DB.Phillarmonic.Repositories.CulturalEventRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("cultural-event")
public class CulturalEventController {
    private final CulturalEventRepository culturalEventRepository;

    public CulturalEventController(CulturalEventRepository culturalEventRepository) {
        this.culturalEventRepository = culturalEventRepository;
    }
    @GetMapping
    public String all(Model model) {
        Iterable<CulturalEvent> culturalEvents = culturalEventRepository.findAll();
        model.addAttribute("culturalEvents", culturalEvents);
        return "cultural-event";
    }
    @PostMapping("add")
    public String add(@RequestParam String type,
                      @RequestParam int number_of_tickets_sold,
                      @RequestParam String title) {
        CulturalEvent culturalEvent = new CulturalEvent(type, number_of_tickets_sold, title);
        culturalEventRepository.save(culturalEvent);
        return "redirect:/cultural-event";
    }
    @PostMapping("delete/{id}")
    public String delete(@PathVariable(value = "id") Long id) {
        CulturalEvent culturalEvent = culturalEventRepository.findById(id).orElseThrow();
        culturalEventRepository.delete(culturalEvent);
        return "redirect:/cultural-event";
    }
    @GetMapping("{id}")
    public String one(@PathVariable(value = "id") Long id,
                      Model model) {
        CulturalEvent culturalEvent = culturalEventRepository.findById(id).orElseThrow();
        Iterable<CulturalEvent> culturalEvents = culturalEventRepository.findAll();

        model.addAttribute("culturalEvent", culturalEvent);
        model.addAttribute("culturalEvents", culturalEvents);
        return "cultural-event-edit";
    }
    @PostMapping("{id}")
    public String edit(@PathVariable(value = "id") Long id,
                       @RequestParam String type,
                       @RequestParam int number_of_tickets_sold,
                       @RequestParam String title) {
        CulturalEvent culturalEvent = culturalEventRepository.findById(id).orElseThrow();
        culturalEvent.setType(type);
        culturalEvent.setNumber_of_tickets_sold(number_of_tickets_sold);
        culturalEvent.setTitle(title);
        culturalEventRepository.save(culturalEvent);
        return "redirect:/cultural-event";
    }
}