package com.backend.padinfo_backend.dto.tournament;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TournamentDTO implements Serializable {
    private Long id;
    private String name;
    private String city;
    private String imageURL;
}
