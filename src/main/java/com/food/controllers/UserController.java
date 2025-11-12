package com.food.controllers;

import com.food.dto.request.UserRequestDTO;
import com.food.dto.response.ApiResponse;
import com.food.dto.response.UserDetailResponse;
import com.food.model.context.UserContext;
import com.food.services.IUserService;
import com.food.services.JwtService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    private final JwtService jwtService;

    private UserContext extractUserContext(String bearerToken) {
        Long userId = null;
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            try {
                String token = bearerToken.substring(7); // remove "Bearer "
                String userIdStr = jwtService.extractUserId(token);
                userId = Long.parseLong(userIdStr);
            } catch (Exception e) {
                log.warn("Failed to extract userId from token", e);
            }
        }
        return new UserContext(userId);
    }

    @PostMapping("/adduser")
    public ResponseEntity<ApiResponse<UserDetailResponse>> addUser(
            @RequestBody UserRequestDTO user,
            @RequestHeader("Authorization") String bearerToken) {

        UserContext userContext = extractUserContext(bearerToken);
        log.info("Request add user: {} by userId={}", user.getName(), userContext.getUserId());

        UserDetailResponse response = userService.saveUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "Add user success"));
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable @Min(1) long userId,
                             @RequestHeader("Authorization") String bearerToken) {

        UserContext userContext = extractUserContext(bearerToken);
        log.info("Request delete userId={} by userIdFromToken={}", userId, userContext.getUserId());

        userService.deleteUser(userId);
        return "User has deleted: " + userId;
    }

    @GetMapping("/{userId}")
    public UserDetailResponse getUser(@PathVariable @Min(1) long userId,
                                      @RequestHeader("Authorization") String bearerToken) {

        UserContext userContext = extractUserContext(bearerToken);
        log.info("Request get User by Id = {} from userId={}", userId, userContext.getUserId());

        return userService.getUserById(userId);
    }

    @GetMapping("/")
    public List<UserDetailResponse> getAllUser(@RequestHeader("Authorization") String bearerToken) {
        UserContext userContext = extractUserContext(bearerToken);
        log.info("Request get all users by userId={}", userContext.getUserId());

        return userService.getAlluser();
    }

    @PutMapping("/{userId}")
    public String updateUser(@PathVariable @Min(1) Long userId,
                             @RequestBody UserRequestDTO userRequestDTO,
                             @RequestHeader("Authorization") String bearerToken) {

        UserContext userContext = extractUserContext(bearerToken);
        log.info("Update userId={} requested by userId={}", userId, userContext.getUserId());

        userService.updateUser(userId, userRequestDTO);
        return ("User with Id = " + userId + " has been updated");
    }
}
