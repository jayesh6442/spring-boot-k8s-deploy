package com.example.kubernetesdeploy.service.impl;

import com.example.kubernetesdeploy.models.Product;
import com.example.kubernetesdeploy.repository.ProductRepository;
import com.example.kubernetesdeploy.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @Override
    public Product saveProduct(Product product) {
        product.setId(null); // ensure auto-generation
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product, Long id) {
        Product existing = getProductById(id);
        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        return productRepository.save(existing);
    }

    @Override
    public void deleteProductById(Long id) {
        Product existing = getProductById(id);
        productRepository.delete(existing);
    }
}
