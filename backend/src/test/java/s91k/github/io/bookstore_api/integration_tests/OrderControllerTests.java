package s91k.github.io.bookstore_api.integration_tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.BeforeEach;
import s91k.github.io.bookstore_api.dto.OrderItemRequest;
import s91k.github.io.bookstore_api.dto.OrderRequest;
import s91k.github.io.bookstore_api.entity.Author;
import s91k.github.io.bookstore_api.entity.Book;
import s91k.github.io.bookstore_api.entity.Order;
import s91k.github.io.bookstore_api.entity.OrderItem;
import s91k.github.io.bookstore_api.repository.AuthorRepository;
import s91k.github.io.bookstore_api.repository.BookRepository;
import s91k.github.io.bookstore_api.repository.OrderRepository;
import tools.jackson.databind.ObjectMapper;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OrderControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OrderRepository orderRepository;

    private Book book;

    @BeforeEach
    void setUp(){
        Author a = authorRepository.save(new Author(null, "HG Wells"));
        book = new Book(null, "The War of the Worlds", a, new Date(1898, Calendar.FEBRUARY, 1), "It's a world of wars!");

        bookRepository.save(book);

        orderRepository.deleteAll();
    }

    @Test
    void getOrderByIdShouldReturnOrder() throws Exception {
        Order o = new Order();

        o.setFirstName("Nils");
        o.setLastName("Nilsson");
        o.setEmail("mejl@mejladress.se");
        o.setStreetAddress("Väggatan 5");
        o.setPostalCode("12345");
        o.setCity("Stadköping");
        o.setOrderItems(List.of(new OrderItem(o, book, 1)));

        orderRepository.save(o);

        mockMvc.perform(get("/orders/" + o.getId())).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(o.getId()));
    }

    @Test
    void createShouldReturnOrderResponse() throws Exception {
        OrderRequest r = new OrderRequest(
                "Nils",
                "Nilsson",
                "mejl@mejladress.se",
                "Väggatan 5",
                "12345",
                "Stadköping",
                List.of(new OrderItemRequest(1, 1))
        );

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(r)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(r.email()));
    }
}
