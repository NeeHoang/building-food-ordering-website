package com.food.services;
import java.util.List;
import com.food.request.OrderItemDTO;
import com.food.model.entities.OrderItem;
import org.springframework.stereotype.Service;

@Service
public interface IOrderItemService {
    OrderItem getOrderItem(Long orderItemId);
    OrderItem createOrderItem(OrderItemDTO orderItemDTO);
    List<OrderItem> findByOrderId(Long orderId);
    void deleteOrderItem(Long orderItemId);
}
