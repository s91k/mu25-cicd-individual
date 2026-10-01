package s91k.github.io.bookstore_api.dto;

import s91k.github.io.bookstore_api.entity.Book;

import java.util.Date;

public record BookResponse(Integer id, String name, String author_name, Date releaseDate, String description){
    public static BookResponse toDto(Book b){
        return new BookResponse(b.getId(), b.getName(), b.getAuthor().getName(), b.getReleaseDate(), b.getDescription());
    }
}
