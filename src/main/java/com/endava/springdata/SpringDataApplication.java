package com.endava.springdata;

import com.endava.springdata.model.Address;
import com.endava.springdata.model.Customer;
import com.endava.springdata.model.Item;
import com.endava.springdata.model.Order;
import com.endava.springdata.repository.CustomerRepository;
import com.endava.springdata.repository.ItemRepository;
import com.endava.springdata.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringDataApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataApplication.class, args);
    }

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        // One to one

        Customer customer1 = new Customer("Aleksandar Stojanovikj");
        Address address = new Address("Marshal Tito", "Brvenica", customer1);
        customer1.setAddress(address);

        customerRepository.save(customer1);

        System.out.println("===== One-to-One =====");
        System.out.println(customerRepository.getOne(1L).getFullName());
        System.out.println(customerRepository.getOne(1L).getAddress().getAddress());

        // One to many

        Order order1 = new Order(LocalDateTime.now(), customer1, 123L);
        Order order2 = new Order(LocalDateTime.now(), customer1, 321L);
        customer1.setOrders(Stream.of(order1, order2).collect(Collectors.toSet()));

        customerRepository.save(customer1);

        Customer queryCustomer1 = customerRepository.findById(1L).get();
        Set<Order> customer1Orders = queryCustomer1.getOrders();

        System.out.println("\n===== One-to-Many =====");
        customer1Orders.forEach(System.out::println);


        // Many to many

        Item item1 = new Item("Mouse", "Device used to control the mouse pointer on a computer");
        Item item2 = new Item("Keyboard", "Device used to type words on a computer");

        order1.addItem(item1);
        order1.addItem(item2);

        item1.addOrder(order1);
        item2.addOrder(order1);

        orderRepository.save(order1);

        System.out.println("\n===== Many-to-Many =====");

        orderRepository.findById(order1.getId()).get().getItems().forEach(i -> System.out.println(i.getName() + " : " + i.getDescription()));

    }
}
