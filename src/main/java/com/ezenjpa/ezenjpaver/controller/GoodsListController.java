package com.ezenjpa.ezenjpaver.controller;

import com.ezenjpa.ezenjpaver.DTO.CartDTO;
import com.ezenjpa.ezenjpaver.DTO.QuestionDTO;
import com.ezenjpa.ezenjpaver.service.GoodsListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Optional;

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
        goodsListService.addGoodsInCart(cart);
        return String.valueOf(session.getAttribute("cart"));
    }

    // 질문작성
    @PostMapping("productQnaWriteAction")
    @ResponseBody
    public String writeQuestion(@ModelAttribute QuestionDTO question) throws InvocationTargetException, IllegalAccessException {
        goodsListService.insertQuestion(question);
        return "<script>alert('등록되었습니다.');location.reload;</script>";
    }

    @RequestMapping("cart")
    public String cart(Model model){
        Optional<String> userId =  Optional.ofNullable((String)session.getAttribute("userId"));
        if(userId.isEmpty()){
            String errorMessage = "로그인 하신 후 이용하실 수 있습니다.";
            model.addAttribute("errorMessage", errorMessage);
            return "login/login";
        }else {
            model = goodsListService.getGoodsInCart(model);
            return "goodsList/cart";
        }
    }

    // 카트 옵션, 수량 변경
    @PostMapping("changeValueAction")
    @ResponseBody
    public void changeValue(@RequestBody CartDTO cart) throws InvocationTargetException, IllegalAccessException {
        goodsListService.changeValueOfItemInCart(cart);
    }
    // 카트 상품 삭제
    @PostMapping("removeGoodsFromCartAction")
    @ResponseBody
    public void removeGoods(@RequestBody HashMap<String, String> cartIdxs){
        goodsListService.removeGoodsFromCart(cartIdxs);
    }
    // 장바구니 개별항목 리스트로 묶기
    @PostMapping("listingGoodsAction")
    @ResponseBody
    public String listingGoods(@RequestParam HashMap<String, String> cartIdxs){
        Long cartListIdx = goodsListService.listingGoods(cartIdxs);
        return String.valueOf(cartListIdx);
    }


}
