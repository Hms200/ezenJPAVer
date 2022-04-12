package com.ezenjpa.ezenjpaver.service;

import com.ezenjpa.ezenjpaver.DTO.GoodsDTO;
import com.ezenjpa.ezenjpaver.DTO.NoticeDTO;
import com.ezenjpa.ezenjpaver.DTO.ReviewDTO;
import com.ezenjpa.ezenjpaver.entity.GoodsEntity;
import com.ezenjpa.ezenjpaver.entity.NoticeEntity;
import com.ezenjpa.ezenjpaver.enums.Events;
import com.ezenjpa.ezenjpaver.repository.GoodsRepository;
import com.ezenjpa.ezenjpaver.repository.NoticeRepository;
import com.ezenjpa.ezenjpaver.repository.ReviewRepository;
import com.ezenjpa.ezenjpaver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class MainService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    NoticeRepository noticeRepository;
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    HttpSession session;

    public void setCartBedgeNumber(){
        log.info("카트 숫자를 설정합니다.");

        Integer userIdx;
        Long cartBedgeNum;

        try {
            userIdx = Integer.parseInt(String.valueOf(session.getAttribute("userIdx")));
            log.info("user idx = {}",userIdx);
        }catch (Exception e){
            log.error("로그인 하지 않은 사용자");
            userIdx = null;
        }

        if(userIdx != null){
            try {
                cartBedgeNum = userRepository.countUserEntitiesByUserIdxAndCartEntitiesNotNull(Long.valueOf(userIdx));
                log.info("Number Of Cart-Bedge = {}", cartBedgeNum);
            }catch (NullPointerException e){
                log.error("빈 카트 ");
                cartBedgeNum = 0L;
            }
            session.setAttribute("cart", cartBedgeNum);
            log.info("카트 숫자 설정 됨");
        }else{
            cartBedgeNum = 0L;
            session.setAttribute("cart", cartBedgeNum);
            log.info("빈 카트, 숫자 설정 됨");
        }
    }

    public Model setNoticeForMain(Model model){
        log.info("main용 중요공지사항을 가져옵니다.");
        List<NoticeEntity> noticeEntities = noticeRepository.getNoticeEntitiesByNoticeShow(1);
        List<NoticeDTO> noticeForMain = new ArrayList<>();
        noticeEntities.forEach(entity -> {
            NoticeDTO notice = entity.convertToNoticeDTO(entity);
            noticeForMain.add(notice);
        });
        model.addAttribute("noticeList", noticeForMain);
        return model;
    }

    public Model setCardData(Model model){
        log.info("best item card용 data를 가져옵니다.");
        List<GoodsEntity> bestItemEntities = goodsRepository.getTop10ByGoodsOnSaleOrderByGoodsPurchasedDesc(1);
        List<GoodsDTO> bestItmeCards = new ArrayList<>();
        bestItemEntities.forEach(entity -> {
            GoodsDTO goodsDTO = entity.convertToGoodsDTO(entity);
            bestItmeCards.add(goodsDTO);
        });
        model.addAttribute("best", bestItmeCards);

        log.info("MD Pick card용 data를 가져옵니다.");
        List<GoodsEntity> mdPickEntities = goodsRepository.getGoodsEntitiesByGoodsOnEventAndGoodsOnSale(Events.MD_PICK,1);
        List<GoodsDTO> MDPickCards = new ArrayList<>();
        mdPickEntities.forEach(entity -> {
            GoodsDTO goodsDTO = entity.convertToGoodsDTO(entity);
            MDPickCards.add(goodsDTO);
        });
        model.addAttribute("mdPick", MDPickCards);

        log.info("할인상품 card용 data를 가져옵니다.");
        List<GoodsEntity> discountEntities = goodsRepository.getGoodsEntitiesByGoodsOnEventAndGoodsOnSale(Events.DISCOUN,1);
        List<GoodsDTO> discountCards =  new ArrayList<>();
        discountEntities.forEach(entity -> {
            GoodsDTO goodsDTO = entity.convertToGoodsDTO(entity);
            discountCards.add(goodsDTO);
        });
        model.addAttribute("discount", discountCards);

        log.info("best review card용 data를 가져옵니다.");
        List<ReviewDTO> reviews = reviewRepository.recentReviews();
        model.addAttribute("reviews", reviews);

        log.info("Entire Item Card용 data를 가져옵니다.");
        List<GoodsEntity> entireItemEntities = goodsRepository.getGoodsEntitiesByGoodsOnSale(1);
        List<GoodsDTO> entireItemCards = new ArrayList<>();
        entireItemEntities.forEach(entity -> {
            GoodsDTO goodsDTO = entity.convertToGoodsDTO(entity);
            entireItemCards.add(goodsDTO);
        });
        model.addAttribute("goodsList", entireItemCards);
        if(model.getAttribute("entireItemCardMode") == null) {
            model.addAttribute("entireItemCardMode", 0);
        }

        return model;
    }

    public Model mainSearch(String searchText, Model model){
        log.info("main search 상품을 검색합니다.");
        List<GoodsEntity> searchedEntity = goodsRepository.getGoodsEntitiesByGoodsNameContaining(searchText);
        List<GoodsDTO> searchedGoods = new ArrayList<>();
        searchedEntity.forEach(entity -> {
            GoodsDTO goodsDTO = entity.convertToGoodsDTO(entity);
            searchedGoods.add(goodsDTO);
        });
        log.info("{}건 검색됨.", searchedEntity.size());
        model.addAttribute("searched", searchedGoods);
        model.addAttribute("entireItemCardMode", 1);
        return model;
    }
}
