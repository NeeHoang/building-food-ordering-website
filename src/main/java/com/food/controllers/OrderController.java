package com.food.controllers;

import java.util.List;
import java.util.stream.*;
import com.food.request.OrderDTO;
import com.food.model.entities.Order;
import com.food.services.impl.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@Valid @PathVariable("id") Long orderId) {
        try{
            Order existingOrder = orderService.getOrderById(orderId);
            return ResponseEntity.ok(existingOrder);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO, BindingResult bindingResult) {
        try{
            if(bindingResult.hasErrors()){
                List<String> errorMessage = bindingResult.getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
                return ResponseEntity.badRequest().body(errorMessage);
            }
            Order orderReponse = orderService.createOrder(orderDTO);
            return ResponseEntity.ok(orderReponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //Chỉnh sửa trạng thái đơn hàng(Cho admin)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@Valid @RequestBody OrderDTO orderDTO, @Valid @PathVariable("id") Long orderId) {
        try{
            Order order = orderService.updateOrder(orderId,orderDTO);
            return ResponseEntity.ok(order);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Admin xoa don hang
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@Valid @PathVariable("id") Long orderId) throws Exception {
        orderService.deleteOrder(orderId); //Xóa mềm => chỉnh trạng thái sang false
        return ResponseEntity.ok("Order deleted");
    }

    //Lấy danh sách đơn hàng đã mua
    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getOrders(@Valid @PathVariable Long user_id) {
        try{
            List<Order> orders = orderService.findByUserId(user_id);
            return ResponseEntity.ok(orders);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
