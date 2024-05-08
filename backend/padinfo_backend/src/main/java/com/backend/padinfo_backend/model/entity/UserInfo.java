package com.backend.padinfo_backend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Hidden
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "userInfo")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(unique = true, nullable = false)
    private String user;

    @Column(nullable = false)
    private String password;

    @Column
    private String name;

    @Column
    private String lastname;

    @Column
    private String email;

    @Column(name = "image_url")
    private String imageURL;

    @Column(nullable = false, name = "is_connected")
    //@Min(value = 0)
    //@Max(value = 1)
    private Integer isConnected;

    @JsonIgnoreProperties("userInfo")
    @OneToMany(mappedBy = "userInfo")
    private List<Game> games;
}
