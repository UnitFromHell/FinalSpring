package com.example.itog.Controller;
import com.example.itog.models.CategoryProduct;
import com.example.itog.models.SizeProduct;
import com.example.itog.models.SizeProduct;
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
@RequestMapping("/size")

public class SizeController {
    @Autowired
    private com.example.itog.repositories.SizeRepository SizeRepository;
    @Autowired
    private com.example.itog.repositories.ProductRepository productRepository;



    @GetMapping()
    public String index(Model model) {
        model.addAttribute("sizes", SizeRepository.findAll());
        return "/size/index";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("size", new CategoryProduct()); return "size/new";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        SizeProduct  sizeProduct = SizeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
        model.addAttribute("size", sizeProduct);
        return "size/edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        try {
            SizeProduct sizeProduct = SizeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
            if (sizeProduct.getProductItems().isEmpty()) {
                SizeRepository.delete(sizeProduct);
            } else {
                List<Product> prductDelList = sizeProduct.getProductItems();
                for (Product product:
                        prductDelList) {
                    productRepository.delete(product);
                }
                SizeRepository.delete(sizeProduct);
            }

        }
        catch (MethodArgumentTypeMismatchException e) {
            return "redirect:/size";
        }
        model.addAttribute("sizes", SizeRepository.findAll());
        return "redirect:/size";
    }
    @PostMapping("/addsize")
    public String addPerson(@Valid SizeProduct sizeProduct, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "size/new";
        }

        if (SizeRepository.existsByName(sizeProduct.getName())) {
            result.rejectValue("name", "error.categoryProduct", "Размер с таким именем уже существует");
            return "size/new";
        }
        SizeRepository.save(sizeProduct);
        model.addAttribute("sizes", SizeRepository.findAll());
        return "redirect:/size";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") long id, @Valid SizeProduct sizeProduct, BindingResult result, Model model) {
        if (result.hasErrors()) {
            sizeProduct.setId(id);
            return "size/edit";
        }

        if (SizeRepository.existsByName(sizeProduct.getName())) {
            result.rejectValue("name", "error.categoryProduct", "Размер с таким именем уже существует");
            return "size/edit";
        }
        SizeRepository.save(sizeProduct);
        model.addAttribute("sizes", SizeRepository.findAll());
        return "size/index";
    }
}
