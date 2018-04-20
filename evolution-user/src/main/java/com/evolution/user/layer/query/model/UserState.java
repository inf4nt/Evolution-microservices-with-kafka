package com.evolution.user.layer.query.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class UserState {

    @Id
    String id;

    @Indexed(unique = true)
    String eventId;

    @Indexed(unique = true)
    String username;

    String password;

    String firstName;

    String lastName;

    String nickname;

    Date datePost;

    Date datePut;



//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
//        UserState userState = (UserState) o;
//        return Objects.equals(id, userState.id);
//    }
//
//    @Override
//    public int hashCode() {
//
//        return Objects.hash(super.hashCode(), id);
//    }
}
