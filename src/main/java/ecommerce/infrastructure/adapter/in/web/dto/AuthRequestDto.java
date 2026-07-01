package ecommerce.infrastructure.adapter.in.web.dto;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String email;
    private String password;
}