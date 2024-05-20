package br.com.roupaspet.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.roupaspet.dtos.request.product.SaveProductDTO;
import br.com.roupaspet.dtos.request.product.UpdateProductDTO;
import br.com.roupaspet.dtos.response.ProductResponseDTO;
import br.com.roupaspet.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/products")
public class ProductController {
  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public ResponseEntity<List<ProductResponseDTO>> getAll() {
    return ResponseEntity.ok(productService.getAll());
  }

  @PostMapping
  public ResponseEntity<Object> save(@RequestBody SaveProductDTO dto, HttpServletRequest request) {
    var product = productService.save(dto);

    var uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(product.id()).toUri();

    return ResponseEntity.created(uri).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductResponseDTO> update(@RequestBody UpdateProductDTO dto, @PathVariable Long id) {
    var product = productService.update(dto, id);
    return ResponseEntity.ok(product);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> logicalDelete(@PathVariable Long id) {
    productService.logicalDelete(id);
    return ResponseEntity.noContent().build();
  }
}
