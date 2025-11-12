package com.food.controllers;

import com.food.dto.request.AuthenticationRequestDTO;
import com.food.dto.request.UserRequestDTO;
import com.food.dto.response.ApiResponse;
import com.food.dto.response.AuthenticationResponseDTO;
import com.food.dto.response.UserDetailResponse;
import com.food.request.ForgotPasswordRequest;
import com.food.request.ResetPasswordRequest;
import com.food.services.JwtService;
import com.food.services.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.food.exception.ErrorCode;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {
    private final IUserService userService;
    private final JwtService jwtService;

    @GetMapping("/")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome to the application!");
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDetailResponse>> register(@RequestBody UserRequestDTO request) {
        log.info("Request to register user: {}", request.getEmail());

        UserDetailResponse userDetail = userService.saveUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(userDetail, "Registration success fully"));
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponseDTO>> login(@Valid @RequestBody AuthenticationRequestDTO request) {
        UserDetailResponse user = userService.authenticate(request);
        String token = jwtService.generateToken(
                user.getId().toString(),
                user.getEmail(),
                Collections.singletonList(user.getRole())
        );

        AuthenticationResponseDTO responseDTO = new AuthenticationResponseDTO(
                token,
                user.getEmail(),
                user.getRole(),
                3600
        );
        return ResponseEntity.ok(ApiResponse.success(responseDTO, "Login success fully"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        userService.processForgotPassword(request);
        return ResponseEntity.ok(ApiResponse.success(null, "If your email exists, a password reset email has been sent."));

    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        userService.resetPassword(request.getEmail(), request.getPhone(), request.getNewPassword());
        return ResponseEntity.ok("Password updated successfully");
    }

    @GetMapping("/oauth2-login")
    public ResponseEntity<ApiResponse<?>> loginError(
            @RequestParam(value = "error", required = false) String error) {

        if (error != null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error(
                            ErrorCode.UNAUTHENTICATED.getCode(),
                            "Google login failed",
                            HttpStatus.UNAUTHORIZED.value()));
        }

        return ResponseEntity.ok(ApiResponse.success("Data not found", "Please Login"));
    }


    @GetMapping("/google-callback")
    public ResponseEntity<ApiResponse<?>> googleCallback(@RequestParam("token") String token) {

        if (!jwtService.validateToken(token)) {
            throw new ResponseStatusException(
                    ErrorCode.INVALID_OR_EXPIRED_TOKEN.getStatusCode(),
                    ErrorCode.INVALID_OR_EXPIRED_TOKEN.getMessage()
            );
        }

        try {
            String email = jwtService.getUsernameFromToken(token);
            List<String> roles = jwtService.getRolesFromToken(token);
            String role = roles.isEmpty() ? "USER" : roles.get(0);

            AuthenticationResponseDTO responseDTO = new AuthenticationResponseDTO(
                    token, email, role, 3600
            );

            return ResponseEntity.ok(ApiResponse.success(responseDTO, "Login google success fully"));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    ErrorCode.TOKEN_PROCESSING_FAILED.getStatusCode(),
                    ErrorCode.TOKEN_PROCESSING_FAILED.getMessage()
            );
        }
    }

}