package com.zxyroy.inventory;

import com.zxyroy.inventory.domain.DTO.CategoryDTO;
import com.zxyroy.inventory.domain.DTO.InventoryDTO;
import com.zxyroy.inventory.domain.request.AddInventoryRequest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InventoryApplicationTests {
    private final String baseUri = "/api";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    void successGetCategories() {
        ResponseEntity<CategoryDTO[]> response = restTemplate.getForEntity(baseUri + "/categories", CategoryDTO[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().length).isEqualTo(2);
    }

    @Test
    @Order(2)
    void successGetSubCategories() {
        ResponseEntity<CategoryDTO[]> response = restTemplate.getForEntity(baseUri + "/categories/1", CategoryDTO[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().length).isEqualTo(3);
    }

    @Test
    @Order(3)
    void failGetSubCategoriesNoResult() {
        ResponseEntity<CategoryDTO[]> response = restTemplate.getForEntity(baseUri + "/categories/3", CategoryDTO[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().length).isEqualTo(0);
    }

    @Test
    @Order(4)
    void failGetSubCategoriesInvalidInput() {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUri + "/categories/0", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Order(5)
    void successGetInventory() {
        ResponseEntity<InventoryDTO[]> response = restTemplate.getForEntity(baseUri + "/inventory", InventoryDTO[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().length).isEqualTo(3);
    }

    @Test
    @Order(6)
    void successAddInventory() {
        AddInventoryRequest addInventoryRequest = new AddInventoryRequest("Watermelon", 1, 4, 20);
        ResponseEntity<InventoryDTO> response = restTemplate.postForEntity(baseUri + "/inventory", addInventoryRequest, InventoryDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(4);
        assertThat(response.getBody().getName()).isEqualTo(addInventoryRequest.getName());
        assertThat(response.getBody().getQuantity()).isEqualTo(addInventoryRequest.getQuantity());
    }

    @Test
    @Order(7)
    void failAddInventoryEmptyName() {
        AddInventoryRequest addInventoryRequest = new AddInventoryRequest("", 1, 4, 20);
        ResponseEntity<String> response = restTemplate.postForEntity(baseUri + "/inventory", addInventoryRequest, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Order(8)
    void failAddInventoryWrongCategoryId() {
        AddInventoryRequest addInventoryRequest = new AddInventoryRequest("Watermelon", 0, 4, 20);
        ResponseEntity<String> response = restTemplate.postForEntity(baseUri + "/inventory", addInventoryRequest, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Order(9)
    void failAddInventoryWrongSubCategoryId() {
        AddInventoryRequest addInventoryRequest = new AddInventoryRequest("Watermelon", 1, 0, 20);
        ResponseEntity<String> response = restTemplate.postForEntity(baseUri + "/inventory", addInventoryRequest, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Order(10)
    void failAddInventoryCategoryIdNotExist() {
        AddInventoryRequest addInventoryRequest = new AddInventoryRequest("Watermelon", 4, 4, 20);
        ResponseEntity<String> response = restTemplate.postForEntity(baseUri + "/inventory", addInventoryRequest, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(11)
    void failAddInventorySubCategoryIdNotExist() {
        AddInventoryRequest addInventoryRequest = new AddInventoryRequest("Watermelon", 1, 1, 20);
        ResponseEntity<String> response = restTemplate.postForEntity(baseUri + "/inventory", addInventoryRequest, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(12)
    void failAddInventoryWrongQuantity() {
        AddInventoryRequest addInventoryRequest = new AddInventoryRequest("Watermelon", 1, 4, -1);
        ResponseEntity<String> response = restTemplate.postForEntity(baseUri + "/inventory", addInventoryRequest, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Order(13)
    void failAddInventoryCategoryNotMatch() {
        AddInventoryRequest addInventoryRequest = new AddInventoryRequest("Watermelon", 2, 4, 20);
        ResponseEntity<String> response = restTemplate.postForEntity(baseUri + "/inventory", addInventoryRequest, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Order(14)
    void successUpdateInventory() {
        int newQuantity = 10;
        ResponseEntity<InventoryDTO> response = restTemplate.exchange(baseUri + "/inventory/4?quantity=" + newQuantity, HttpMethod.PUT, new HttpEntity<>(new HttpHeaders()), InventoryDTO.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(4);
        assertThat(response.getBody().getQuantity()).isEqualTo(newQuantity);
    }

    @Test
    @Order(14)
    void failUpdateInventoryNotExist() {
        ResponseEntity<String> response = restTemplate.exchange(baseUri + "/inventory/5?quantity=10", HttpMethod.PUT, new HttpEntity<>(new HttpHeaders()), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(15)
    void failUpdateInventoryInvalidQuantity() {
        ResponseEntity<String> response = restTemplate.exchange(baseUri + "/inventory/4?quantity=-1", HttpMethod.PUT, new HttpEntity<>(new HttpHeaders()), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Order(16)
    void successRemoveInventory() {
        restTemplate.delete(baseUri + "/inventory/4");
        successGetInventory();
    }
}
