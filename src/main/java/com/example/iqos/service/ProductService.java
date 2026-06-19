package com.example.iqos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.iqos.entity.Product;
import com.example.iqos.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
 /**
     * フレーバー種別・強さを条件に製品を検索する
     *
     * 条件は以下の4パターンで分岐する：
     * 1. 両方なし → 全件取得
     * 2. フレーバーのみ指定 → フレーバーで検索
     * 3. 強さのみ指定 → メンソール強度で検索
     * 4. 両方指定 → 両条件で絞り込み
     *
     * @param flavorType フレーバー種別（例：メンソール、フルーツなど）
     * @param strength メンソール強度（数値）
     * @return 条件に一致する商品リスト
     */
    public List<Product> search(String flavorType, Integer strength) {

    // フレーバー条件が指定されているか判定
    boolean hasFlavor = flavorType != null && !flavorType.isEmpty();

    // 強さ条件が指定されているか判定
    boolean hasStrength = strength != null;

    // 両方未指定の場合は全件返却
    if (!hasFlavor && !hasStrength) {
        return productRepository.findAll();
    }

    // フレーバーだけ
    if (hasFlavor && !hasStrength) {
        return productRepository.findByFlavorType(flavorType);
    }

    // 星だけ
    if (!hasFlavor) {
        return productRepository.findByMentholStrength(strength);
    }

    // 両方
    return productRepository.findByFlavorTypeAndMentholStrength(
            flavorType,
            strength
    );
}

    /**
     * 全商品をID昇順で取得する
     *
     * @return 商品一覧（ID昇順）
     */
    public List<Product> getAllProducts() {
        return productRepository.findAllByOrderByIdAsc();
    }

}