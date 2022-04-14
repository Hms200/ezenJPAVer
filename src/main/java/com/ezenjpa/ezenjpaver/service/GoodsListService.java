package com.ezenjpa.ezenjpaver.service;

import com.ezenjpa.ezenjpaver.DTO.CartDTO;
import com.ezenjpa.ezenjpaver.DTO.PurchaseDTO;
import com.ezenjpa.ezenjpaver.DTO.QuestionDTO;
import com.ezenjpa.ezenjpaver.entity.*;
import com.ezenjpa.ezenjpaver.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    QuestionRepository questionRepository;
    @Autowired
    CartListRepository cartListRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PurchaseRepository purchaseRepository;
    @Autowired
    MainService mainService;
    @Autowired
    EntityUpdateUtil updateUtil;
    @Autowired
    HttpSession session;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    public Model getAllGoodsList(Model model) {
        log.info("판매중인 모든 상품 리스트를 가져옵니다.");
        List<GoodsEntity> allGoods = goodsRepository.getGoodsEntitiesByGoodsOnSale(1);
        model.addAttribute("list", allGoods);
        return model;
    }

    public Model getGoodsDetail(Long goodsIdx, Model model) {
        log.info("상품 상세정보를 가져옵니다.");
        GoodsEntity goods = goodsRepository.getByGoodsIdx(goodsIdx);
        List<ReviewEntity> review = goods.getReviewEntities();
        List<QuestionEntity> question = goods.getQuestionEntities();
        List<GoodsImgsEntity> goodsImgs = goods.getGoodsImgsEntities();
        List<ReviewImgsEntity> reviewImgs = new ArrayList<>();
        review.forEach(r -> reviewImgs.add(r.getReviewImgsEntities()));
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
        cartRepository.save(newCart);
        mainService.setCartBedgeNumber();
    }

    public void insertQuestion(QuestionDTO question) throws InvocationTargetException, IllegalAccessException {
        log.info("상품 idx : {} 에 새로운 질문을 작성합니다.", question.getGoodsIdx());
        QuestionEntity newQuestion = new QuestionEntity();
        newQuestion = (QuestionEntity) updateUtil.entityUpdateUtil(question, newQuestion);
        questionRepository.save(newQuestion);
    }

    public Model getGoodsInCart(Model model) {
        Long userIdx = Long.valueOf((String) session.getAttribute("userIdx"));
        log.info("idx : {} 의 카트에 담긴 상품을 불러옵니다.", userIdx);
        List<CartEntity> cartList = cartRepository.getAllByCartIsDoneAndUserEntityUserIdx(0, userIdx);
        List<GoodsEntity> goodsList = new ArrayList<>();
        cartList.forEach(cart -> goodsList.add(cart.getGoodsEntity()));
        List<OptionEntity> optionList = optionRepository.getAll();
        model.addAttribute("cartlist", cartList)
                .addAttribute("goodslist", goodsList)
                .addAttribute("optionlist", optionList);
        return model;
    }

    public void changeValueOfItemInCart(CartDTO cart) throws InvocationTargetException, IllegalAccessException {
        log.info("cart idx : {} 에 담긴 정보를 변경합니다.",cart.getCartIdx());
        CartEntity original = cartRepository.getById(cart.getCartIdx());
        original = (CartEntity) updateUtil.entityUpdateUtil(cart, original);
        cartRepository.save(original);
    }

    public void removeGoodsFromCart(HashMap<String, String> list){
        Map<String, String> targets = new HashMap<>();
        list.forEach((k,v) -> {
            if(v.equals("true")){
                targets.put(k, v);
            }
        });
        log.info("다음 cart idx를 가진 항목을 삭제합니다. - {}",targets.keySet());
        targets.forEach((k,v) -> cartRepository.deleteById(Long.valueOf(k)));
    }

    public Long listingGoods(HashMap<String, String> list){
        CartListEntity newCartListEntity = new CartListEntity();
        final CartListEntity newCartList = cartListRepository.save(newCartListEntity);
        log.info("생성된 cart list idx - {}",newCartList.getCartListIdx());

        Map<String, String> targets = new HashMap<>();
        list.forEach((k,v) -> {
            if(v.equals("true")){
                targets.put(k, v);
            }
        });
        log.info("다음 cart idx를 가진 항목을 하나의 리스트로 묶습니다. - {}",targets);
        targets.forEach((k,v) -> {
            CartEntity cart = cartRepository.getById(Long.valueOf(k));
            cart.setCartListEntity(newCartList);
            cartRepository.save(cart);
        });
        return newCartList.getCartListIdx();
    }

    public Model getListedGoods(Long cartListIdx, Model model){
        log.info("구매 대상 목록을 불러옵니다.");
        List<CartEntity> cartList = cartRepository.getAllByCartIsDoneAndCartListEntityCartListIdx(0, cartListIdx);
        List<GoodsEntity> goodsList = new ArrayList<>();
        List<OptionEntity> optionList = optionRepository.getAll();
        UserEntity userInfo = cartList.get(0).getUserEntity();
        cartList.forEach(cart -> {
            GoodsEntity goods = cart.getGoodsEntity();
            goodsList.add(goods);
        });
        model.addAttribute("cartlist", cartList)
                .addAttribute("goodslist", goodsList)
                .addAttribute("optionlist", optionList)
                .addAttribute("userinfo", userInfo)
                .addAttribute("cartlistidx", cartListIdx);
        return model;
    }

    public Boolean checkPw(String password){
        Long userIdx = Long.valueOf((String) session.getAttribute("userIdx"));
        log.info("user idx : {}  의 비밀번호 일치여부를 확인합니다.", userIdx);
        UserEntity user = userRepository.getById(userIdx);
        return passwordEncoder.matches(password,user.getUserPw());
    }

    public void makePurchase(PurchaseDTO purchase) throws InvocationTargetException, IllegalAccessException {
        log.info("구매기록을 저장합니다.");
        PurchaseEntity newPurchaseEntity = new PurchaseEntity();
        newPurchaseEntity = (PurchaseEntity) updateUtil.entityUpdateUtil(purchase, newPurchaseEntity);
        final PurchaseEntity newPurchase = purchaseRepository.save(newPurchaseEntity);

        Long cartListIdx = newPurchase.getCartListEntity().getCartListIdx();
        log.info("cart list idx : {}로 묶인 항목을 구매됨으로 변경, 구매한 수량만큼 구매카운터 증가, 재고감소, 재고소진 시 품절처리 합니다.", cartListIdx);
        List<CartEntity> carts = cartRepository.getAllByCartIsDoneAndCartListEntityCartListIdx(0, cartListIdx);
        carts.forEach(cart -> {
            cart.setCartIsDone(1);
            GoodsEntity goods = cart.getGoodsEntity();
            goods.setGoodsPurchased(goods.getGoodsPurchased() + cart.getCartAmount());
            goods.setGoodsStock(goods.getGoodsStock() - cart.getCartAmount());
            if(goods.getGoodsStock()<=0){
                goods.setGoodsStock(0);
                goods.setGoodsOnSale(0);
            }
            cartRepository.save(cart);
            goodsRepository.save(goods);
        });
        mainService.setCartBedgeNumber();

    }

}


