package s91k.github.io.bookstore_api.integration_tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.BeforeEach;
import s91k.github.io.bookstore_api.entity.Author;
import s91k.github.io.bookstore_api.entity.Book;
import s91k.github.io.bookstore_api.repository.AuthorRepository;
import s91k.github.io.bookstore_api.repository.BookRepository;


import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp(){
        Author a = authorRepository.save(new Author(null, "HG Wells"));

        bookRepository.save(new Book(null, "The War of the Worlds", a, new Date(1898, Calendar.FEBRUARY, 1), "It's a world of wars!"));
        bookRepository.save(new Book(null, "The Time Machine", a, new Date(1895, Calendar.FEBRUARY, 1), "It's a machine in time!"));
    }

    @Test
    void getBooksShouldReturnAllBooks() throws Exception {
        mockMvc.perform(get("/books")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getBookByIdWhenBookExistsShouldReturnBook() throws Exception {
        mockMvc.perform(get("/books/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getBookByIdWhenBookDoesNotExistShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/books/3")).andExpect(status().isNotFound());
    }
}
