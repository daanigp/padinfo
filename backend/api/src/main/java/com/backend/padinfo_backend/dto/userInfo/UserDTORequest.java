package com.backend.padinfo_backend.dto.userInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTORequest implements Serializable {
    private String username;
    private String password;
    private List<Long> rolIds;
}
