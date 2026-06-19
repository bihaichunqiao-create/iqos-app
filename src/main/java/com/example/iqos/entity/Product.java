package com.example.iqos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * productsテーブルに対応するエンティティ
 *
 * IQOS製品の基本情報を保持する
 * （名称、シリーズ、フレーバー、強度、価格など）
 */
@Entity
@Table(name = "products")
public class Product {

    /** 主キー（自動採番） */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 商品名、シリーズ名 */
    private String name;
    private String series;
    
    /** フレーバー種別（DBカラム: flavor_type） */
    @Column(name = "flavor_type")
    private String flavorType;

    /** メンソール強度（DBカラム: menthol_strength） */
    @Column(name = "menthol_strength")
    private Integer mentholStrength;

    /** 価格 */
    private Integer price;

    /** 商品画像URLまたはパス */
    private String image;

    // --- Getter / Setter ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSeries() { return series; }
    public void setSeries(String series) { this.series = series; }

    public String getFlavorType() { return flavorType; }
    public void setFlavorType(String flavorType) { this.flavorType = flavorType; }

    public Integer getMentholStrength() { return mentholStrength; }
    public void setMentholStrength(Integer mentholStrength) { this.mentholStrength = mentholStrength; }

    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}