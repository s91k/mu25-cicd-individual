package s91k.github.io.bookstore_api.unit_tests.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import s91k.github.io.bookstore_api.dto.BookResponse;
import s91k.github.io.bookstore_api.entity.Author;
import s91k.github.io.bookstore_api.entity.Book;
import s91k.github.io.bookstore_api.repository.BookRepository;
import s91k.github.io.bookstore_api.service.BookService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void findALlShouldReturnAllBooks(){
        Author a = new Author(0, "HG Wells");

        List<Book> books = List.of(
                new Book(0, "The War of the Worlds", a, new Date(1898, Calendar.FEBRUARY, 1), "It's a world of wars!"),
                new Book(1, "The Time Machine", a, new Date(1895, Calendar.FEBRUARY, 1), "It's a machine in time!")
        );

        when(bookRepository.findAll()).thenReturn(books);

        List<BookResponse> bookResponses = bookService.findAll();

        assertEquals(bookResponses.size(), books.size());

        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void findByIdShouldReturnBookWhenBookExists() throws Exception {
        Author a = new Author(0, "HG Wells");
        Book b = new Book(0, "The War of the Worlds", a, new Date(1898, Calendar.FEBRUARY, 1), "It's a world of wars!");

        when(bookRepository.findById(0)).thenReturn(Optional.of(b));

        BookResponse bookResponse = bookService.findById(0);

        assertEquals(bookResponse.getId(), b.getId());

        verify(bookRepository, times(1)).findById(0);
    }

    @Test
    void findByIdShouldThrowExceptionWhenBookDoesNotExist() throws Exception {
        when(bookRepository.findById(0)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> bookService.findById(0));

        verify(bookRepository, times(1)).findById(0);
    }
}
