package com.example.itog.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "SizeProduct")

public class SizeProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true)
    @NotNull(message = "Размер не может быть пустым")
    @DecimalMin(value = "1", message = "Размер не может быть меньше 1")
    @DecimalMax(value = "55", message = "Размер не может быть больше 55")
    private double name;

    @OneToMany(mappedBy = "sizeProduct")
    private List<Product> productItems;

    public SizeProduct(){

    }

    public SizeProduct(long id, double name, List<Product> productItems) {
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

    public double getName() {
        return name;
    }

    public void setName(double name) {
        this.name = name;
    }

    public List<Product> getProductItems() {
        return productItems;
    }

    public void setProductItems(List<Product> productItems) {
        this.productItems = productItems;
    }
}
