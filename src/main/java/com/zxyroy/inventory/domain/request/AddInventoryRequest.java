package com.zxyroy.inventory.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class AddInventoryRequest {
    @Getter
    String name;
    @Getter
    long categoryId;
    @Getter
    long subCategoryId;
    @Getter
    int quantity;
}
