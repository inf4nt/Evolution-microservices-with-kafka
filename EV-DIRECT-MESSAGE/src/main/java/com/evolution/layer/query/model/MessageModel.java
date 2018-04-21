package com.evolution.layer.query.model;

import com.evolution.core.share.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

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

    String text;

    User sender;

    User recipient;
}
