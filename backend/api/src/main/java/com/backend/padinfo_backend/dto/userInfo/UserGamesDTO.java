package com.backend.padinfo_backend.dto.userInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGamesDTO implements Serializable {
    private Long id;
    private List<Long> idGames;
}
