package com.rgbplace.domain.order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order,Long> {
    Page<OrderRequestList> findAllByUserId(Long id, Pageable pageable);
}
