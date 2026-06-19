package com.example.iqos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.iqos.entity.Product;

/**
 * Productエンティティに対するデータアクセス層
 *
 * Spring Data JPAにより、メソッド名から自動的にSQLが生成される
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * フレーバー種別で商品を検索する
     *
     * @param flavorType フレーバー種別（例：メンソール、フルーツなど）
     * @return 該当する商品一覧
     */
    List<Product> findByFlavorType(String flavorType);

    /**
     * メンソール強度で商品を検索する
     *
     * @param mentholStrength 強度レベル（数値）
     * @return 該当する商品一覧
     */
    List<Product> findByMentholStrength(Integer mentholStrength);

    /**
     * フレーバー種別とメンソール強度の両方で商品を検索する
     *
     * @param flavorType フレーバー種別
     * @param mentholStrength メンソール強度
     * @return 条件に一致する商品一覧
     */
    List<Product> findByFlavorTypeAndMentholStrength(
        String flavorType,
        Integer mentholStrength
    );

    /**
     * 商品をID昇順で全件取得する
     *
     * @return ID昇順の商品一覧
     */

    List<Product> findAllByOrderByIdAsc();
}
