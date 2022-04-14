package com.ezenjpa.ezenjpaver.controller;

import com.ezenjpa.ezenjpaver.DTO.CartDTO;
import com.ezenjpa.ezenjpaver.DTO.PurchaseDTO;
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
    public String listingGoods(@RequestBody HashMap<String, String> cartIdxs){
        Long cartListIdx = goodsListService.listingGoods(cartIdxs);
        return String.valueOf(cartListIdx);
    }

    @GetMapping("purchase")
    public String purchase(@RequestParam("cart_list_idx") Long cartListIdx, Model model){
        model = goodsListService.getListedGoods(cartListIdx, model);
        return "goodsList/purchase";
    }

    // 구매 전 비밀번호 재확인
    @PostMapping("checkPwAction")
    @ResponseBody
    public Boolean checkPw(@RequestBody HashMap<String, String> pw) {
        String password = pw.get("inputtedPw");
        return  goodsListService.checkPw(password);
    }
    // 결제 진행 완료 후 구매기록 저장 & 장바구니항목 구매됨으로 변경 & 상품 구매카운터 증가
    // 각 상품 구매 후 남은 재고 없으면 품절처리 & 장바구니 배지 숫자 재설정
    @PostMapping("makePurchaseAction")
    @ResponseBody
    public void makePurchase(@RequestBody PurchaseDTO purchase) throws InvocationTargetException, IllegalAccessException {
        goodsListService.makePurchase(purchase);
    }

}
