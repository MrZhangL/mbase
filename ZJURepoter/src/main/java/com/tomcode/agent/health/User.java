package com.tomcode.agent.health;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class User {

    private String username;
    private String password;
    private String email;

}
