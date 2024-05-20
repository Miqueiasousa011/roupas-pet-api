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

import br.com.roupaspet.dtos.request.category.SaveCategoryDTO;
import br.com.roupaspet.dtos.request.category.UpdateCategoryDTO;
import br.com.roupaspet.dtos.response.CategoryResponseDTO;
import br.com.roupaspet.services.CategoryService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/categories")
public class CategoryController {
  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping
  public ResponseEntity<List<CategoryResponseDTO>> getAll() {
    return ResponseEntity.ok(categoryService.getAll());
  }

  @PostMapping
  public ResponseEntity<Object> save(@RequestBody SaveCategoryDTO dto, HttpServletRequest request) {
    var category = categoryService.save(dto);

    var uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}").buildAndExpand(category.id()).toUri();

    return ResponseEntity.created(uri).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<CategoryResponseDTO> update(@RequestBody UpdateCategoryDTO dto, @PathVariable Long id) {
    var category = categoryService.update(dto, id);
    return ResponseEntity.ok(category);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> logicalDelete(@PathVariable Long id) {
    categoryService.logicalDelete(id);
    return ResponseEntity.noContent().build();
  }
}
