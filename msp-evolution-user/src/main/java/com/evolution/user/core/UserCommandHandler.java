package com.evolution.user.core;

import com.evolution.library.core.v5.CommandHandler;
import com.evolution.library.core.v5.MessageService;
import com.evolution.user.core.common.UserEventStatus;
import com.evolution.user.core.common.UserRequestTypes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.QueryableStoreRegistry;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

import static com.evolution.user.core.common.UserEventStatus.*;

@Service
public class UserCommandHandler implements CommandHandler<UserCommand, UserEvent> {

    private final QueryableStoreRegistry queryableStoreRegistry;

    @Autowired
    public UserCommandHandler(QueryableStoreRegistry queryableStoreRegistry) {
        this.queryableStoreRegistry = queryableStoreRegistry;
    }

    @Override
    public UserEvent handle(@Valid UserCommand command) {
        final ReadOnlyKeyValueStore<String, UserState> store = queryableStoreRegistry
                .getQueryableStoreType(MessageService.getStore(UserState.class), QueryableStoreTypes.keyValueStore());

        UserRequestTypes type = command.getType();

        UserEvent event = UserEvent.builder().build();
        UserState state;
        UserEventStatus status = PROGRESS;
        switch (type) {
            case UserUpdateFirstNameRequest:
                state = store.get(command.getKey());
                if (state == null) {
                    status = USER_BY_KEY_NOT_FOUND;
                }
                break;
            case UserCreateRequest:
                KeyValueIterator<String, UserState> iterator = store.all();
                String username = command.getContent().getUsername();
                while (iterator.hasNext()) {
                    UserState s = iterator.next().value;
                    if (s.getContent() != null && s.getContent().getUsername().equals(username)) {
                        status = USER_BY_USER_NAME_ALREADY_EXIST;
                        break;
                    }
                }
                break;
        }

        return event
                .withKey(command.getKey())
                .withCorrelation(MessageService.random())
                .withOperationNumber(command.getOperationNumber())
                .withType(type)
                .withContent(command.getContent())
                .withEventStatus(status);
    }
}
