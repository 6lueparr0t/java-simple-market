package com.rgbplace.service.product;

import com.rgbplace.domain.product.Product;

import java.io.IOException;
import java.util.Map;

public interface ProductService {
    Map<String, Object> getProductList(Integer page, Integer item) throws IOException;
    Product getProduct(Long pno) throws IOException;
}
