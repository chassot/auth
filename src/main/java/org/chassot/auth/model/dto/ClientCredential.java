package org.chassot.auth.model.dto;

import lombok.Data;

@Data
public class ClientCredential {
    private String secret;
    private String uid;
    private Type type;

    enum Type {
        USER,
        APP
    }

}