package org.chassot.auth.model.dto;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class UserLoggedIn {
    private UserDetails user;
    private String token;
    private String msg;

    public UserLoggedIn(UserDetails user, String token, String msg) {
        this.user = user;
        this.token = token;
        this.msg = msg;
    }

    public UserLoggedIn() {
    }
}
