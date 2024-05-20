package br.com.roupaspet.repositories;

import br.com.roupaspet.models.ToDo;
import br.com.roupaspet.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    List<ToDo> findAllByUserAndIsActive(User user, boolean isActive);
}
