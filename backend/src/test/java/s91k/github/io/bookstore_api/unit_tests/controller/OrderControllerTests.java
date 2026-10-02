package s91k.github.io.bookstore_api.unit_tests.controller;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import s91k.github.io.bookstore_api.controller.OrderController;
import s91k.github.io.bookstore_api.dto.OrderItemRequest;
import s91k.github.io.bookstore_api.dto.OrderRequest;
import s91k.github.io.bookstore_api.entity.Author;
import s91k.github.io.bookstore_api.entity.Book;
import s91k.github.io.bookstore_api.entity.Order;
import s91k.github.io.bookstore_api.entity.OrderItem;
import s91k.github.io.bookstore_api.service.OrderService;
import tools.jackson.databind.ObjectMapper;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderService orderService;

    @Test
    void findByIdShouldReturnOrderWhenOrderExists() throws Exception {
        Author a = new Author(0, "HG Wells");

        List<Book> books = List.of(
                new Book(0, "The War of the Worlds", a, new Date(1898, Calendar.FEBRUARY, 1), "It's a world of wars!"),
                new Book(1, "The Time Machine", a, new Date(1895, Calendar.FEBRUARY, 1), "It's a machine in time!")
        );

        Order o = new Order();

        o.setId("0");
        o.setFirstName("Nils");
        o.setLastName("Nilsson");
        o.setEmail("mejl@mejladress.se");
        o.setStreetAddress("Väggatan 5");
        o.setPostalCode("12345");
        o.setCity("Stadköping");
        o.setOrderItems(books.stream().map((b) -> new OrderItem(o, b, 1)).toList());

        when(orderService.findById("0")).thenReturn(o);

        mockMvc.perform(get("/orders/0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(o.getId()));
    }

    @Test
    void findByIdShouldReturnNotFoundWhenOrderDoesNotExist() throws Exception {
        when(orderService.findById("0")).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/orders/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createShouldReturnOrderResponseWhenOrderRequestIsValid() throws Exception {
        Book b = new Book(
                1,
                "The War of the Worlds",
                new Author(1, "HG Wells"),
                new Date(1898, Calendar.FEBRUARY, 1),
                "It's a world of wars!"
        );

        OrderRequest r = new OrderRequest(
                "Nils",
                "Nilsson",
                "mejl@mejladress.se",
                "Väggatan 5",
                "12345",
                "Stadköping",
                List.of(new OrderItemRequest(1, 1))
        );

        Order o = new Order();

        o.setId("123");
        o.setFirstName(r.firstName());
        o.setLastName(r.lastName());
        o.setEmail(r.email());
        o.setStreetAddress(r.streetAddress());
        o.setPostalCode(r.postalCode());
        o.setCity(r.city());
        o.setOrderItems(List.of(new OrderItem(o, b, 1)));

        when(orderService.create(any(OrderRequest.class))).thenReturn(o);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(r)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(o.getId()));
    }

    @Test
    void createShouldReturnBadRequestWhenOrderRequestIsInvalid() throws Exception {
        OrderRequest r = new OrderRequest(null, null, null, null, null, null, null);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(r)))
                .andExpect(status().isBadRequest());
    }
}
