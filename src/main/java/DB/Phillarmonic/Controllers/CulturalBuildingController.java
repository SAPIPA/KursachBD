package DB.Phillarmonic.Controllers;

import DB.Phillarmonic.Models.CulturalBuilding;
import DB.Phillarmonic.Repositories.CulturalBuildingRepository;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.model.IModel;

@Controller
@RequestMapping("cultural-building")
public class CulturalBuildingController {
    private final CulturalBuildingRepository culturalBuildingRepository;

    public CulturalBuildingController(CulturalBuildingRepository culturalBuildingRepository) {
        this.culturalBuildingRepository = culturalBuildingRepository;
    }
    @GetMapping
    public String all(Model model) {
        Iterable<CulturalBuilding> culturalBuildings = culturalBuildingRepository.findAll();

        model.addAttribute("culturalBuildings", culturalBuildings);
        return "cultural-building";
    }
    @PostMapping("add")
    public String add(@RequestParam String title,
                      @RequestParam String address,
                      @RequestParam String type,
                      @RequestParam int numberOfHalls,
                      @RequestParam int area,
                      @RequestParam int screen_size) {
        CulturalBuilding culturalBuilding = new CulturalBuilding(title,
                address, type, numberOfHalls, area, screen_size);
        culturalBuildingRepository.save(culturalBuilding);
        return "redirect:/cultural-building";
    }
    @PostMapping("delete/{id}")
    public String delete(@PathVariable(value = "id") Long id) {
        CulturalBuilding culturalBuilding = culturalBuildingRepository.findById(id).orElseThrow();
        culturalBuildingRepository.delete(culturalBuilding);
        return "redirect:/cultural-building";
    }
    @GetMapping("{id}")
    public String one(@PathVariable(value = "id") Long id,
                      Model model) {
        CulturalBuilding culturalBuilding = culturalBuildingRepository.findById(id).orElseThrow();
        Iterable<CulturalBuilding> culturalBuildings = culturalBuildingRepository.findAll();

        model.addAttribute("culturalBuilding", culturalBuilding);
        model.addAttribute("culturalBuildings", culturalBuildings);
        return "cultural-building-edit";
    }
    @PostMapping("{id}")
    public String edit(@PathVariable(value = "id") Long id,
                       @RequestParam String title,
                       @RequestParam String address,
                       @RequestParam String type,
                       @RequestParam int numberOfHalls,
                       @RequestParam int area,
                       @RequestParam int screen_size) {
        CulturalBuilding culturalBuilding = culturalBuildingRepository.findById(id).orElseThrow();
        culturalBuilding.setTitle(title);
        culturalBuilding.setAddress(address);
        culturalBuilding.setType(type);
        culturalBuilding.setNumberOfHalls(numberOfHalls);
        culturalBuilding.setArea(area);
        culturalBuilding.setScreen_size(screen_size);
        culturalBuildingRepository.save(culturalBuilding);
        return "redirect:/cultural-building";
    }
    @GetMapping("/filter")
    public String filter(@RequestParam(required = false) String type,
                         @RequestParam(required = false) Integer numberOfHalls,
                         Model model) {
        Iterable<CulturalBuilding> culturalBuildings = culturalBuildingRepository.findAll();
        if (type != null && !type.isEmpty()) {
            culturalBuildings = culturalBuildingRepository.findByType(type);
        }
        if (numberOfHalls != null) {
            culturalBuildings = culturalBuildingRepository.findByNumberOfHallsGreaterThanEqual(numberOfHalls);
        }
        model.addAttribute("culturalBuildings", culturalBuildings);
        return "cultural-building";
    }
}