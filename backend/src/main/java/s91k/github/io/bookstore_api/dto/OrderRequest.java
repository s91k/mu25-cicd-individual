package s91k.github.io.bookstore_api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record OrderRequest(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String streetAddress,
        @NotNull
        @Pattern(regexp = "^[0-9]{3} ?[0-9]{2}$")
        String postalCode,
        @NotBlank()
        String city,
        @NotNull
        @NotEmpty
        List<@Valid OrderItemRequest> orderItems
) { }
