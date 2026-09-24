package s91k.github.io.bookstore_api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;
}
