package com.evolution.library.core.v4;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QueryController<S extends StateEvent> {

    ResponseEntity<List<S>> findAll();

    ResponseEntity<S> findByKey(String key);
}
