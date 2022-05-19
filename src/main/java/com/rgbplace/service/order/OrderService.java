package com.rgbplace.service.order;

import com.rgbplace.dto.OrderDto;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public interface OrderService {
    Map<String, Object> postOrderRequest(OrderDto orderDto, HttpServletRequest request) throws IOException;
    Map<String, Object> getOrderList(Integer page, Integer item, HttpServletRequest request) throws IOException;
}
