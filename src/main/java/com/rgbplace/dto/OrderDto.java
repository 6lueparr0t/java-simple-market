package com.rgbplace.dto;

import com.rgbplace.domain.product.Product;
import com.rgbplace.domain.user.User;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {
    @NotNull
    private Long pno;

    private User user;
    private Product product;
}
