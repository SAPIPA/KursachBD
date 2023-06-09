package DB.Phillarmonic.Controllers;

import DB.Phillarmonic.Models.Artist;
import DB.Phillarmonic.Models.ArtistEvent;
import DB.Phillarmonic.Models.CulturalEvent;
import DB.Phillarmonic.Repositories.ArtistEventRepository;
import DB.Phillarmonic.Repositories.ArtistRepository;
import DB.Phillarmonic.Repositories.CulturalEventRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("artist-event")
public class ArtistEventController {
    private final ArtistRepository artistRepository;
    private final CulturalEventRepository culturalEventRepository;
    private final ArtistEventRepository artistEventRepository;

    public ArtistEventController(ArtistRepository artistRepository, CulturalEventRepository culturalEventRepository, ArtistEventRepository artistEventRepository) {
        this.artistRepository = artistRepository;
        this.culturalEventRepository = culturalEventRepository;
        this.artistEventRepository = artistEventRepository;
    }
    @GetMapping
    public String all(Model model) {
        Iterable<Artist> artists = artistRepository.findAll();
        Iterable<CulturalEvent> culturalEvents = culturalEventRepository.findAll();
        Iterable<ArtistEvent> artistEvents = artistEventRepository.findAll();

        model.addAttribute("artists", artists);
        model.addAttribute("culturalEvents", culturalEvents);
        model.addAttribute("artistEvents", artistEvents);
        return "artist-event";
    }
    @PostMapping("add")
    public String add(@RequestParam(defaultValue = "false") boolean prize,
                      @RequestParam Artist artist,
                      @RequestParam CulturalEvent culturalEvent) {
        ArtistEvent artistEvent = new ArtistEvent(prize, artist, culturalEvent);
        artistEventRepository.save(artistEvent);
        return "redirect:/artist-event";
    }
    @PostMapping("delete/{id}")
    public String delete(@PathVariable(value = "id") Long id) {
        ArtistEvent artistEvent = artistEventRepository.findById(id).orElseThrow();
        artistEventRepository.delete(artistEvent);
        return "redirect:/artist-event";
    }
    @GetMapping("{id}")
    public String one(@PathVariable(value = "id") Long id,
                      Model model) {
        ArtistEvent artistEvent = artistEventRepository.findById(id).orElseThrow();
        Iterable<Artist> artists = artistRepository.findAll();
        Iterable<CulturalEvent> culturalEvents = culturalEventRepository.findAll();
        model.addAttribute("artistEvent", artistEvent);
        model.addAttribute("artists", artists);
        model.addAttribute("culturalEvents", culturalEvents);
        return "artist-event-edit";
    }
    @PostMapping("{id}")
    public String edit(@PathVariable(value = "id") Long id,
                       @RequestParam(defaultValue = "false") boolean prize,
                       @RequestParam Artist artist,
                       @RequestParam CulturalEvent culturalEvent) {
        ArtistEvent artistEvent = artistEventRepository.findById(id).orElseThrow();
        artistEvent.setPrize(prize);
        artistEvent.setArtist(artist);
        artistEvent.setCulturalEvent(culturalEvent);
        artistEventRepository.save(artistEvent);
        return "redirect:/artist-event";
    }
    @GetMapping("/filter")
    public String filter(@RequestParam(required = false) CulturalEvent culturalEvent,
                         Model model) {
        Iterable<Artist> artists = artistRepository.findAll();
        Iterable<CulturalEvent> culturalEvents = culturalEventRepository.findAll();
        Iterable<ArtistEvent> artistEvents = artistEventRepository.findAll();
        if(culturalEvent != null) {
            artistEvents = artistEventRepository.findByCulturalEventAndPrizeIsTrue(culturalEvent);
        }
        model.addAttribute("artists", artists);
        model.addAttribute("culturalEvents", culturalEvents);
        model.addAttribute("artistEvents", artistEvents);
        return "artist-event";
    }
}
