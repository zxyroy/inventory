package com.zxyroy.inventory.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    @Getter
    private long id;
    @Getter
    private String name;
}
