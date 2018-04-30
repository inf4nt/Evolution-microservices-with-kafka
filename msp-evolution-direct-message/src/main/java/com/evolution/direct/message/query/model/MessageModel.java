package com.evolution.direct.message.query.model;

import com.evolution.direct.message.share.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "message")
public class MessageModel {

    @Id
    String key;

    @Indexed(unique = true)
    String eventNumber;

    @NotEmpty
    String text;

    @NotEmpty
    User sender;

    @NotEmpty
    User recipient;
}
