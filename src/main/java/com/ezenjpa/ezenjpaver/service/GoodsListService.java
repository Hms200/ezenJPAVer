package com.ezenjpa.ezenjpaver.service;

import com.ezenjpa.ezenjpaver.DTO.CartDTO;
import com.ezenjpa.ezenjpaver.entity.*;
import com.ezenjpa.ezenjpaver.repository.CartRepository;
import com.ezenjpa.ezenjpaver.repository.GoodsRepository;
import com.ezenjpa.ezenjpaver.repository.OptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class GoodsListService {

    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    OptionRepository optionRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    MainService mainService;
    @Autowired
    EntityUpdateUtil updateUtil;


    public Model getAllGoodsList(Model model){
        log.info("모든 상품 리스트를 가져옵니다.");
        List<GoodsEntity> allGoods = goodsRepository.getGoodsEntitiesByGoodsOnSale(1);
        model.addAttribute("list", allGoods);
        return model;
    }

    public Model getGoodsDetail(Long goodsIdx, Model model){
        log.info("상품 상세정보를 가져옵니다.");
        GoodsEntity goods = goodsRepository.getByGoodsIdx(goodsIdx);
        List<ReviewEntity> review = goods.getReviewEntities();
        List<QuestionEntity> question = goods.getQuestionEntities();
        List<GoodsImgsEntity> goodsImgs = goods.getGoodsImgsEntities();
        List<ReviewImgsEntity> reviewImgs = new ArrayList<>();
        review.forEach(r -> {
            reviewImgs.add(r.getReviewImgsEntities());
        });
        List<OptionEntity> options = optionRepository.getAll();

        model.addAttribute("goods", goods)
            .addAttribute("reviewList", review)
            .addAttribute("reviewImgList", reviewImgs)
            .addAttribute("goodsImgs", goodsImgs)
            .addAttribute("goodsOptions", options)
            .addAttribute("questionList", question);

        return model;
    }

    public void addGoodsInCart(CartDTO cart) throws InvocationTargetException, IllegalAccessException {
        log.info("idx = {} 상품을 장바구니에 담습니다.", cart.getGoodsIdx());
        CartEntity newCart = new CartEntity();
        newCart = (CartEntity) updateUtil.entityUpdateUtil(cart, newCart);
        newCart = cartRepository.save(newCart);
        log.info("cart idx : {} 부여됨", newCart.getCartIdx());
        mainService.setCartBedgeNumber();
    }

}
