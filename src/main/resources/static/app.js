document.addEventListener("DOMContentLoaded", () => {

    const container = document.querySelector(".product-container");
    const form = document.getElementById("searchForm");

    // =========================
    // ページ表示後に商品一覧を取得
    // =========================
    loadProducts();

    // 商品一覧APIを呼び出す
    function loadProducts() {
        fetch("/api/products")
            .then(res => res.json())
            .then(renderProducts)
            .catch(err => {
                console.error("データ取得エラー:", err);

                // エラーメッセージ表示
                container.innerHTML =
                    '<p style="text-align:center;color:#ff3b30;">データの取得に失敗しました。</p>';
            });
    }

    // =========================
    // 検索処理
    // =========================
    form.addEventListener("submit", (e) => {
        e.preventDefault();

        const params = buildSearchParams();

        fetch("/api/products/search?" + params)
            .then(res => res.json())
            .then(renderProducts)
            .catch(err => {
                console.error("検索エラー:", err);
            });
    });

    /**
    * 検索条件をURLパラメータへ変換
    *
    * 例:
    * flavorType=メンソール&strength=4
    */
    function buildSearchParams() {
        const params = new URLSearchParams();

        const flavorType = form.flavorType.value;
        const strength = form.strength.value;

        if (flavorType) params.append("flavorType", flavorType);
        if (strength != null && strength !== "") {
            params.append("strength", strength);
        }

        return params;
    }

    /**
     * APIから取得した商品情報を画面へ表示する
     *
     * @param {Array} products 商品一覧
     */
    function renderProducts(products) {

        if (!products || products.length === 0) {
            container.innerHTML =
                '<p style="text-align:center;color:#8e8e93;">商品がありません</p>';
            return;
        }

        container.innerHTML = "";

        products.forEach(product => {

            const name = product.name || "名称未設定";
            const price = product.price;
            const priceText =
                price == null
                    ? "販売終了"
                    : `¥${Number(price).toLocaleString()} (税込)`;
            const series = product.series || "ILUMA SERIES";

            const image = product.image || "noimage.jpg";

            const flavorType =
                product.flavorType ??
                product.flavor_type ??
                "未設定";

            const strength =
                product.mentholStrength ??
                product.menthol_strength ??
                0;

            // 強度(1～5)を星表示へ変換
            const stars =
                "★".repeat(Math.min(5, strength)) +
                "☆".repeat(5 - Math.min(5, strength));

            container.insertAdjacentHTML("beforeend", `
                        <div class="product-card">
                        <div class="series-tag">${series}</div>
                        <img src="${image}" alt="${name}" class="product-image" >

                        <h3 class="product-name">${name}</h3>

                        <div class="product-info">
                        <span>フレーバータイプ</span>
                        <span>${flavorType}</span>
                        </div>

                        <div class="product-info">
                        <span>清涼感の強さ</span>
                        <span>${stars}</span>
                        </div>

                        <div class="purchase-counter">
                            <button class="minus-btn" data-id="${product.id}">－</button>

                            <span id="count-${product.id}">
                                ${localStorage.getItem(`count-${product.id}`) || 0}
                            </span>

                            <button class="plus-btn" data-id="${product.id}">＋</button>
                        </div>

                        <div class="product-price">
                            ${priceText}
                        </div>

                        <button class="btn-detail"onclick="location.href='https://jp.iqos.com/passion'">詳細を見る</button>
                        </div>
                    `);
        });

        setTimeout(() => {

            // ========================================
            // 購入本数カウンター
            // LocalStorageに保存するため
            // リロード後も値が保持される
            // ========================================
            document.querySelectorAll(".plus-btn").forEach(btn => {

                btn.addEventListener("click", () => {

                    const id = btn.dataset.id;

                    let count =
                        Number(localStorage.getItem(`count-${id}`)) || 0;

                    // 本数を1増やす
                    count++;

                    localStorage.setItem(`count-${id}`, count);

                    document.getElementById(`count-${id}`).textContent = count;
                });
            });

            document.querySelectorAll(".minus-btn").forEach(btn => {

                btn.addEventListener("click", () => {

                    const id = btn.dataset.id;

                    let count =
                        Number(localStorage.getItem(`count-${id}`)) || 0;

                    // 0未満にならないよう制御
                    if (count > 0) {
                        count--;
                    }

                    localStorage.setItem(`count-${id}`, count);

                    document.getElementById(`count-${id}`).textContent = count;
                });
            });

        }, 0);
    }

    // =========================
    // ハンバーガーメニュー
    // =========================
    const menuBtn = document.getElementById("menuBtn");
    const closeBtn = document.getElementById("closeBtn");
    const sideMenu = document.getElementById("sideMenu");
    const overlay = document.getElementById("overlay");

    // メニュー表示
    menuBtn.addEventListener("click", () => {
        sideMenu.classList.add("open");
        overlay.classList.add("active");
    });

    closeBtn.addEventListener("click", closeMenu);
    overlay.addEventListener("click", closeMenu);

    // メニュー非表示
    function closeMenu() {
        sideMenu.classList.remove("open");
        overlay.classList.remove("active");
    }

    document.getElementById("resetBtn").addEventListener("click", () => {
        form.flavorType.value = "";
        form.strength.value = "";

        fetch("/api/products")
            .then(res => res.json())
            .then(renderProducts);
    });
    const stars = document.querySelectorAll("#starFilter span");
    const strengthInput = document.getElementById("strengthInput");

    let current = 0;

    // ========================================
    // メンソール強度選択
    // 同じ星を押すと選択解除
    // ========================================
    stars.forEach(star => {
        star.addEventListener("click", () => {

            const value = Number(star.dataset.value);

            // 同じ星を押したらリセット（0）
            if (current === value) {
                current = 0;
            } else {
                current = value;
            }

            strengthInput.value = current;

            updateStars(current);
        });
    });

    function updateStars(value) {
        stars.forEach(star => {
            const v = Number(star.dataset.value);
            star.classList.toggle("active", v <= value);
        });
    }
    const flavorButtons = document.querySelectorAll("#flavorFilter button");
    const flavorInput = document.getElementById("flavorInput");

    // 現在選択中のフレーバーを保持
    let selectedFlavor = "";

    // ========================================
    // フレーバー選択
    // 同じボタンを押すと解除
    // ========================================
    flavorButtons.forEach(btn => {
        btn.addEventListener("click", () => {

            const value = btn.dataset.value;

            // 同じもの押したら解除
            if (selectedFlavor === value) {
                selectedFlavor = "";
            } else {
                selectedFlavor = value;
            }

            flavorInput.value = selectedFlavor;

            updateFlavorUI(selectedFlavor);
        });
    });

    function updateFlavorUI(value) {
        flavorButtons.forEach(btn => {
            btn.classList.toggle("active", btn.dataset.value === value);
        });
    }

});