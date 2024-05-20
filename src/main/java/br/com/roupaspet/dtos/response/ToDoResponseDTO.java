package br.com.roupaspet.dtos.response;

import br.com.roupaspet.models.ToDo;

public record ToDoResponseDTO(
        Long id,
        String title,
        String description,
        boolean isComplete
) {

    public ToDoResponseDTO(ToDo todo) {
        this(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.isComplete()
        );
    }
}
