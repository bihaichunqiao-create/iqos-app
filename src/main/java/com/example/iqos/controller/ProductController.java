package com.example.iqos.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.iqos.entity.Product;
import com.example.iqos.service.ProductService;

/**
 * 商品情報を提供するREST APIコントローラ
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

private final ProductService productService;

public ProductController(ProductService productService) {
    this.productService = productService;
}

     /**
     * 商品検索API
     *
     * 検索条件：
     * ・flavorTypeのみ指定
     * ・strengthのみ指定
     * ・両方指定
     * ・両方未指定（全件取得）
     *
     * リクエスト例：
     * GET /api/products/search?flavorType=MENTHOL
     * GET /api/products/search?strength=4
     * GET /api/products/search?flavorType=MENTHOL&strength=4
     *
     * @param flavorType フレーバー種別
     * @param strength メンソール強度
     * @return 条件に一致する商品一覧
     */
    @GetMapping("/search")
    public List<Product> search(
        @RequestParam(required = false) String flavorType,
        @RequestParam(required = false) Integer strength
    ) {
    return productService.search(flavorType, strength);
    }

    /**
     * 全商品取得API
     *
     * ID昇順で全商品を取得する
     *
     * @return 商品一覧
     */
    @GetMapping
    public List<Product> getAll() {
        return productService.getAllProducts();
    }
}