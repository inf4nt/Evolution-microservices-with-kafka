package com.evolution.user.query.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
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
