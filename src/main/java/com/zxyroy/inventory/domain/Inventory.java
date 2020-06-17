package com.zxyroy.inventory.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "tb_inventory")
@Where(clause = "deleted = false")
@ToString
public class Inventory extends BasicEntity{
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    @ManyToOne
    private Category category;
    @Getter
    @Setter
    @ManyToOne
    private Category subCategory;
    @Getter
    @Setter
    private int quantity;

    @Version
    private Long version;
}
