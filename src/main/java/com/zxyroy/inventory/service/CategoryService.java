package com.zxyroy.inventory.service;

import com.zxyroy.inventory.domain.Category;
import com.zxyroy.inventory.domain.DTO.CategoryDTO;
import com.zxyroy.inventory.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDTO> getCategories(){
        return getCategories(null);
    }

    public List<CategoryDTO> getCategories(Long parentCategoryId){
        List<Category> categories;
        if(parentCategoryId == null)
            categories = categoryRepository.findAllByParentIdIsNull();
        else {
            if(parentCategoryId <= 0)
                throw new IllegalArgumentException();
            categories = categoryRepository.findAllByParentId(parentCategoryId);
        }
        return categories.stream().map(category -> new CategoryDTO(category.getId(), category.getName())).collect(Collectors.toList());
    }
}
