package uz.pdp.vazifa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.vazifa.entity.Category;
import uz.pdp.vazifa.payload.CategoryDto;
import uz.pdp.vazifa.repository.CategoryRepository;

import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductService productService;

    public Page<Category> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Category getById(Integer id) {
        Optional<Category> byId = categoryRepository.findById(id);
        return byId.orElse(null);
    }

    public Category add(CategoryDto categoryDto) {
        Optional<Category> categoryOptional = categoryRepository.findByName(categoryDto.getName());
        Category category = new Category();
        if (categoryOptional.isPresent())
            return null;

        if (categoryDto.getCategoryId() == null) {
            category.setName(categoryDto.getName());
            categoryRepository.save(category);
            return category;
        }

        Optional<Category> optionalCategory = categoryRepository.findById(categoryDto.getCategoryId());
        if (!optionalCategory.isPresent())
            return null;

        if (categoryDto.getName().equals(optionalCategory.get().getName()))
            return null;

        category.setName(categoryDto.getName());
        category.setParentCategory(optionalCategory.get());
        categoryRepository.save(category);
        return category;
    }

    public Category edit(Integer id, CategoryDto categoryDto) {

        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) {
            return null;
        }

        Optional<Category> categoryOptional = categoryRepository.findByName(categoryDto.getName());

        if (categoryOptional.isPresent() && !id.equals(categoryOptional.get().getId()))
            return null;

        if (categoryDto.getCategoryId() == null) {
            Category category = new Category();
            category.setId(id);
            category.setName(categoryDto.getName());
            categoryRepository.save(category);
            return category;
        }

        Optional<Category> parentCategory = categoryRepository.findById(categoryDto.getCategoryId());

        if (!parentCategory.isPresent())
            return null;

        if (categoryDto.getName().equals(parentCategory.get().getName()))
            return null;

        Category category = new Category();
        category.setId(id);
        category.setName(categoryDto.getName());
        category.setParentCategory(parentCategory.get());

        categoryRepository.save(category);
        return category;
    }

    public boolean delete(Integer id) {

        try {
            categoryRepository.deleteById(id);
            productService.deleteByCategoryId(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
        }

}
