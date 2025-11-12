package com.food.controllers;

import com.food.customexceptions.DataNotFoundException;
import com.food.request.OrderItemDTO;
import com.food.model.entities.OrderItem;
import com.food.response.OrderDetailResponse;
import com.food.services.impl.OrderItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/order_items")
public class OrderItemController {
    private final OrderItemService orderItemService;
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@Valid @PathVariable("id") Long id) throws DataNotFoundException{
        OrderItem orderItem = orderItemService.getOrderItem(id);
        return ResponseEntity.ok().body(OrderDetailResponse.from(orderItem));
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(@PathVariable("orderId") Long orderId) {
        List<OrderItem> orderItems = orderItemService.findByOrderId(orderId);
        List<OrderDetailResponse> orderDetailResponses = orderItems
                .stream()
                .map(OrderDetailResponse::from)
                .toList();
        return ResponseEntity.ok(orderDetailResponses);
    }
    @DeleteMapping("")
    public ResponseEntity<?> deleteOrderItem(@Valid @PathVariable("id") Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.ok().body("Đã xóa sản phẩm với id:: " + id+ " thành công");
    }
    @PostMapping("")
    public ResponseEntity<?> createOrderItem(@Valid @RequestBody OrderItemDTO orderItemDTO) {
        try{
            OrderItem orderItem = orderItemService.createOrderItem(orderItemDTO);
            return ResponseEntity.ok().body(OrderDetailResponse.from(orderItem));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
