package com.ezenjpa.ezenjpaver.controller;

import com.ezenjpa.ezenjpaver.DTO.CartDTO;
import com.ezenjpa.ezenjpaver.service.GoodsListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;

@Slf4j
@Controller
@RequestMapping("goodsList")
public class GoodsListController {

    @Autowired
    GoodsListService goodsListService;
    @Autowired
    HttpSession session;

    @RequestMapping("goodsList")
    public String goodsList(Model model){
        model = goodsListService.getAllGoodsList(model);
        return "goodsList/goodsList";
    }

    @GetMapping("goodsDetail")
    public String goodsDetail(@RequestParam Long goods_idx, Model model){
        model = goodsListService.getGoodsDetail(goods_idx, model);
        return "goodsList/goodsDetail";
    }

    // 장바구니에 담기
    @PostMapping("toShoppingCartAction")
    @ResponseBody
    public String toShoppingCart(@RequestBody CartDTO cart) throws InvocationTargetException, IllegalAccessException {
        return String.valueOf(session.getAttribute("cart"));
    }

    // 질문작성
    @PostMapping("productQnaWriteAction")

}
