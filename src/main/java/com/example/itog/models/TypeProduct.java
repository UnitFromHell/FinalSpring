package com.example.itog.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "TypeProduct")
public class TypeProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true)
    @NotBlank(message = "Тип не может быть пустым")
    @Size(min = 2, max = 30, message = "Длина типа минимум 2 символа и максимум 30 символов")
    private String name;

    @OneToMany(mappedBy = "typeProduct")
    private List<Product> productItems;

    public TypeProduct(){

    }

    public TypeProduct(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TypeProduct(long id, String name, List<Product> productItems) {
        this.id = id;
        this.name = name;
        this.productItems = productItems;
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

    public List<Product> getProductItems() {
        return productItems;
    }

    public void setProductItems(List<Product> productItems) {
        this.productItems = productItems;
    }
}
