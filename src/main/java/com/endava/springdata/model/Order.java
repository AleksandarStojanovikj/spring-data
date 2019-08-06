package com.endava.springdata.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime orderDate;

    private Long amountEuros;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "orders")
    private Set<Item> items = new HashSet<>();

    public Order() {

    }

    public Order(LocalDateTime orderDate, Customer customer, Long amountEuros) {
        this.orderDate = orderDate;
        this.customer = customer;
        this.amountEuros = amountEuros;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Long getAmountEuros() {
        return amountEuros;
    }

    public void setAmountEuros(Long amountEuros) {
        this.amountEuros = amountEuros;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return String.format("id: %d\tamount: %d\tcustomer: %s", id, amountEuros, customer.getFullName());
    }
}
