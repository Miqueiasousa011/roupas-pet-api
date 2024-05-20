package br.com.roupaspet.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.roupaspet.dtos.request.category.SaveCategoryDTO;
import br.com.roupaspet.dtos.request.category.UpdateCategoryDTO;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = { "id" })
@Entity
@Table(name = "categories")
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @Column(name = "is_active")
  private Boolean isActive = true;
  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
  private List<Product> products;
  @CreationTimestamp
  private LocalDateTime createdAt;
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public Category(SaveCategoryDTO dto) {
    this.name = dto.name();
  }

  public void update(UpdateCategoryDTO dto) {
    if (dto.name() != null) {
      this.name = dto.name();
    }
  }

  public void deleteLogical() {
    this.isActive = false;
  }
}
