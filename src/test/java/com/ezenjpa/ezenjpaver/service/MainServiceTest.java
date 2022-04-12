package com.ezenjpa.ezenjpaver.service;

import com.ezenjpa.ezenjpaver.DTO.GoodsDTO;
import com.ezenjpa.ezenjpaver.DTO.NoticeDTO;
import com.ezenjpa.ezenjpaver.DTO.ReviewDTO;
import com.ezenjpa.ezenjpaver.entity.GoodsEntity;
import com.ezenjpa.ezenjpaver.entity.NoticeEntity;
import com.ezenjpa.ezenjpaver.entity.ReviewEntity;
import com.ezenjpa.ezenjpaver.enums.Events;
import com.ezenjpa.ezenjpaver.repository.GoodsRepository;
import com.ezenjpa.ezenjpaver.repository.NoticeRepository;
import com.ezenjpa.ezenjpaver.repository.ReviewRepository;
import com.ezenjpa.ezenjpaver.repository.UserRepository;
import org.hibernate.annotations.Comment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MainServiceTest {

    @Autowired
    HttpSession session;
    @Autowired
    UserRepository userRepository;
    @Autowired
    NoticeRepository noticeRepository;
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    ReviewRepository reviewRepository;
    GoodsEntity goodsEntity;

    @Test
    @Comment("카트숫자 설정. 로그인 안했을 때")
    void setCartBedgeNumber() {


        Integer userIdx;
        Long cartBedgeNum;



        try {
            userIdx = Integer.parseInt(String.valueOf(session.getAttribute("userIdx")));

        }catch (Exception e){

            userIdx = null;
        }

        if(userIdx != null){

            try {
                cartBedgeNum = userRepository.countUserEntitiesByUserIdxAndCartEntitiesNotNull(Long.valueOf(userIdx));

            }catch (NullPointerException e){

                cartBedgeNum = 0L;
            }
            session.setAttribute("cart", cartBedgeNum);

        }else{
            cartBedgeNum = 0L;
            session.setAttribute("cart", cartBedgeNum);

        }
        Assertions.assertEquals(0L, cartBedgeNum, "");
    }

    @Test
    @Comment("메인용 공지사항 리스트 가져오기")
    void setNoticeForMain() {
        List<NoticeEntity> noticeEntities = new ArrayList<>();
        noticeEntities = noticeRepository.getNoticeEntitiesByNoticeShow(1);

        Assertions.assertNotNull(noticeEntities);
    }

    @Test
    @Comment("Best Item Card data 가져오기")
    void bestItemCardData(){
        List<GoodsEntity> goodsEntities = goodsRepository.getTop10ByGoodsOnSaleOrderByGoodsPurchasedDesc(1);
        int size = goodsEntities.size();

        Assertions.assertEquals(10, size);
    }
    @Test
    @Comment("이벤트 설정에 따른 card data 가져오기")
    void mdPickCardData(){

        List<GoodsEntity> goodsEntities = goodsRepository.getGoodsEntitiesByGoodsOnEventAndGoodsOnSale(Events.MD_PICK, 1);
        List<GoodsDTO> goodsDTOList = new ArrayList<>();
        goodsEntities.forEach(entity -> {
            GoodsDTO goodsDTO = entity.convertToGoodsDTO(entity);
            goodsDTOList.add(goodsDTO);
        });;
        System.out.println(goodsDTOList.toString());
        int size = goodsDTOList.size();
        Assertions.assertEquals(3, size);

    }

    @Test
    @Comment("namedNativeQuery 동작, resultset mapping 동작 확동")
    void reviews(){
        List<ReviewDTO> reviews = reviewRepository.reviews();

        System.out.println(reviews.toString());
    }

    @Test
    @Comment("recent review 동작확인")
    void recentReviews(){
        List<ReviewDTO> recentReviews = reviewRepository.recentReviews();

        System.out.println(recentReviews.toString());
    }

    @Test
    @Comment("판매중인 모든 상품 가져오기 동작확인")
    void entireItem(){
        List<GoodsEntity> goodsEntities = goodsRepository.getGoodsEntitiesByGoodsOnSale(1);
        int size = goodsEntities.size();
        System.out.println(size);
    }
}