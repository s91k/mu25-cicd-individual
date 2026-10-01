package s91k.github.io.bookstore_api.unit_tests.controller;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import s91k.github.io.bookstore_api.controller.BookController;
import s91k.github.io.bookstore_api.entity.Author;
import s91k.github.io.bookstore_api.entity.Book;
import s91k.github.io.bookstore_api.service.BookService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        Author a = new Author(0, "HG Wells");

        List<Book> books = List.of(
                new Book(0, "The War of the Worlds", a, new Date(1898, Calendar.FEBRUARY, 1), "It's a world of wars!"),
                new Book(1, "The Time Machine", a, new Date(1895, Calendar.FEBRUARY, 1), "It's a machine in time!")
        );

        when(bookService.findAll()).thenReturn(books);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getBookByIdShouldReturnBookWhenBookExists() throws Exception {
        Author a = new Author(0, "HG Wells");
        Book b = new Book(0, "The War of the Worlds", a, new Date(1898, Calendar.FEBRUARY, 1), "It's a world of wars!");

        when(bookService.findById(0)).thenReturn(b);

        mockMvc.perform(get("/books/0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(b.getId()));
    }

    @Test
    void getBookByIdShouldReturnNotFoundWhenBookDoesNotExist() throws Exception {
        when(bookService.findById(0)).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/books/0"))
                .andExpect(status().isNotFound());
    }
}
