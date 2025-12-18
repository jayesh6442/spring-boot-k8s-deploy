package com.example.kubernetesdeploy.service;

import com.example.kubernetesdeploy.models.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product saveProduct(Product product);

    Product updateProduct(Product product, Long id);

    void deleteProductById(Long id);
}
