package com.evolution.user.layer.query.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class UserState {

    @Id
    String id;

    @Indexed(unique = true)
    String eventId;

    String username;

    String password;

    String firstName;

    String lastName;

    String nickname;

    Date datePost;

    Date datePut;
}
