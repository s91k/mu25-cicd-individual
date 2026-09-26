package s91k.github.io.bookstore_api.service;

import org.springframework.stereotype.Service;
import s91k.github.io.bookstore_api.dto.BookResponse;
import s91k.github.io.bookstore_api.repository.BookRepository;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    public List<BookResponse> findAll(){
        return this.bookRepository.findAll().stream().map(BookResponse::toDto).toList();
    }

    public BookResponse findById(int id){
        return BookResponse.toDto(this.bookRepository.findById(id).orElseThrow());
    }
}
