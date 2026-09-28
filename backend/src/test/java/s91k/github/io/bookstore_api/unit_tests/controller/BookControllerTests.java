package s91k.github.io.bookstore_api.unit_tests.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import s91k.github.io.bookstore_api.controller.BookController;
import s91k.github.io.bookstore_api.dto.BookResponse;
import s91k.github.io.bookstore_api.service.BookService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Test
    void getBooksShouldReturnAllBooks() throws Exception {
        List<BookResponse> books = List.of(
                new BookResponse(0, "The War of the Worlds", "HG Wells", new Date(1898, Calendar.FEBRUARY, 1), "It's a world of wars!"),
                new BookResponse(1, "The Time Machine", "HG Wells", new Date(1895, Calendar.FEBRUARY, 1), "It's a machine in time!")
        );

        when(bookService.findAll()).thenReturn(books);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getBookByIdShouldReturnBookWhenBookExists() throws Exception {
        BookResponse b = new BookResponse(0, "The War of the Worlds", "HG Wells", new Date(1898, Calendar.FEBRUARY, 1), "It's a world of wars!");

        when(bookService.findById(0)).thenReturn(b);

        mockMvc.perform(get("/books/0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(b.getId()));
    }

    @Test
    void getBookByIdShouldReturnNotFoundWhenBookDoesNotExist() throws Exception {
        when(bookService.findById(0)).thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/books/0"))
                .andExpect(status().isNotFound());
    }
}
