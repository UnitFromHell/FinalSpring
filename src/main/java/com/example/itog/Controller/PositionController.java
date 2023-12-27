package com.example.itog.Controller;
import com.example.itog.models.*;
import com.example.itog.repositories.CkladRepository;
import com.example.itog.repositories.PositionRepository;
import com.example.itog.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/position")
public class PositionController {
    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CkladRepository ckladRepository;

    @GetMapping()
    public String getAllPositions(Model model) {
        model.addAttribute("positions", positionRepository.findAll());
        return "position/index";
    }
    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("position", new Position());
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        List<Cklad> cklads = ckladRepository.findAll();
        model.addAttribute("cklads", cklads);
        return "position/new";
    }
    @PostMapping("/addposition")
    public String addPosition(@Valid Position position, BindingResult result, Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        List<Cklad> cklads = ckladRepository.findAll();
        model.addAttribute("cklads", cklads);
        model.addAttribute("positions", new Position());
        if (result.hasErrors()) {
            return "position/new";
        }
        positionRepository.save(position);
        return "redirect:/position";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid position Id: " + id));
        model.addAttribute("position", position);
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        List<Cklad> cklads = ckladRepository.findAll();
        model.addAttribute("cklads", cklads);


        return "position/edit";
    }

    @PostMapping("/{id}")
    public String updatePosition(@PathVariable("id") long id, @Valid Position position, BindingResult result, Model model) {
        if (result.hasErrors()) {
            position.setId(id);
            return "position/edit";
        }
        positionRepository.save(position);
        return "redirect:/position";
    }

    @GetMapping("/delete/{id}")
    public String deletePosition(@PathVariable("id") long id, Model model) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid position Id: " + id));
        positionRepository.delete(position);
        return "redirect:/position";
    }
}
