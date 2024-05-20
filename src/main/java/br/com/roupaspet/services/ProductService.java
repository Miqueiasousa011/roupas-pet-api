package br.com.roupaspet.services;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import br.com.roupaspet.dtos.request.product.SaveProductDTO;
import br.com.roupaspet.dtos.request.product.UpdateProductDTO;
import br.com.roupaspet.dtos.response.ProductResponseDTO;
import br.com.roupaspet.models.Product;
import br.com.roupaspet.repositories.CategoryRepository;
import br.com.roupaspet.repositories.ProductRepository;
import br.com.roupaspet.services.exceptions.APIException;
import jakarta.transaction.Transactional;

@Service
public class ProductService {
  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;

  public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
  }

  @Transactional
  public List<ProductResponseDTO> getAll() {
    return productRepository.findProductsByIsActiveIsTrue()
        .stream()
        .map(ProductResponseDTO::new)
        .toList();
  }

  @Transactional
  public ProductResponseDTO save(SaveProductDTO dto) {
    if (Objects.isNull(dto)) {
      throw new APIException("error");
    }

    var category = categoryRepository.findById(dto.categoryID())
        .orElseThrow(() -> new APIException("Produto sem categoria"));

    var product = productRepository.save(new Product(dto, category));

    return new ProductResponseDTO(product);
  }

  @Transactional
  public ProductResponseDTO update(UpdateProductDTO dto, Long id) {
    var product = productRepository.getReferenceById(id);

    if (dto.categoryID() != null) {
      var category = categoryRepository.getReferenceById(dto.categoryID());
      product.setCategory(category);
    }

    product.update(dto);

    return new ProductResponseDTO(product);
  }

  @Transactional
  public void logicalDelete(Long id) {
    var product = productRepository.getReferenceById(id);
    product.deleteLogical();
  }
}
