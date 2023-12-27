package com.example.itog.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message = "Номер не может быть пустым")
    @Size(min = 2, max = 30, message = "Длина номера минимум 2 символа и максимум 30 символов")
    private String name;

    @NotNull(message = "Сумма не может быть пустым")
    @Min(value = 1, message = "Заказ не может быть меньше 1")
    private double sum;

    @NotBlank(message = "Адрес не может быть пустым")
    private String adress;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Person user;

    public Order(){

    }

    public Order(long id, String name, double sum, String adress, Person user) {
        this.id = id;
        this.name = name;
        this.sum = sum;
        this.adress = adress;
        this.user = user;
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

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }
}
