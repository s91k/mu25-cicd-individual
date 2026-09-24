package s91k.github.io.bookstore_api.unit_tests.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import s91k.github.io.bookstore_api.dto.BookResponse;
import s91k.github.io.bookstore_api.entity.Book;
import s91k.github.io.bookstore_api.repository.BookRepository;
import s91k.github.io.bookstore_api.service.BookService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void shouldReturnAllBooks(){
        List<Book> books = List.of(
                new Book(0, "The War of the Worlds"),
                new Book(1, "The Time Machine")
        );

        when(bookRepository.findAll()).thenReturn(books);

        List<BookResponse> bookResponses = bookService.findAll();

        assertEquals(bookResponses.size(), books.size());

        verify(bookRepository).findAll();
    }
}
