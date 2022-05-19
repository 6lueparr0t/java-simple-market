package com.rgbplace.controller;

import com.rgbplace.dto.OrderDto;
import com.rgbplace.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
@Slf4j
public class OrderController {

    private static final String APPLICATION_JSON_VALUE = "application/json";
    private final OrderService orderService;

    @PostMapping("/request")
    public ResponseEntity<Map<String, Object>> postOrder(HttpServletRequest request,
                                                         HttpServletResponse response,
                                                         @RequestBody OrderDto orderDto) throws IOException {

        final URI uri = linkTo(methodOn(this.getClass()).postOrder(request, response, orderDto)).toUri();

        try {
            Map<String, Object> data = orderService.postOrderRequest(orderDto, request);

            response.setContentType(APPLICATION_JSON_VALUE);
            return ResponseEntity.created(uri).body(data);
        } catch (Exception e) {
            log.error(e.getMessage());

            throw new IOException(e);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getOrderList(HttpServletRequest request,
                                                            HttpServletResponse response,
                                                            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                            @RequestParam(value = "item", required = false, defaultValue = "20") Integer item
    ) throws IOException {

        final URI uri = linkTo(methodOn(this.getClass()).getOrderList(request, response, page, item)).toUri();

        try {
            Map<String, Object> data = orderService.getOrderList(page, item, request);

            response.setContentType(APPLICATION_JSON_VALUE);
            return ResponseEntity.created(uri).body(data);
        } catch (Exception e) {
            log.error(e.getMessage());

            throw new IOException(e);
        }
    }
}
