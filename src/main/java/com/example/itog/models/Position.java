package com.example.itog.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "position")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long cklad_id;

    private long product_id;

    public Position(){

    }
    public Position(long id, long cklad_id, long product_id) {
        this.id = id;
        this.cklad_id = cklad_id;
        this.product_id = product_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCklad_id() {
        return cklad_id;
    }

    public void setCklad_id(long cklad_id) {
        this.cklad_id = cklad_id;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }
}
