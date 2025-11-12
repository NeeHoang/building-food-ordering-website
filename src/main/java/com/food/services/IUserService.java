package com.food.services;

import com.food.dto.request.AuthenticationRequestDTO;
import com.food.dto.request.UserRequestDTO;
import com.food.dto.response.UserDetailResponse;
import com.food.request.ForgotPasswordRequest;
import org.springframework.security.core.AuthenticationException;

import java.util.List;

public interface IUserService {

    UserDetailResponse saveUser(UserRequestDTO request);

    void updateUser(long userId, UserRequestDTO request);

    void deleteUser(long userId);

    List<UserDetailResponse> getAlluser();

    UserDetailResponse getUserById(long userId);

    Boolean isEmailExist(String email);

    UserDetailResponse authenticate(AuthenticationRequestDTO request) throws AuthenticationException;

    UserDetailResponse saveOrUpdateGoogleUser(String email, String name);

    void processForgotPassword(ForgotPasswordRequest request);

    void resetPassword(String email, String phone, String newPassword);

}
