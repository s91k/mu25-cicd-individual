package s91k.github.io.bookstore_api.unit_tests.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import s91k.github.io.bookstore_api.dto.OrderItemRequest;
import s91k.github.io.bookstore_api.dto.OrderRequest;
import s91k.github.io.bookstore_api.entity.Author;
import s91k.github.io.bookstore_api.entity.Book;
import s91k.github.io.bookstore_api.entity.Order;
import s91k.github.io.bookstore_api.entity.OrderItem;
import s91k.github.io.bookstore_api.repository.OrderRepository;
import s91k.github.io.bookstore_api.service.BookService;
import s91k.github.io.bookstore_api.service.OrderService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private BookService bookService;

    @InjectMocks
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
        o.setStreetAddress("Väggatan 5");
        o.setPostalCode("12345");
        o.setCity("Stadköping");
        o.setOrderItems(books.stream().map((b) -> new OrderItem(o, b, 1)).toList());

        when(orderRepository.findById("0")).thenReturn(Optional.of(o));

        Order result = orderService.findById("0");

        assertEquals(result.getId(), o.getId());

        verify(orderRepository, times(1)).findById("0");
    }

    @Test
    void findByIdShouldThrowExceptionWhenBookDoesNotExist() throws Exception {
        when(orderRepository.findById("0")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderService.findById("0"));

        verify(orderRepository, times(1)).findById("0");
    }

    @Test
    void createShouldReturnOrderWhenOrderRequestIsValid() {
        Book b = new Book(
                0,
                "The War of the Worlds",
                new Author(0, "HG Wells"),
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
                List.of(new OrderItemRequest(0, 1))
        );

        when(bookService.findById(0)).thenReturn(b);

        when(orderRepository.save(any(Order.class))).thenAnswer((Answer<Order>) invocationOnMock -> {
            Order savedOrder = invocationOnMock.getArgument(0);
            savedOrder.setId("123");
            return savedOrder;
        });

        Order result = orderService.create(r);

        assertEquals(result.getFirstName(), r.firstName());
        assertEquals(result.getOrderItems().size(), r.orderItems().size());

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void createShouldThrowExceptionWhenOrderItemsIsEmpty() {
        OrderRequest r = new OrderRequest(
                "Nils",
                "Nilsson",
                "mejl@mejladress.se",
                "Väggatan 5",
                "12345",
                "Stadköping",
                List.of()
        );

        assertThrows(IllegalArgumentException.class, () -> orderService.create(r));

        verify(orderRepository, times(0)).save(any(Order.class));
    }

    @Test
    void createShouldThrowExceptionWhenOrderItemsIsNull() {
        OrderRequest r = new OrderRequest(
                "Nils",
                "Nilsson",
                "mejl@mejladress.se",
                "Väggatan 5",
                "12345",
                "Stadköping",
               null
        );

        assertThrows(IllegalArgumentException.class, () -> orderService.create(r));

        verify(orderRepository, times(0)).save(any(Order.class));
    }
}