package DB.Phillarmonic.Controllers;

import DB.Phillarmonic.Models.CulturalBuilding;
import DB.Phillarmonic.Models.CulturalEvent;
import DB.Phillarmonic.Models.EventBuilding;
import DB.Phillarmonic.Repositories.CulturalBuildingRepository;
import DB.Phillarmonic.Repositories.CulturalEventRepository;
import DB.Phillarmonic.Repositories.EventBuildingRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("event-building")
public class EventBuildingController {
    private final EventBuildingRepository eventBuildingRepository;
    private final CulturalEventRepository culturalEventRepository;
    private final CulturalBuildingRepository culturalBuildingRepository;
    public EventBuildingController(EventBuildingRepository eventBuildingRepository, CulturalEventRepository culturalEventRepository, CulturalBuildingRepository culturalBuildingRepository) {
        this.eventBuildingRepository = eventBuildingRepository;
        this.culturalEventRepository = culturalEventRepository;
        this.culturalBuildingRepository = culturalBuildingRepository;
    }
    @GetMapping
    public String all(Model model) {
        Iterable<EventBuilding> eventBuildings = eventBuildingRepository.findAll();
        Iterable<CulturalBuilding> culturalBuildings = culturalBuildingRepository.findAll();
        Iterable<CulturalEvent> culturalEvents = culturalEventRepository.findAll();
        model.addAttribute("eventBuildings", eventBuildings);
        model.addAttribute("culturalBuildings", culturalBuildings);
        model.addAttribute("culturalEvents", culturalEvents);
        return "event-building";
    }
    @PostMapping("add")
    public String add(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date,
                      @RequestParam CulturalBuilding culturalBuilding,
                      @RequestParam CulturalEvent culturalEvent) {
        EventBuilding eventBuilding = new EventBuilding(date, culturalBuilding, culturalEvent);
        eventBuildingRepository.save(eventBuilding);
        return "redirect:/event-building";
    }
    @PostMapping("delete/{id}")
    public String delete(@PathVariable(value = "id") Long id) {
        EventBuilding eventBuilding = eventBuildingRepository.findById(id).orElseThrow();
        eventBuildingRepository.delete(eventBuilding);
        return "redirect:/event-building";
    }
    @GetMapping("{id}")
    public String one(@PathVariable(value = "id") Long id,
                      Model model) {
        EventBuilding eventBuilding = eventBuildingRepository.findById(id).orElseThrow();
        Iterable<CulturalBuilding> culturalBuildings = culturalBuildingRepository.findAll();
        Iterable<CulturalEvent> culturalEvents = culturalEventRepository.findAll();
        model.addAttribute("eventBuilding", eventBuilding);
        model.addAttribute("culturalBuildings", culturalBuildings);
        model.addAttribute("culturalEvents", culturalEvents);
        return "event-building-edit";
    }
    @PostMapping("{id}")
    public String edit(@PathVariable(value = "id") Long id,
                       @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date,
                       @RequestParam CulturalBuilding culturalBuilding,
                       @RequestParam CulturalEvent culturalEvent) {
        EventBuilding eventBuilding = eventBuildingRepository.findById(id).orElseThrow();
        eventBuilding.setDate(date);
        eventBuilding.setCulturalBuilding(culturalBuilding);
        eventBuilding.setCulturalEvent(culturalEvent);
        eventBuildingRepository.save(eventBuilding);
        return "redirect:/event-building";
    }
    @GetMapping("/filter")
    public String filter(@RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate minDate,
                         @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate maxDate,
                         @RequestParam(required = false) CulturalBuilding culturalBuilding,
                         Model model) {
        Iterable<EventBuilding> eventBuildings = eventBuildingRepository.findAll();
        Iterable<CulturalBuilding> culturalBuildings = culturalBuildingRepository.findAll();
        Iterable<CulturalEvent> culturalEvents = culturalEventRepository.findAll();
        if(minDate != null && maxDate != null) {
            eventBuildings = eventBuildingRepository.findByDateBetween(minDate, maxDate);
        }
        if(culturalBuilding != null) {
            eventBuildings = eventBuildingRepository.findByCulturalBuilding(culturalBuilding);
        }
        model.addAttribute("eventBuildings", eventBuildings);
        model.addAttribute("culturalBuildings", culturalBuildings);
        model.addAttribute("culturalEvents", culturalEvents);
        return "event-building";
    }
}