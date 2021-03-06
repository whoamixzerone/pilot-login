package com.example.pilotlogin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @NotEmpty
    private String account;
    @NotEmpty
    private String password;
}
