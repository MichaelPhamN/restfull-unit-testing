package com.example.scenariothree.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements Serializable {
    private Integer id;
    private String email;
    private String password;
    private Boolean isAdmin;
}