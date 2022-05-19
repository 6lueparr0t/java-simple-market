package com.rgbplace.domain.product;


import com.rgbplace.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
@Table(name = "TBL_PRODUCT")
@EntityListeners(AuditingEntityListener.class)
@Builder(builderMethodName = "ProductBuilder")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 400, nullable = false, name = "name")
    private String name;

    @Column(length = 4000, name = "desc")
    private String desc;

    @Column(name = "price")
    private Long price;

    @Column(name = "cdtm")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "udtm")
    @LastModifiedDate
    private LocalDateTime updatedDate;

    public static ProductBuilder builder(ProductDto productDto) {
        return ProductBuilder()
                .name(productDto.getName())
                .desc(productDto.getDesc())
                .price(productDto.getPrice());
    }
}
