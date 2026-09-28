package s91k.github.io.bookstore_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import s91k.github.io.bookstore_api.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
