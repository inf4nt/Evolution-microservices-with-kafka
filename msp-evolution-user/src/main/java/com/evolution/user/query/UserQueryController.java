package com.evolution.user.query;

import com.evolution.library.core.v4.MessageService;
import com.evolution.user.core.UserState;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.kafka.streams.QueryableStoreRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/state")
public class UserQueryController {

    private final QueryableStoreRegistry queryableStoreRegistry;

    @Autowired
    public UserQueryController(QueryableStoreRegistry queryableStoreRegistry) {
        this.queryableStoreRegistry = queryableStoreRegistry;
    }

    @GetMapping
    public ResponseEntity<KeyValueIterator<String, UserState>> findAll() {
        final ReadOnlyKeyValueStore<String, UserState> store =
                queryableStoreRegistry.getQueryableStoreType(MessageService.getStore(UserState.class), QueryableStoreTypes.keyValueStore());
        return ResponseEntity.ok(store.all());
    }

    @GetMapping(value = "/{key}")
    public ResponseEntity<UserState> findByKey(@PathVariable String key) {
        final ReadOnlyKeyValueStore<String, UserState> store =
                queryableStoreRegistry.getQueryableStoreType(MessageService.getStore(UserState.class), QueryableStoreTypes.keyValueStore());
        return ResponseEntity.ok(store.get(key));
    }
}
