package br.com.roupaspet.models;

import br.com.roupaspet.dtos.request.ToDoRequestDTO;
import br.com.roupaspet.dtos.request.ToDoUpdateRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = { "id" })
@Entity
@Table(name = "todos")
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private boolean isComplete;
    private Boolean isActive;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public ToDo(ToDoRequestDTO dto) {
        this.title = dto.title();
        this.description = dto.description();
        this.isComplete = false;
        this.isActive = true;
    }

    public void update(ToDoUpdateRequestDTO dto) {
        if (dto.title() != null) {
            this.title = dto.title();
        }
        if (dto.description() != null) {
            this.description = dto.description();
        }

        this.isComplete = dto.isComplete();
    }

    public void deleteLogical() {
        this.isActive = false;
    }
}
