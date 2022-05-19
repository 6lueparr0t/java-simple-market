package com.rgbplace.controller;

import com.rgbplace.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
@Slf4j
public class ProductController {

    private static final String APPLICATION_JSON_VALUE = "application/json";
    private final ProductService productService;

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> productList(HttpServletRequest request,
                                                           HttpServletResponse response,
                                                           @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                           @RequestParam(value = "item", required = false, defaultValue = "20") Integer item
    ) throws IOException {

        final URI uri = linkTo(methodOn(this.getClass()).productList(request, response, page, item)).toUri();

        try {
            Map<String, Object> data = productService.getProductList(page, item);

            response.setContentType(APPLICATION_JSON_VALUE);
            return ResponseEntity.created(uri).body(data);
        } catch (Exception e) {
            log.error(e.getMessage());

            throw new IOException(e);
        }
    }
}
