package com.evolution.direct.message.layer.query.model;

import com.evolution.direct.message.share.User;
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
@Document(collection = "message")
public class Message {

    String id;

    @Indexed(unique = true)
    String eventId;

    User sender;

    User recipient;

    String text;

    Date postDate;

    Date putDate;

    boolean isRead;
}
