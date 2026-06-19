package com.example.iqos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * トップページ表示用コントローラ
 *
 * ブラウザからルートURL("/")にアクセスされた際に
 * index.html を表示する。
 */
@Controller // ← @RestController ではなく、画面用の @Controller を使います！
public class HomeController {

    /**
     * トップページを表示する
     *
     * URL:
     * GET /
     *
     * @return templates/index.html
     */
    // http://localhost:8081/ にアクセスされたら、templatesの中の index.html を開く設定
    @GetMapping("/")
    public String index() {
        return "index"; 
    }
}