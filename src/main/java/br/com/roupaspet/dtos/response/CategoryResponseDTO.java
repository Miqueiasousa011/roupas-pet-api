package br.com.roupaspet.dtos.response;

import br.com.roupaspet.models.Category;

public record CategoryResponseDTO(Long id, String name) {
  public CategoryResponseDTO(Category category) {
    this(category.getId(), category.getName());
  }
}
