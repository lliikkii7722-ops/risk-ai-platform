package com.riskai.riskaiplatform.dto;

import com.riskai.riskaiplatform.entity.UserRole;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private UserRole role;
}