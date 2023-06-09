package DB.Phillarmonic.Controllers;

import DB.Phillarmonic.Models.Person;
import DB.Phillarmonic.Repositories.PersonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("person")
public class PersonController {
    private final PersonRepository personRepository;
    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    @GetMapping
    public String all(Model model) {
        Iterable<Person> people = personRepository.findAll();

        model.addAttribute("people", people);
        return "person";
    }
    @PostMapping("add")
    public String add(@RequestParam String name,
                      @RequestParam String surname,
                      @RequestParam String patronymic,
                      @RequestParam String email) {
        Person person = new Person(name, surname, patronymic, email);
        personRepository.save(person);
        return "redirect:/person";
    }
    @PostMapping("delete/{id}")
    public String delete(@PathVariable(value = "id") Long id) {
        Person person = personRepository.findById(id).orElseThrow();
        personRepository.delete(person);
        return "redirect:/person";
    }
    @GetMapping("{id}")
    public String one(@PathVariable(value = "id") Long id,
                      Model model) {
        Person person = personRepository.findById(id).orElseThrow();
        Iterable<Person> people = personRepository.findAll();

        model.addAttribute("people", people);
        model.addAttribute("person", person);
        return "person-edit";
    }
    @PostMapping("{id}")
    public String edit(@PathVariable(value = "id") Long id,
                       @RequestParam String name,
                       @RequestParam String surname,
                       @RequestParam String patronymic,
                       @RequestParam String email) {
        Person person = personRepository.findById(id).orElseThrow();
        person.setName(name);
        person.setSurname(surname);
        person.setPatronymic(patronymic);
        person.setEmail(email);
        personRepository.save(person);
        return "redirect:/person";
    }
}