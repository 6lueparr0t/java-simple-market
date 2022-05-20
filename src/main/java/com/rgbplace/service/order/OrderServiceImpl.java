package com.rgbplace.service.order;

import com.auth0.jwt.interfaces.Claim;
import com.rgbplace.common.constant.ExceptionMessage;
import com.rgbplace.common.constant.StatusMessage;
import com.rgbplace.domain.order.Order;
import com.rgbplace.domain.order.OrderRepository;
import com.rgbplace.domain.order.OrderRequestList;
import com.rgbplace.domain.product.Product;
import com.rgbplace.domain.user.User;
import com.rgbplace.dto.OrderDto;
import com.rgbplace.service.product.ProductService;
import com.rgbplace.service.token.TokenService;
import com.rgbplace.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final UserService userService;
    private final ProductService productService;
    private final TokenService tokenService;
    private final OrderRepository orderRepository;

    @Override
    public Map<String, Object> postOrderRequest(OrderDto orderDto, HttpServletRequest request) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (Optional.ofNullable(authorizationHeader).isPresent() && authorizationHeader.startsWith("Bearer ")) {
            Map<String, Object> data = new HashMap<>();
            try {
                Map<String, Claim> claimMap = tokenService.checkToken(authorizationHeader);

                User user = userService.getUser(claimMap.get("UserId").asString());

                if (Optional.ofNullable(user)
                        .map(User::getAccessToken)
                        .isPresent() && ("Bearer "+user.getAccessToken()).equals(authorizationHeader)) {

                    Product product = productService.getProduct(orderDto.getPno());

                    Order order = new Order();
                    order.setUser(user);
                    order.setProduct(product);
                    orderRepository.save(order);

                    data.put("status", StatusMessage.SUCCESS.getValue());
                    data.put("msg", ExceptionMessage.SUCCESS.getValue());
                } else {
                    throw new IOException(ExceptionMessage.INVALID_TOKEN.getValue());
                }
            } catch (Exception e) {
                log.error("Error logging in: {}", e.getMessage());
                data.put("status", StatusMessage.FAIL.getValue());
                data.put("msg", ExceptionMessage.ERROR.getValue());
            }

            return data;
        } else {
            throw new IOException(ExceptionMessage.INVALID_TOKEN.getValue());
        }
    }
    @Override
    public Map<String, Object> getOrderList(Integer page, Integer item, HttpServletRequest request) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (Optional.ofNullable(authorizationHeader).isPresent() && authorizationHeader.startsWith("Bearer ")) {
            Map<String, Object> data = new HashMap<>();
            try {
                Map<String, Claim> claimMap = tokenService.checkToken(authorizationHeader);

                User user = userService.getUser(claimMap.get("UserId").asString());

                if (Optional.ofNullable(user)
                        .map(User::getAccessToken)
                        .isPresent() && ("Bearer "+user.getAccessToken()).equals(authorizationHeader)) {
                    Pageable pageable = PageRequest.of(page, item);
                    User findUser = userService.getUser(user.getUid());
                    Page<OrderRequestList> orderPage = orderRepository.findAllByUserId(findUser.getId(), pageable);
                    List<OrderRequestList> orderList = orderPage.getContent();
                    data.put("data", orderList);

                    Map<String, Object> pageInfo = new HashMap<>();
                    pageInfo.put("currentPage", orderPage.getNumber());
                    pageInfo.put("totalItems", orderPage.getTotalElements());
                    pageInfo.put("totalPages", orderPage.getTotalPages());
                    data.put("page", pageInfo);

                    data.put("status", StatusMessage.SUCCESS.getValue());
                    data.put("msg", ExceptionMessage.SUCCESS.getValue());
                } else {
                    throw new IOException(ExceptionMessage.INVALID_TOKEN.getValue());
                }
            } catch (Exception e) {
                log.error("Error logging in: {}", e.getMessage());
                data.put("status", StatusMessage.FAIL.getValue());
                data.put("msg", ExceptionMessage.ERROR.getValue());
            }

            return data;
        } else {
            throw new IOException(ExceptionMessage.INVALID_TOKEN.getValue());
        }
    }
}
