package com.nanditha.medislot.dto;

import com.nanditha.medislot.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private String username;
    private String email;
    private String fullName;
    private User.Role role;
    private String message;
}