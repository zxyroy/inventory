package com.zxyroy.inventory.service;

import com.zxyroy.inventory.domain.Category;
import com.zxyroy.inventory.domain.DTO.InventoryDTO;
import com.zxyroy.inventory.domain.Inventory;
import com.zxyroy.inventory.exeption.CategoryNotMatchException;
import com.zxyroy.inventory.exeption.ResourseNotFoundExeption;
import com.zxyroy.inventory.repository.CategoryRepository;
import com.zxyroy.inventory.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<InventoryDTO> getInventoryList() {
        return inventoryRepository.findAll().stream().map(this::convert).collect(Collectors.toList());
    }

    public InventoryDTO updateQuantity(long id, int quantity) {
        if (quantity < 0)
            throw new IllegalArgumentException();
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(ResourseNotFoundExeption::new);
        log.info(inventory.toString());
        inventory.setQuantity(quantity);
        inventoryRepository.save(inventory);
        return convert(inventory);
    }

    public InventoryDTO addInventory(String name, long categoryId, long subCategoryId, int quantity) {
        //Bean validation not compatible with webflux. Use temporary solution first
        if (name == null || name.length() == 0 || categoryId < 1 || subCategoryId < 1 || quantity < 0)
            throw new IllegalArgumentException();
        Category category = categoryRepository.findByIdAndParentIdIsNull(categoryId).orElseThrow(ResourseNotFoundExeption::new);
        Category subCategory = categoryRepository.findByIdAndParentIdIsNotNull(subCategoryId).orElseThrow(ResourseNotFoundExeption::new);
        if (!category.getId().equals(subCategory.getParentId()))
            throw new CategoryNotMatchException();

        Inventory inventory = new Inventory();
        inventory.setName(name);
        inventory.setQuantity(quantity);
        inventory.setCategory(category);
        inventory.setSubCategory(subCategory);
        inventoryRepository.save(inventory);
        return convert(inventory);
    }

    public void removeInventory(long id) {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(ResourseNotFoundExeption::new);
        inventory.setDeleted(true);
        inventoryRepository.save(inventory);
    }


    private InventoryDTO convert(Inventory inventory) {
        return new InventoryDTO(inventory.getId(), inventory.getName(), inventory.getCategory().getName(), inventory.getSubCategory().getName(), inventory.getQuantity());
    }
}
