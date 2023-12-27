package com.example.itog.Controller;



import com.example.itog.models.Person;
import com.example.itog.models.Role;

import com.example.itog.repositories.PersonRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/people")
@PreAuthorize("hasAuthority('ADMIN')")
public class PersonController {
    @Autowired
    private PersonRepository personRepository;


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personRepository.findAll());
        return "/people/index";
    }

    @GetMapping("/new")
    public String showAddForm(Person person, Model model) {
        return "people/new";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Person person = personRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
        model.addAttribute("person", person);

        return "people/edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        try {
            // Получение информации о текущем пользователе
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserName = authentication.getName();
            Person person = personRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid person Id: " + id));

            // Проверка, чтобы пользователь не мог удалить самого себя
            if (person.getLogin().equals(currentUserName)) {
                // Вернуть сообщение об ошибке или выполнить другие действия, например, перенаправить на страницу с предупреждением
                return "redirect:/error-page";
            }

            personRepository.delete(person);
        } catch (MethodArgumentTypeMismatchException e) {
            return "redirect:/people";
        }
        model.addAttribute("people", personRepository.findAll());
        return "redirect:/people";
    }

    @PostMapping("/addperson")
    public String addPerson(@Valid Person person, BindingResult result, Model model, @RequestParam("roles") Set<Role> roles) {

        if (result.hasErrors()) {

            return "people/new";
        }

        try {


            if (personRepository.existsByLogin(person.getLogin())) {
                result.rejectValue("login", "error.person", "Данный логин уже существует");
                return "people/new";
            }


            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encryptedPassword = passwordEncoder.encode(person.getPassword());
            person.setPassword(encryptedPassword);

            person.setRoles(roles);
            personRepository.save(person);
            model.addAttribute("people", personRepository.findAll());
            return "people/index";
        } catch (Exception e) {
            result.reject("error.person", "Произошла ошибка при добавлении человека");
            return "people/new";
        }
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") long id, @ModelAttribute("person") @Valid Person person, BindingResult result, Model model, @RequestParam("roles") Set<Role> roles) {
        if (result.hasErrors()) {
            model.addAttribute("person", person);
            return "people/edit";
        }


        Person existingPerson = personRepository.findById(id).orElse(null);
        if (!person.getLogin().equals(existingPerson.getLogin()) && personRepository.existsByLogin(person.getLogin())) {
            result.rejectValue("login", "error.person", "Данный логин уже существует");
            return "people/edit";
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(person.getPassword());
        person.setPassword(encryptedPassword);
        person.setRoles(roles);
        personRepository.save(person);
        model.addAttribute("persons", personRepository.findAll());
        return "redirect:/people";
    }
    @GetMapping("/search")
    public String search(@RequestParam("name") String name, Model model) {
        List<Person> persons = personRepository.findAll();
        List<Person> sortPerson = new ArrayList<>();
        for (Person person:
                persons) {
            if(person.getName().toLowerCase().contains(name.toLowerCase()))
            {
                sortPerson.add(person);
            }
        }
        model.addAttribute("people", sortPerson);
        return "/people/index";
    }
}
