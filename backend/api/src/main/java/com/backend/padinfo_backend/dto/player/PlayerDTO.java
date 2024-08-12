package com.backend.padinfo_backend.dto.player;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO implements Serializable {
    private Long id;
    private String gender;
    private Integer rankingPosition;
    private String points;
    private String name;
    private String imageURL;
}
