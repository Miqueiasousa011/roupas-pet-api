package br.com.roupaspet.services;

import br.com.roupaspet.dtos.request.ToDoRequestDTO;
import br.com.roupaspet.dtos.request.ToDoUpdateRequestDTO;
import br.com.roupaspet.dtos.response.ToDoResponseDTO;
import br.com.roupaspet.models.ToDo;
import br.com.roupaspet.models.User;
import br.com.roupaspet.repositories.ToDoRepository;
import br.com.roupaspet.services.exceptions.APIException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ToDoService {
    private final ToDoRepository toDoRepository;

    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @Transactional
    public List<ToDoResponseDTO> getAll(User user) {
        return toDoRepository.findAllByUserAndIsActive(user, true)
                .stream()
                .map(ToDoResponseDTO::new)
                .toList();
    }

    @Transactional
    public ToDoResponseDTO save(ToDoRequestDTO dto, User user) {
        if (Objects.isNull(user)) {
            throw new APIException("error");
        }

        var todo = new ToDo(dto);
        todo.setUser(user);

        var createdTodo = toDoRepository.save(todo);

        return new ToDoResponseDTO(createdTodo);
    }

    @Transactional
    public ToDoResponseDTO update(ToDoUpdateRequestDTO dto, Long id) {
        var todo = toDoRepository.getReferenceById(id);
        todo.update(dto);

        return new ToDoResponseDTO(todo);
    }

    @Transactional
    public void logicalDelete(Long id) {
        var todo = toDoRepository.getReferenceById(id);
        todo.deleteLogical();
    }

}
