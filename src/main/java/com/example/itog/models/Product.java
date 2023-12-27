package com.example.itog.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message = "Название не может быть пустым")
    @Size(min = 2, max = 30, message = "Длина названия минимум 2 символа и максимум 30 символов")
    private String name;

    @ManyToOne
    @JoinColumn(name = "type_product_id")
    private TypeProduct typeProduct;
    @ManyToOne
    @JoinColumn(name = "category_product_id")
    private CategoryProduct categoryProduct;

    @ManyToOne
    @JoinColumn(name = "size_product_id")
    private SizeProduct sizeProduct;

    @ManyToMany
    @JoinTable(name="position",
            joinColumns=@JoinColumn(name="product_id"),
            inverseJoinColumns=@JoinColumn(name="cklad_id"))
    private List<Cklad> cklads;

    public Product(){

    }

    public Product(long id, String name, TypeProduct typeProduct, CategoryProduct categoryProduct, SizeProduct sizeProduct,List<Cklad> cklads) {
        this.id = id;
        this.name = name;
        this.typeProduct = typeProduct;
        this.categoryProduct = categoryProduct;
        this.sizeProduct = sizeProduct;
        this.cklads = cklads;
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

    public TypeProduct getTypeProduct() {
        return typeProduct;
    }

    public void setTypeProduct(TypeProduct typeProduct) {
        this.typeProduct = typeProduct;
    }

    public CategoryProduct getCategoryProduct() {
        return categoryProduct;
    }

    public void setCategoryProduct(CategoryProduct categoryProduct) {
        this.categoryProduct = categoryProduct;
    }

    public SizeProduct getSizeProduct() {
        return sizeProduct;
    }

    public void setSizeProduct(SizeProduct sizeProduct) {
        this.sizeProduct = sizeProduct;
    }

    public List<Cklad> getCklads() {
        return cklads;
    }

    public void setCklads(List<Cklad> cklads) {
        this.cklads = cklads;
    }
}
