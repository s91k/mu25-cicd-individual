package s91k.github.io.bookstore_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import s91k.github.io.bookstore_api.entity.OrderItem;
import s91k.github.io.bookstore_api.entity.OrderItemId;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {
    List<OrderItem> findByOrderId(Integer orderId);
}
