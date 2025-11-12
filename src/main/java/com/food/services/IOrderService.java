package com.food.services;

import java.util.List;
import com.food.request.OrderDTO;
import com.food.model.entities.Order;
import org.springframework.stereotype.Service;

@Service
public interface IOrderService {
    Order getOrderById(Long id);
    Order createOrder(OrderDTO orderDTO) throws Exception;
    Order updateOrder(Long id,OrderDTO orderDTO) throws Exception;
    void deleteOrder(Long id) throws Exception;
    void updateOrderStatus(Long orderId, String status);
    List<Order> findByUserId(Long userId);
}
