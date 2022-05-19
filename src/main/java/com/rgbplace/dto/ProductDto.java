package com.rgbplace.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    @NotNull
    private String name;

    private String desc;

    @NotNull
    private Long price;
}
