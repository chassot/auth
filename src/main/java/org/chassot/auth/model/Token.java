package org.chassot.auth.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Token implements Serializable {

    private final String jwttoken;

}
