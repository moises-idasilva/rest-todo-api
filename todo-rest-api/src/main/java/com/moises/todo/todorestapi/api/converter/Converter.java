package com.moises.todo.todorestapi.api.converter;

import java.util.List;

public interface Converter<T, S, U> {

    public T toEntity(U inputDto);

    public S toDto(T domain);

    public List<S> toCollectionDTO(List<T> list);

}
