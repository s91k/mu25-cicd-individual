package s91k.github.io.bookstore_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import s91k.github.io.bookstore_api.entity.Book;

import java.util.Date;

@Data
@AllArgsConstructor
public class BookResponse {
    private Integer id;
    private String name;
    private String author_name;
    private Date releaseDate;
    private String description;

    public static BookResponse toDto(Book b){
        return new BookResponse(b.getId(), b.getName(), b.getAuthor().getName(), b.getReleaseDate(), b.getDescription());
    }
}
