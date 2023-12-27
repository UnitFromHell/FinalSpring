package com.example.itog.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "Cklad1")
public class Cklad {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message = "Название не может быть пустым")
    @Size(min = 2, max = 30, message = "Длина названия минимум 2 символа и максимум 30 символов")
    private String name;
    @NotBlank(message = "Адрес не может быть пустым")
    private String adress;



    @ManyToMany
    @JoinTable(name="position",
            joinColumns=@JoinColumn(name="cklad_id"),
            inverseJoinColumns=@JoinColumn(name="product_id"))
    private List<Product> products;

    public Cklad(){

    }

    public Cklad(long id, String name, String adress, List<Product> products) {
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.products = products;

    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }


}
