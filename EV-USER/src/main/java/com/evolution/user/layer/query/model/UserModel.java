package com.evolution.user.layer.query.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user_model")
public class UserModel {

    @Id
    String key;

    @Indexed(unique = true)
    String eventNumber;

    @Indexed(unique = true)
    String username;

    String password;

    String firstName;

    String lastName;
}
