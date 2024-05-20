package br.com.roupaspet.services;

import br.com.roupaspet.dtos.request.category.SaveCategoryDTO;
import br.com.roupaspet.dtos.request.category.UpdateCategoryDTO;
import br.com.roupaspet.dtos.response.CategoryResponseDTO;
import br.com.roupaspet.models.Category;
import br.com.roupaspet.repositories.CategoryRepository;
import br.com.roupaspet.services.exceptions.APIException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public List<CategoryResponseDTO> getAll() {
        return categoryRepository.findCategoriesByIsActiveIsTrue()
                .stream()
                .map(CategoryResponseDTO::new)
                .toList();
    }

    @Transactional
    public CategoryResponseDTO save(SaveCategoryDTO dto) {
        if (Objects.isNull(dto)) {
            throw new APIException("error");
        }

        var category = new Category(dto);
        var createdTodo = categoryRepository.save(category);
        return new CategoryResponseDTO(createdTodo);
    }

    @Transactional
    public CategoryResponseDTO update(UpdateCategoryDTO dto, Long id) {
        var category = categoryRepository.getReferenceById(id);
        category.update(dto);

        return new CategoryResponseDTO(category);
    }

    @Transactional
    public void logicalDelete(Long id) {
        var todo = categoryRepository.getReferenceById(id);
        todo.deleteLogical();
    }

}
