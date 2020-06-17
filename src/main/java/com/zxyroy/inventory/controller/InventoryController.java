package com.zxyroy.inventory.controller;

import com.zxyroy.inventory.domain.DTO.InventoryDTO;
import com.zxyroy.inventory.domain.request.AddInventoryRequest;
import com.zxyroy.inventory.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(value = "/api/inventory")
@Validated
@Slf4j
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @PostMapping("")
    public Mono<InventoryDTO> addInventory(@RequestBody Mono<AddInventoryRequest> addInventoryRequestMono) {
        return addInventoryRequestMono.flatMap(addInventoryRequest -> Mono.just(inventoryService.addInventory(addInventoryRequest.getName(), addInventoryRequest.getCategoryId(), addInventoryRequest.getSubCategoryId(), addInventoryRequest.getQuantity())));
    }

    @GetMapping("")
    public Mono<List<InventoryDTO>> getInventory() {
        return Mono.just(inventoryService.getInventoryList());
    }

    @PutMapping("/{inventoryId}")
    public Mono<InventoryDTO> updateQuantity(@PathVariable("inventoryId") long inventoryId, @RequestParam("quantity") int quantity) {
        return Mono.just(inventoryService.updateQuantity(inventoryId, quantity));
    }

    @DeleteMapping("/{inventoryId}")
    public void removeQuantity(@PathVariable("inventoryId") long inventoryId) {
        inventoryService.removeInventory(inventoryId);
    }
}
