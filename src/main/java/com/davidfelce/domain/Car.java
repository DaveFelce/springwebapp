package com.davidfelce.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.LinkedList;

public class Car {
    private int id;
    @NotEmpty
    private String name;
    @Min(100) @Max(1000000)
    private BigDecimal price;
    private LinkedList<Driver> drivers = new LinkedList<>();

    public Car() {
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public LinkedList<Driver> getDrivers() {
        return drivers;
    }
    public void setDrivers(LinkedList<Driver> drivers) {
        this.drivers = drivers;
    }
}

