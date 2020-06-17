package com.zxyroy.inventory.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class InventoryDTO {
    @Getter
    private long id;
    @Getter
    private String name;
    @Getter
    private String category;
    @Getter
    private String subCategory;
    @Getter
    private int quantity;
}
