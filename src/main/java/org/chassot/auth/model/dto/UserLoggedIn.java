package org.chassot.auth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoggedIn {
    private UserInfo user;
    private String token;
    private String msg;

}
