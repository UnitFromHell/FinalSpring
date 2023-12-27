package com.example.itog.Controller;

import com.example.itog.models.CategoryProduct;
import com.example.itog.models.TypeProduct;

import com.example.itog.models.Product;
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
@RequestMapping("/type")
public class TypeController {
    @Autowired
    private com.example.itog.repositories.TypeRepository typeRepository;
    @Autowired
    private com.example.itog.repositories.ProductRepository productRepository;



    @GetMapping()
    public String index(Model model) {
        model.addAttribute("types", typeRepository.findAll());
        return "/type/index";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("type", new CategoryProduct()); return "type/new";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        TypeProduct typeProduct = typeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
        model.addAttribute("type", typeProduct);
        return "type/edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        try {
            TypeProduct typeProduct = typeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
            if (typeProduct.getProductItems().isEmpty()) {
                typeRepository.delete(typeProduct);
            } else {
                List<Product> prductDelList = typeProduct.getProductItems();
                for (Product product:
                        prductDelList) {
                    productRepository.delete(product);
                }
                typeRepository.delete(typeProduct);
            }

        }
        catch (MethodArgumentTypeMismatchException e) {
            return "redirect:/type";
        }
        model.addAttribute("types", typeRepository.findAll());
        return "redirect:/type";
    }
    @PostMapping("/addtype")
    public String addPerson(@Valid TypeProduct typeProduct, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "type/new";
        }

        if (typeRepository.existsByName(typeProduct.getName())) {
            result.rejectValue("name", "error.categoryProduct", "Тип с таким именем уже существует");
            return "type/new";
        }
        typeRepository.save(typeProduct);
        model.addAttribute("types", typeRepository.findAll());
        return "redirect:/type";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") long id, @Valid TypeProduct typeProduct, BindingResult result, Model model) {
        if (result.hasErrors()) {
            typeProduct.setId(id);
            return "type/edit";
        }

        if (typeRepository.existsByName(typeProduct.getName())) {
            result.rejectValue("name", "error.categoryProduct", "Тип с таким именем уже существует");
            return "type/edit";
        }
        typeRepository.save(typeProduct);
        model.addAttribute("types", typeRepository.findAll());
        return "type/index";
    }
}
