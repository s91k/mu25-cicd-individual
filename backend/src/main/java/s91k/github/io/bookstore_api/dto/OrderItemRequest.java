package s91k.github.io.bookstore_api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemRequest(
        @NotNull
        @Positive
        Integer bookId,
        @NotNull
        @Positive
        @Max(99)
        Integer quantity
) { }
