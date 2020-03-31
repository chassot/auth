package org.chassot.auth.model.dto;

import lombok.Data;

@Data
public class NewUser  {
    private String email;
    private String name;
    private String password;
}
