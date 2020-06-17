package com.zxyroy.inventory.repository;

import com.zxyroy.inventory.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByParentIdIsNull();
    List<Category> findAllByParentId(long parentCategoryId);
    Optional<Category> findByIdAndParentIdIsNull(long id);
    Optional<Category> findByIdAndParentIdIsNotNull(long id);
}
