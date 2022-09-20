package com.moises.todo.todorestapi.domain.service;

import com.moises.todo.todorestapi.domain.exception.TodoDirectoryNotFoundException;
import com.moises.todo.todorestapi.domain.model.TodoDirectory;
import com.moises.todo.todorestapi.domain.repository.TodoDirectoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoDirectoryService {

    @Autowired
    private TodoDirectoryRepo todoDirectoryRepo;

    public TodoDirectory findOrFail(String ownerCode) {
        return todoDirectoryRepo.findByOwnerCode(ownerCode)
                .orElseThrow(() -> new TodoDirectoryNotFoundException(ownerCode));
    }

    public TodoDirectory update(TodoDirectory todoDirectory) {

        todoDirectory.setOwnerCode(todoDirectory.getOwnerCode());
        todoDirectory.setOwnerUsername(todoDirectory.getOwnerUsername());

        return todoDirectoryRepo.save(todoDirectory);

    };

}
