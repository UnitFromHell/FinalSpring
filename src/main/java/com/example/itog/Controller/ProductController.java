package com.example.itog.Controller;
import com.example.itog.models.*;
import com.example.itog.repositories.*;
import jakarta.validation.Valid;
import org.hibernate.engine.jdbc.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/product")

public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CkladRepository ckladRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("types", typeRepository.findAll());
        model.addAttribute("sizes", sizeRepository.findAll());
        model.addAttribute("categorys", categoryRepository.findAll());
        return "/product/index";
    }
    @GetMapping("/new")
    public String showAddForm(Model model) {
        List<TypeProduct> typeProducts = typeRepository.findAll();
        model.addAttribute("types", typeProducts);
        model.addAttribute("product", new Product());
        List<SizeProduct> sizeProducts = sizeRepository.findAll();
        model.addAttribute("sizes", sizeProducts);
        List<CategoryProduct> categoryProducts = categoryRepository.findAll();
        model.addAttribute("categorys", categoryProducts);
        return "product/new";
    }
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Product  product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
        model.addAttribute("product", product);
        List<TypeProduct> typeProducts = typeRepository.findAll();
        model.addAttribute("types", typeProducts);
        List<SizeProduct> sizeProducts = sizeRepository.findAll();
        model.addAttribute("sizes", sizeProducts);
        List<CategoryProduct> categoryProducts = categoryRepository.findAll();
        model.addAttribute("categorys", categoryProducts);
        return "product/edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        try {
            Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
            productRepository.delete(product);
        }
        catch (MethodArgumentTypeMismatchException e) {
            return "redirect:/product";

        }
        model.addAttribute("products", productRepository.findAll());
        return "redirect:/product";
    }

    @PostMapping("/addproduct")
    public String addPerson(@Valid Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<TypeProduct> typeProducts2 = typeRepository.findAll();
            model.addAttribute("types", typeProducts2);
            List<SizeProduct> sizeProducts2 = sizeRepository.findAll();
            model.addAttribute("sizes", sizeProducts2);
            List<CategoryProduct> categoryProducts2 = categoryRepository.findAll();
            model.addAttribute("categorys", categoryProducts2);
            model.addAttribute("products", productRepository.findAll());
            return "product/new";
        }
        productRepository.save(product);
        model.addAttribute("products", productRepository.findAll());
        return "redirect:/product";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") long id, @Valid Product product, BindingResult result, Model model) {
        List<TypeProduct> typeProducts2 = typeRepository.findAll();
        model.addAttribute("types", typeProducts2);
        List<SizeProduct> sizeProducts2 = sizeRepository.findAll();
        model.addAttribute("sizes", sizeProducts2);
        List<CategoryProduct> categoryProducts2 = categoryRepository.findAll();
        model.addAttribute("categorys", categoryProducts2);
        model.addAttribute("products", productRepository.findAll());
        if (result.hasErrors()) {
            product.setId(id);
            List<TypeProduct> typeProducts = typeRepository.findAll();
            model.addAttribute("types", typeProducts);
            List<SizeProduct> sizeProducts = sizeRepository.findAll();
            model.addAttribute("sizes", sizeProducts);
            List<CategoryProduct> categoryProducts = categoryRepository.findAll();
            model.addAttribute("categorys", categoryProducts);
            model.addAttribute("products", productRepository.findAll());
            return "product/edit";
        }
        productRepository.save(product);
        List<TypeProduct> typeProducts = typeRepository.findAll();
        model.addAttribute("types", typeProducts);
        List<SizeProduct> sizeProducts = sizeRepository.findAll();
        model.addAttribute("sizes", sizeProducts);
        List<CategoryProduct> categoryProducts = categoryRepository.findAll();
        model.addAttribute("categorys", categoryProducts);
        model.addAttribute("products", productRepository.findAll());
        return "product/index";
    }

    @GetMapping("/search")
    public String search(@RequestParam("name") String name, Model model) {
        List<Product> products = productRepository.findAll();
        List<Product> sortproducts = new ArrayList<>();
        for (Product product:
                products) {
            if(product.getName().toLowerCase().contains(name.toLowerCase()))
            {
                sortproducts.add(product);
            }
        }
        model.addAttribute("products", sortproducts);
        return "product/index";
    }
}
