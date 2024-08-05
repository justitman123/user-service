package de.comparus.userservice.model;

import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String username;
    private String name;
    private String surname;
}
