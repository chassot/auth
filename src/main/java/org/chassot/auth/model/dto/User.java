package org.chassot.auth.model.dto;

import lombok.Data;

@Data
public class User {
    private String email;
    private String name;
    private String token;
}
