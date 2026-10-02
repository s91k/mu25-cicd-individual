package s91k.github.io.bookstore_api.dto;

import s91k.github.io.bookstore_api.entity.Order;

import java.util.List;

public record OrderResponse(
        String id,
        String firstName,
        String lastName,
        String email,
        String streetAddress,
        String postalCode,
        String city,
        List<OrderItemResponse> orderItems
) {
    public static OrderResponse toDto(Order o){
        return new OrderResponse(
                o.getId(),
                o.getFirstName(),
                o.getLastName(),
                o.getEmail(),
                o.getStreetAddress(),
                o.getPostalCode(),
                o.getCity(),
                o.getOrderItems().stream().map((item) -> new OrderItemResponse(
                        item.getBook().getId(),
                        item.getBook().getName(),
                        item.getQuantity())
                ).toList()
        );
    }
}
