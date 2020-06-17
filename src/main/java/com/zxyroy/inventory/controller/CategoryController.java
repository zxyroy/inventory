package com.zxyroy.inventory.controller;

import com.zxyroy.inventory.domain.DTO.CategoryDTO;
import com.zxyroy.inventory.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(value = "/api/categories")
@Validated
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "")
    public Mono<List<CategoryDTO>> getParentCateogries(){
        return Mono.just(categoryService.getCategories());
    }

    @GetMapping(value = "/{parentCategoryId}")
    public Mono<List<CategoryDTO>> getSubCateogries(@PathVariable("parentCategoryId") long parentCategoryId){
        return Mono.just(categoryService.getCategories(parentCategoryId));
    }
}
