package com.food.services.impl;

import com.food.dto.request.AuthenticationRequestDTO;
import com.food.dto.request.UserRequestDTO;
import com.food.dto.response.UserDetailResponse;
import com.food.exception.ErrorCode;
import com.food.model.entities.Role;
import com.food.model.entities.User;
import com.food.repositories.RoleRepository;
import com.food.repositories.UserRepository;
import com.food.request.ForgotPasswordRequest;
import com.food.services.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final MailService mailService;

    @Override
    public void processForgotPassword(ForgotPasswordRequest request) {
        String email = request.getEmail();
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            log.warn("Attempt to reset password for non-existing email: {}", email);
            return;
        }

        User user = optionalUser.get();
        String newPassword = generateRandomPassword();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        mailService.sendResetPasswordEmail(email, newPassword);
    }

    private String generateRandomPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }


    @Override
    public UserDetailResponse saveUser(UserRequestDTO request) {
        if (isEmailExist(request.getEmail())) {
            throw new ResponseStatusException(
                    ErrorCode.EMAIL_ALREADY_EXISTS.getStatusCode(),
                    ErrorCode.EMAIL_ALREADY_EXISTS.getMessage()
            );
        }

        Role role = roleRepository.findById(1L)
                .orElseThrow(() -> new ResponseStatusException(
                        ErrorCode.ROLE_NOT_FOUND.getStatusCode(),
                        ErrorCode.ROLE_NOT_FOUND.getMessage()
                ));

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        userRepository.save(user);

        return UserDetailResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .role(role.getName())
                .build();
    }

    @Override
    public void resetPassword(String email, String phone, String newPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(ErrorCode.NOT_FOUND.getStatusCode(), "User not found");
        }

        User user = userOptional.get();

        if (!user.getPhone().equals(phone)) {
            throw new ResponseStatusException(ErrorCode.UNAUTHENTICATED.getStatusCode(), "Phone number does not match");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void updateUser(long userId, UserRequestDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        ErrorCode.NOT_FOUND.getStatusCode(),
                        "User not found"
                ));

        if (isEmailExist(request.getEmail()) && !user.getEmail().equals(request.getEmail())) {
            throw new ResponseStatusException(
                    ErrorCode.EMAIL_ALREADY_EXISTS.getStatusCode(),
                    ErrorCode.EMAIL_ALREADY_EXISTS.getMessage()
            );
        }

        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setAddress(request.getAddress());

        userRepository.save(user);
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserDetailResponse> getAlluser() {
        return userRepository.findAll().stream()
                .map(user -> UserDetailResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .phone(user.getPhone())
                        .email(user.getEmail())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public UserDetailResponse getUserById(long userId) {
        return userRepository.findById(userId)
                .map(user -> UserDetailResponse.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .phone(user.getPhone())
                        .email(user.getEmail())
                        .address(user.getAddress())
                        .build())
                .orElse(null);
    }

    @Override
    public Boolean isEmailExist(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDetailResponse authenticate(AuthenticationRequestDTO request) {
        log.info("Authenticating user with email: {}", request.getEmail());
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isEmpty()) {
            log.warn("Authentication failed: User not found for email {}", request.getEmail());
            throw new ResponseStatusException(ErrorCode.UNAUTHENTICATED.getStatusCode(), ErrorCode.UNAUTHENTICATED.getMessage());
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.warn("Authentication failed: Incorrect password for email {}", request.getEmail());
            throw new ResponseStatusException(ErrorCode.UNAUTHENTICATED.getStatusCode(), ErrorCode.UNAUTHENTICATED.getMessage());
        }

        log.info("Authentication successful for email: {}", request.getEmail());

        return UserDetailResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .build();
    }


    @Override
    public UserDetailResponse saveOrUpdateGoogleUser(String email, String name) {
        // Kiểm tra xem email đã tồn tại chưa
        Optional<User> existingUser = userRepository.findByEmail(email);

        User user;
        if (existingUser.isPresent()) {
            // Nếu user đã tồn tại, cập nhật tên nếu cần
            user = existingUser.get();
            if (!user.getName().equals(name)) {
                user.setName(name);
                userRepository.save(user);
                log.info("Updated Google user with email: {}", email);
            }
        } else {
            // Nếu user chưa tồn tại, tạo mới
            Role role = roleRepository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("Role with ID 1 (USER) not found"));

            user = User.builder()
                    .name(name)
                    .email(email)
                    .password(passwordEncoder.encode("google-auth-" + UUID.randomUUID())) // Password giả
                    .role(role)
                    .build();

            userRepository.save(user);
            log.info("Saved new Google user with email: {}", email);
        }

        // Trả về thông tin user để tạo JWT
        return UserDetailResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .build();
    }
}