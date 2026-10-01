package s91k.github.io.bookstore_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(OrderItemId.class)
public class OrderItem {
    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

    @Id
    @ManyToOne
    @JoinColumn(name = "book_id")
    Book book;

    @Column(nullable = false)
    Integer quantity;
}
