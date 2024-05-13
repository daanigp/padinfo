package com.backend.padinfo_backend.dto.userInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {
    private Long id;
    private String user;
    private String password;
    private String name;
    private String lastname;
    private String email;
    private String imageURL;
}
