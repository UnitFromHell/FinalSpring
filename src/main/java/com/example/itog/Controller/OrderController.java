package com.example.itog.Controller;
import com.example.itog.models.Cklad;
import com.example.itog.models.Order;
import com.example.itog.models.Person;
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
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private com.example.itog.repositories.OrderRepository orderRepository;
    @Autowired
    private com.example.itog.repositories.PersonRepository personRepository;



    @GetMapping()
    public String index(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        model.addAttribute("persons", personRepository.findAll());
        return "order/index";
    }

    @GetMapping("/new")
    public String showAddForm(Order order, Model model)
    {
        List<Person> persons = personRepository.findAll();
        model.addAttribute("users", persons);
        return "order/new";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
        model.addAttribute("order", order);
        List<Person> persons = personRepository.findAll();
        model.addAttribute("users", persons);
        return "order/edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") long id, Model model) {
        try {
            Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid strawberry Id: " + id));
            orderRepository.delete(order);
        }
        catch (MethodArgumentTypeMismatchException e) {
            return "redirect:/order";
        }
        model.addAttribute("orders", orderRepository.findAll());
        return "redirect:/order";
    }

    @GetMapping("/search")
    public String search(@RequestParam("name") String name, Model model) {
        if (name.isEmpty()) {
            model.addAttribute("orders", orderRepository.findAll());
        } else {
            List<Order> orders = orderRepository.findAll();
            List<Order> sortorders = new ArrayList<>();
            for (Order order : orders) {
                if (order.getName().toLowerCase().contains(name.toLowerCase())) {
                    sortorders.add(order);
                }
            }
            model.addAttribute("orders", sortorders);
        }
        return "/order/index";
    }
}
