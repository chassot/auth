package org.chassot.auth.model.dto;

import lombok.Data;

@Data
public class NewUser extends User{
    private String password;
}
