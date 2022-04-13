package com.ezenjpa.ezenjpaver.service;

import com.ezenjpa.ezenjpaver.DTO.ReviewDTO;
import com.ezenjpa.ezenjpaver.DTO.UserDTO;
import com.ezenjpa.ezenjpaver.VO.PurchaseListVO;
import com.ezenjpa.ezenjpaver.entity.PurchaseEntity;
import com.ezenjpa.ezenjpaver.entity.ReviewEntity;
import com.ezenjpa.ezenjpaver.entity.ReviewImgsEntity;
import com.ezenjpa.ezenjpaver.entity.UserEntity;
import com.ezenjpa.ezenjpaver.enums.ImgCat;
import com.ezenjpa.ezenjpaver.enums.Statement;
import com.ezenjpa.ezenjpaver.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@Transactional
public class MyPageService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    PurchaseRepository purchaseRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ReviewImgsRepository reviewImgsRepository;
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    HttpSession session;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    EntityUpdateUtil updateUtil;
    @Autowired
    FileService fileService;

    //member info
    public Model memberInfo(Model model){
        log.info("로그인한 사용자 정보를 가져옵니다.");
        String userIdx = (String) session.getAttribute("userIdx");
        UserEntity userEntity = userRepository.getById(Long.valueOf(userIdx));
        UserDTO userDTO = userEntity.convertToUserDTO(userEntity);
        model.addAttribute("user", userDTO);
        return model;
    }

    //update member info
    public void updateMemberInfo(UserDTO user) throws InvocationTargetException, IllegalAccessException {
        Long userIdx = Long.valueOf((String) session.getAttribute("userIdx"));
        String encodedPw = passwordEncoder.encode(user.getUserPw());
        UserEntity userEntity = userRepository.getById(userIdx);
        userEntity = (UserEntity) updateUtil.entityUpdateUtil(user, userEntity);
        userEntity.setUserPw(encodedPw);
        userRepository.save(userEntity);
        log.info("user 정보 업데이트 완료.");
    }

    //purchase list
    public Model purchaseList(Model model){
        Long userIdx = Long.valueOf((String) session.getAttribute("userIdx")) ;
        log.info("user idx = {} 의 구매내역을 가져옵니다.", userIdx);
        List<PurchaseListVO> purchaseList = cartRepository.makingPurchaseListForMyPage(userIdx);
        model.addAttribute("List", purchaseList);
        return model;
    }
    //purchase list filter
    public Model purchaseListFilter(Model model, Integer cat){
        Long userIdx = Long.valueOf((String) session.getAttribute("userIdx"));
        Statement stmt = Statement.getStatementByCode(cat);
        log.info("다음 주문상태의 리스트로 필터링합니다. {}", stmt.getDescription());
        List<PurchaseListVO> purchaseList = cartRepository.makingPurchaseListForMyPageByCat(userIdx,stmt.getDescription());
        model.addAttribute("List", purchaseList);
        return model;
    }
    //purchase list 환불,교환,취소 신청
    public void changeStatement(Long userIdx, String ask){
        PurchaseEntity purchase = purchaseRepository.getByUserEntityUserIdx(userIdx);
        Statement stmt;
        switch (ask){
            case "refund" : stmt = Statement.REFUND_ASKED; break;
            case "change" : stmt = Statement.CHANGE_ASKED; break;
            case "cancel" : stmt = Statement.CANCEL; break;
            default: stmt = null;
        }
        log.info("{} (으)로 주문상태를 변경합니다.", stmt.getDescription());
        purchase.setPurchaseStatement(stmt.getDescription());
        purchaseRepository.save(purchase);
    }

    // 리뷰 작성
    public Long insertReview(ReviewDTO review) throws InvocationTargetException, IllegalAccessException {
      ReviewEntity newReview = ReviewEntity.builder()
              .userEntity(userRepository.getById(review.getUserIdx()))
              .goodsEntity(goodsRepository.getById(review.getGoodsIdx()))
              .reviewStar(review.getReviewStar())
              .reviewContents(review.getReviewContents())
              .reviewDate(Date.from(Instant.now()))
              .reviewIsReplied(0)
              .build();
      newReview = reviewRepository.save(newReview);
      return newReview.getReviewIdx();

    }
    // 리뷰 이미지 등록
    public void uploadeImg(MultipartFile multipartFile, Long reviewIdx) throws Exception {
        if(multipartFile.isEmpty()){
            log.info("파일이 없습니다.");
        }else {
            String img = fileService.fileUploader(ImgCat.REVIEWS, multipartFile);
            log.info("이미지 저장됨. 경로 = {}", img);
            ReviewImgsEntity reviewImg = ReviewImgsEntity.builder()
                    .reviewImg(img)
                    .reviewEntity(reviewRepository.getById(reviewIdx))
                    .build();
            reviewImgsRepository.save(reviewImg);
        }
    }
}
