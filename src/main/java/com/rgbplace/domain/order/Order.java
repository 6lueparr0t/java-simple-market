package com.rgbplace.domain.order;


import com.rgbplace.domain.product.Product;
import com.rgbplace.domain.user.User;
import com.rgbplace.dto.OrderDto;
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
@Table(name = "TBL_ORDER")
@EntityListeners(AuditingEntityListener.class)
@Builder(builderMethodName = "OrderBuilder")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="uid", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="pno", nullable = false)
    private Product product;

    @Column(name = "cdtm")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "udtm")
    @LastModifiedDate
    private LocalDateTime updatedDate;

    public static OrderBuilder builder(OrderDto orderDto) {
        return OrderBuilder()
                .user(orderDto.getUser())
                .product(orderDto.getProduct());
    }
}
