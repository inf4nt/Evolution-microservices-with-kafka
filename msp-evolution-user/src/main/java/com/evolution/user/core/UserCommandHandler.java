package com.evolution.user.core;

import com.evolution.library.core.v4.CommandHandler;
import com.evolution.user.core.common.UserEventStatus;
import com.evolution.library.core.v4.MessageService;
import com.evolution.user.core.common.UserRequestTypes;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.QueryableStoreRegistry;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import static com.evolution.user.core.common.UserEventStatus.Fail;
import static com.evolution.user.core.common.UserEventStatus.Progress;

@Service
public class UserCommandHandler implements CommandHandler<UserCommand, UserEvent> {

    private final QueryableStoreRegistry queryableStoreRegistry;

    @Autowired
    public UserCommandHandler(QueryableStoreRegistry queryableStoreRegistry) {
        this.queryableStoreRegistry = queryableStoreRegistry;
    }

    @Override
    public UserEvent handle(@Valid UserCommand command) {
        //todo по сути каждая операция команда в евент работает одинаково. Скорее смысл в том чтоб валидировать ее каким-то образом

        final ReadOnlyKeyValueStore<String, UserState> store =
                queryableStoreRegistry.getQueryableStoreType(MessageService.getStore(UserState.class), QueryableStoreTypes.keyValueStore());

        @NotEmpty UserRequestTypes type = command.getRequestType();
        UserEvent res = UserEvent.builder().build();
        UserEventStatus status = null;

        switch (type) {
            case UserUpdateFirstNameRequest: {
                UserState state = store.get(command.getKey());
                if (state == null) {
                   status = Fail;
                } else {
                    status = Progress;
                }
                break;
            }
            case UserCreateRequest: {
                KeyValueIterator<String, UserState> iterator = store.all();
                while (iterator.hasNext()) {
                    if (iterator.next().value.getDomain().getUsername().equals(command.getDomain().getUsername())) {
                        status = Fail;
                        break;
                    }
                }
                if (status != Fail) {
                    status = Progress;
                }

                break;
            }
            default:
                throw new UnsupportedOperationException("Not found request type by " + type);
        }

        return res
                .withRequestType(type)
                .withEventStatus(status)
                .withCorrelation(MessageService.random())
                .withOperationNumber(command.getOperationNumber())
                .withDomain(command.getDomain());
    }
}
