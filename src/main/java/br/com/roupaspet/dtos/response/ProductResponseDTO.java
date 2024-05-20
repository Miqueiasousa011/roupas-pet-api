package br.com.roupaspet.dtos.response;

import br.com.roupaspet.models.Product;

public record ProductResponseDTO(
    Long id,
    String name,
    String description,
    Double price,
    String image,
    Long stock,
    CategoryResponseDTO category) {

  public ProductResponseDTO(Product product) {
    this(product.getId(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        product.getImage(),
        product.getStock(),
        new CategoryResponseDTO(product.getCategory()) //
    );
  }
}
