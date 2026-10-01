package s91k.github.io.bookstore_api.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import s91k.github.io.bookstore_api.dto.OrderRequest;
import s91k.github.io.bookstore_api.entity.Order;
import s91k.github.io.bookstore_api.entity.OrderItem;
import s91k.github.io.bookstore_api.repository.OrderRepository;

@Service
public class OrderService {
    OrderRepository orderRepository;
    BookService bookService;

    public OrderService(OrderRepository orderRepository, BookService bookService){
        this.orderRepository = orderRepository;
        this.bookService = bookService;
    }

    public Order findById(String id) {
        return this.orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order with id " + id + " not found."));
    }

    public Order create(OrderRequest r){
        if(r.orderItems() == null || r.orderItems().isEmpty()){
            throw new IllegalArgumentException("Must contain at least one order item");
        }

        Order o = new Order();

        o.setFirstName(r.firstName());
        o.setLastName(r.lastName());
        o.setEmail(r.email());
        o.setStreetAddress(r.streetAddress());
        o.setPostalCode(r.postalCode());
        o.setCity(r.city());

        o.setOrderItems(r.orderItems().stream().map((item) ->
                new OrderItem(o, bookService.findById(item.bookId()), item.quantity())).toList()
        );


        return this.orderRepository.save(o);
    }
}
