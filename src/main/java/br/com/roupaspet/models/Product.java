package br.com.roupaspet.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.roupaspet.dtos.request.product.SaveProductDTO;
import br.com.roupaspet.dtos.request.product.UpdateProductDTO;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = { "id" })
@Entity
@Table(name = "products")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String description;
  private Double price;
  private String image;
  private Long stock;
  private Boolean isActive = true;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private Category category;
  @CreationTimestamp
  private LocalDateTime createdAt;
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  public Product(SaveProductDTO dto, Category category) {
    this.name = dto.name();
    this.description = dto.description();
    this.price = dto.price();
    this.image = dto.image();
    this.stock = dto.stock();
    this.category = category;
  }

  public void update(UpdateProductDTO dto) {
    if (dto.name() != null) {
      this.name = dto.name();
    }
    if (dto.description() != null) {
      this.description = dto.description();
    }
    if (dto.price() != null) {
      this.price = dto.price();
    }
    if (dto.image() != null) {
      this.image = dto.image();
    }
    if (dto.stock() != null) {
      this.stock = dto.stock();
    }
    if (dto.isActive() != null) {
      this.isActive = dto.isActive();
    }
  }

  public void deleteLogical() {
    this.isActive = false;
  }
}
