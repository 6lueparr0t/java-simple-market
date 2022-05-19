package com.rgbplace.service.product;

import com.rgbplace.domain.product.Product;
import com.rgbplace.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Map<String, Object> getProductList(Integer page, Integer item) throws IOException {
        try {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");

            Pageable pageable = PageRequest.of(page, item);
            Page<Product> productPage = productRepository.findAll(pageable);
            List<Product> productList = productPage.getContent();
            response.put("data", productList);

            Map<String, Object> pageInfo = new HashMap<>();
            pageInfo.put("currentPage", productPage.getNumber());
            pageInfo.put("totalItems", productPage.getTotalElements());
            pageInfo.put("totalPages", productPage.getTotalPages());
            response.put("page", pageInfo);

            return response;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IOException(e);
        }
    }

    @Override
    public Product getProduct(Long pno) throws IOException {
        try {
            log.info("Fetching product {}", pno);
            Optional<Product> productOptional = productRepository.findById(pno);
            if (productOptional.isPresent()) {
                return productOptional.get();
            } else {
                throw new IOException("product not found");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new IOException(e);
        }
    }
}
