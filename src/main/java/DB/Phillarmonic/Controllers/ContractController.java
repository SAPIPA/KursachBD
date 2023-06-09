package DB.Phillarmonic.Controllers;

import DB.Phillarmonic.Models.*;
import DB.Phillarmonic.Repositories.ArtistRepository;
import DB.Phillarmonic.Repositories.ContractRepository;
import DB.Phillarmonic.Repositories.GenreArtistRepository;
import DB.Phillarmonic.Repositories.ImpresarioRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("contract")
public class ContractController {
    private final GenreArtistRepository genreArtistRepository;
    private final ContractRepository contractRepository;
    private final ArtistRepository artistRepository;
    private final ImpresarioRepository impresarioRepository;
    public ContractController(GenreArtistRepository genreArtistRepository, ContractRepository contractRepository, ArtistRepository artistRepository, ImpresarioRepository impresarioRepository) {
        this.genreArtistRepository = genreArtistRepository;
        this.contractRepository = contractRepository;
        this.artistRepository = artistRepository;
        this.impresarioRepository = impresarioRepository;
    }
    @GetMapping
    public String all(Model model) {
        Iterable<Contract> contracts = contractRepository.findAll();
        Iterable<Artist> artists = artistRepository.findAll();
        Iterable<Impresario> impresarios = impresarioRepository.findAll();

        model.addAttribute("contracts", contracts);
        model.addAttribute("artists", artists);
        model.addAttribute("impresarios", impresarios);
        return "contract";
    }
    @PostMapping("add")
    public String add(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date,
                      @RequestParam int duration,
                      @RequestParam Artist artist,
                      @RequestParam Impresario impresario) {
        Contract contract = new Contract(date, duration, artist, impresario);
        contractRepository.save(contract);
        return "redirect:/contract";
    }
    @PostMapping("delete/{id}")
    public String delete(@PathVariable(value = "id") Long id) {
        Contract contract = contractRepository.findById(id).orElseThrow();
        contractRepository.delete(contract);
        return "redirect:/contract";
    }
    @GetMapping("{id}")
    public String one(@PathVariable(value = "id") Long id,
                      Model model) {
        Contract contract = contractRepository.findById(id).orElseThrow();
        Iterable<Artist> artists = artistRepository.findAll();
        Iterable<Impresario> impresarios = impresarioRepository.findAll();

        model.addAttribute("contract", contract);
        model.addAttribute("artists", artists);
        model.addAttribute("impresarios", impresarios);
        return "contract-edit";
    }
    @PostMapping("{id}")
    public String edit(@PathVariable(value = "id") Long id,
                       @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date,
                       @RequestParam int duration,
                       @RequestParam Artist artist,
                       @RequestParam Impresario impresario) {
        Contract contract = contractRepository.findById(id).orElseThrow();
        contract.setDate(date);
        contract.setDuration(duration);
        contract.setArtist(artist);
        contract.setImpresario(impresario);
        contractRepository.save(contract);
        return "redirect:/contract";
    }
    @GetMapping("/filter")
    public String filter(@RequestParam(required = false) Impresario impresario,
                         @RequestParam(required = false) Artist artist,
                         Model model) {
        Iterable<Contract> contracts = contractRepository.findAll();
        Iterable<Artist> artists = artistRepository.findAll();
        Iterable<Impresario> impresarios = impresarioRepository.findAll();
        if (impresario != null) {
            contracts = contractRepository.findByImpresario(impresario);
        }
        if (artist != null) {
            contracts = contractRepository.findByArtist(artist);
        }
        model.addAttribute("contracts", contracts);
        model.addAttribute("artists", artists);
        model.addAttribute("impresarios", impresarios);
        return "contract";
    }
    @GetMapping("/filter/genre")
    public String filterGenre(@RequestParam(required = false) Genre genre,
                              Model model) {
        Iterable<GenreArtist> genreArtists = genreArtistRepository.findByGenre(genre);
        List<Impresario> impresarios = new ArrayList<>();

        for (GenreArtist genreArtist : genreArtists) {
            Iterable<Contract> contracts = contractRepository.findByArtist(genreArtist.getArtist());

            for (Contract contract : contracts) {
                Impresario impresario = contract.getImpresario();
                impresarios.add(impresario);
            }
        }
        return "contract";
    }
}
