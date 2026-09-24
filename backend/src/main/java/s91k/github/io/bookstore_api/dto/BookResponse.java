package s91k.github.io.bookstore_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import s91k.github.io.bookstore_api.entity.Book;

@Data
@AllArgsConstructor
public class BookResponse {
    private Integer id;
    private String name;

    public static BookResponse toDto(Book b){
        return new BookResponse(b.getId(), b.getName());
    }
}
