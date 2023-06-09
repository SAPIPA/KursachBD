package DB.Phillarmonic.Controllers;

import DB.Phillarmonic.Models.Agreement;
import DB.Phillarmonic.Models.Impresario;
import DB.Phillarmonic.Models.Organizer;
import DB.Phillarmonic.Repositories.AgreementRepository;
import DB.Phillarmonic.Repositories.ImpresarioRepository;
import DB.Phillarmonic.Repositories.OrganizerRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("agreement")
public class AgreementController {
    private final AgreementRepository agreementRepository;
    private final ImpresarioRepository impresarioRepository;
    private final OrganizerRepository organizerRepository;

    public AgreementController(AgreementRepository agreementRepository, ImpresarioRepository impresarioRepository, OrganizerRepository organizerRepository) {
        this.agreementRepository = agreementRepository;
        this.impresarioRepository = impresarioRepository;
        this.organizerRepository = organizerRepository;
    }
    @GetMapping
    public String all(Model model) {
        Iterable<Agreement> agreements = agreementRepository.findAll();
        Iterable<Impresario> impresarios = impresarioRepository.findAll();
        Iterable<Organizer> organizers = organizerRepository.findAll();

        model.addAttribute("agreements", agreements);
        model.addAttribute("impresarios", impresarios);
        model.addAttribute("organizers", organizers);
        return "agreement";
    }
    @PostMapping("add")
    public String add(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date,
                      @RequestParam Impresario impresario,
                      @RequestParam Organizer organizer) {
        Agreement agreement = new Agreement(date, impresario, organizer);
        agreementRepository.save(agreement);
        return "redirect:/agreement";
    }
    @PostMapping("delete/{id}")
    public String delete(@PathVariable(value = "id") Long id) {
        Agreement agreement = agreementRepository.findById(id).orElseThrow();
        agreementRepository.delete(agreement);
        return "redirect:/agreement";
    }
    @GetMapping("{id}")
    public String one(@PathVariable(value = "id") Long id,
                      Model model) {
        Agreement agreement = agreementRepository.findById(id).orElseThrow();
        Iterable<Impresario> impresarios = impresarioRepository.findAll();
        Iterable<Organizer> organizers = organizerRepository.findAll();

        model.addAttribute("agreement", agreement);
        model.addAttribute("impresarios", impresarios);
        model.addAttribute("organizers", organizers);
        return "agreement-edit";
    }
    @PostMapping("{id}")
    public String edit(@PathVariable(value = "id") Long id,
                       @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date,
                       @RequestParam Impresario impresario,
                       @RequestParam Organizer organizer) {
        Agreement agreement = agreementRepository.findById(id).orElseThrow();
        agreement.setDate(date);
        agreement.setImpresario(impresario);
        agreement.setOrganizer(organizer);
        agreementRepository.save(agreement);
        return "redirect:/agreement";
    }
}
