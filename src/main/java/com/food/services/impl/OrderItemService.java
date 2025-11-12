package com.food.services.impl;

import com.food.customexceptions.DataNotFoundException;
import com.food.request.OrderItemDTO;
import com.food.model.entities.Order;
import com.food.model.entities.OrderItem;
import com.food.model.entities.Product;
import com.food.repositories.OrderItemRepository;
import com.food.repositories.OrderRepository;
import com.food.repositories.ProductRepository;
import com.food.services.IOrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService implements IOrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    @Override
    public OrderItem getOrderItem(Long orderItemId) {
        return orderItemRepository.findById(orderItemId).orElseThrow(() -> new DataNotFoundException("OrderItem not found"));
    }

    @Override
    public OrderItem createOrderItem(OrderItemDTO orderItemDTO) {
        Order order = orderRepository.findById(orderItemDTO.getOrderId()).orElseThrow(() -> new DataNotFoundException("Cannot find Order with id: " + orderItemDTO.getOrderId()));
        Product product = productRepository.findById(orderItemDTO.getProductId()).orElseThrow(() -> new DataNotFoundException("Cannot find Product: " + orderItemDTO.getProductId()));
        OrderItem orderItem = OrderItem.builder()
                .product(product)
                .order(order)
                .price(orderItemDTO.getPrice())
                .totalPrice(orderItemDTO.getTotalPrice())
                .quantity(orderItemDTO.getQuantity())
                .build();
        return orderItemRepository.save(orderItem);
    }

    @Override
    public List<OrderItem> findByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    @Override
    public void deleteOrderItem(Long orderItemId) {
        orderItemRepository.deleteById(orderItemId);
    }
}
