package com.zxyroy.inventory.domain;

import lombok.Getter;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_category")
@Where(clause = "deleted = false")
public class Category extends BasicEntity {
    @Getter
    @Column(unique = true)
    private String name;
    @Getter
    private Long parentId;
}
