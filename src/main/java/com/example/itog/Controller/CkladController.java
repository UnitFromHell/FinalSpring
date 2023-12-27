package com.example.itog.Controller;
import com.example.itog.models.Cklad;
import com.example.itog.models.Product;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cklad")
public class CkladController {
    @Autowired
    private com.example.itog.repositories.CkladRepository ckladRepository;
    @Autowired
    private com.example.itog.repositories.ProductRepository productRepository;

    @Autowired
    public CkladController(com.example.itog.repositories.CkladRepository CkladRepository) {
        this.ckladRepository = ckladRepository;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("cklads", ckladRepository.findAll());
        model.addAttribute("products", productRepository.findAll());
        return "/cklad/index";
    }

    @GetMapping("/new")
    public String showAddForm(Cklad cklad) {
        return "cklad/new";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Cklad cklad = ckladRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
        model.addAttribute("cklad", cklad);
        return "cklad/edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        try {
            Cklad cklad = ckladRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
            ckladRepository.delete(cklad);
        }
        catch (MethodArgumentTypeMismatchException e) {
            return "redirect:/cklad";

        }
        model.addAttribute("cklads", ckladRepository.findAll());
        return "redirect:/cklad";
    }

    @PostMapping("/addcklad")
    public String addPerson(@Valid Cklad cklad, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "cklad/new";
        }
        ckladRepository.save(cklad);
        model.addAttribute("cklads", ckladRepository.findAll());
        return "redirect:/cklad";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") long id, @Valid Cklad cklad, BindingResult result, Model model) {
        if (result.hasErrors()) {
            cklad.setId(id);
            return "cklad/edit";
        }



        ckladRepository.save(cklad);

        model.addAttribute("cklads", ckladRepository.findAll());
        return "cklad/index";
    }

    @GetMapping("/search")
    public String search(@RequestParam("name") String name, Model model) {
        List<Cklad> cklads = ckladRepository.findAll();
        List<Cklad> sortcklad = new ArrayList<>();
        for (Cklad cklad:
                cklads) {
            if(cklad.getName().toLowerCase().contains(name.toLowerCase()))
            {
                sortcklad.add(cklad);
            }
        }
        model.addAttribute("cklads", sortcklad);
        return "cklad/index";
    }






}
