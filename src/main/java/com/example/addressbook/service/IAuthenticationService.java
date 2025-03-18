package com.example.addressbook.service;
import com.example.addressbook.dto.AuthUserDTO;
import com.example.addressbook.dto.LoginDTO;
import com.example.addressbook.model.AuthUser;


public interface IAuthenticationService {
    AuthUser register(AuthUserDTO userDTO) throws Exception;

    String login(LoginDTO loginDTO);
    String forgotPassword(String email, String newPassword);
    String resetPassword(String email, String currentPassword, String newPassword);
}
