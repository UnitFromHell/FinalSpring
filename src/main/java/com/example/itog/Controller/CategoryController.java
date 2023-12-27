package com.example.itog.Controller;

import com.example.itog.models.CategoryProduct;
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
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private com.example.itog.repositories.CategoryRepository categoryRepository;
    @Autowired
    private com.example.itog.repositories.ProductRepository productRepository;



    @GetMapping()
    public String index(Model model) {
        model.addAttribute("categorys", categoryRepository.findAll());
        return "/category/index";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("category", new CategoryProduct()); return "category/new";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        CategoryProduct  categoryProduct = categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
        model.addAttribute("category", categoryProduct);
        return "category/edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        try {
            CategoryProduct categoryProduct = categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
            if (categoryProduct.getProductItems().isEmpty()) {
                categoryRepository.delete(categoryProduct);
            } else {
                List<Product> prductDelList = categoryProduct.getProductItems();
                for (Product product:
                        prductDelList) {
                    productRepository.delete(product);
                }
                categoryRepository.delete(categoryProduct);
            }

        }
        catch (MethodArgumentTypeMismatchException e) {
            return "redirect:/category";
        }
        model.addAttribute("categorys", categoryRepository.findAll());
        return "redirect:/category";
    }
    @PostMapping("/addcategory")
    public String addPerson(@Valid CategoryProduct categoryProduct, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "Такая категория уже есть");
            model.addAttribute("category", categoryProduct);
            return "category/new";
        }

        if (categoryRepository.existsByName(categoryProduct.getName())) {
            result.rejectValue("name", "error.categoryProduct", "Категория с таким именем уже существует");
            model.addAttribute("category", categoryProduct);
            return "category/new";
        }
        categoryRepository.save(categoryProduct);
        model.addAttribute("categorys", categoryRepository.findAll());
        return "redirect:/category";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") long id, @Valid CategoryProduct categoryProduct, BindingResult result, Model model) {
        if (result.hasErrors()) {
            categoryProduct.setId(id);
            model.addAttribute("errorMessage", "Такая категория уже есть");
            model.addAttribute("category", categoryProduct);
            return "category/edit";
        }

        if (categoryRepository.existsByName(categoryProduct.getName())) {
            result.rejectValue("name", "error.categoryProduct", "Категория с таким именем уже существует");
            model.addAttribute("category", categoryProduct);
            return "category/edit";
        }
        categoryRepository.save(categoryProduct);
        model.addAttribute("categorys", categoryRepository.findAll());
        return "category/index";
    }
}
