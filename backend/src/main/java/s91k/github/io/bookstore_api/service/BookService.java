package s91k.github.io.bookstore_api.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import s91k.github.io.bookstore_api.dto.BookResponse;
import s91k.github.io.bookstore_api.entity.Book;
import s91k.github.io.bookstore_api.repository.BookRepository;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(){
        return this.bookRepository.findAll();
    }

    public Book findById(int id){
        return this.bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " not found."));
    }
}
