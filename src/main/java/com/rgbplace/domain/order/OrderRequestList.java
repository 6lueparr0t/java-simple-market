package com.rgbplace.domain.order;

import com.rgbplace.domain.product.Product;

import java.time.LocalDateTime;

public interface OrderRequestList {
    Long getId();
    Product getProduct();
    LocalDateTime getCreatedDate();
}
