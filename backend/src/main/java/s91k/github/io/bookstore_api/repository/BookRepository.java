package s91k.github.io.bookstore_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import s91k.github.io.bookstore_api.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
