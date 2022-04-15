package com.ezenjpa.ezenjpaver.service;

import com.ezenjpa.ezenjpaver.DTO.*;
import com.ezenjpa.ezenjpaver.VO.UserListForAdmin;
import com.ezenjpa.ezenjpaver.entity.*;
import com.ezenjpa.ezenjpaver.enums.Events;
import com.ezenjpa.ezenjpaver.enums.ImgCat;
import com.ezenjpa.ezenjpaver.enums.Statement;
import com.ezenjpa.ezenjpaver.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class AdminService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    OneToOneRepository oneToOneRepository;
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    GoodsImgsRepository goodsImgsRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    PurchaseRepository purchaseRepository;
    @Autowired
    PagenationService pagenation;
    @Autowired
    FileService fileService;
    @Autowired
    EntityUpdateUtil updateUtil;

    public Model memberListForAdmin(Model model){
        log.info("관리자용 사용자 리스트를 가져옵니다.");
        List<UserListForAdmin> userList = userRepository.getAllForAdmin();
        model.addAttribute("userlist", userList);
        return model;
    }

    public Model memberListForAdminByFilter(String cat, String searchText, Model model){
        log.info("사용자 리스트를 필터링합니다.");
        if(cat.equals("0")){
            log.info("{} 를 id에 포함하는 모든 사용자를 검색합니다.", searchText);
            searchText = "%"+searchText+"%";
            List<UserListForAdmin> listForAdmins = userRepository.getUserListsForAdminBySearching(searchText);
            model.addAttribute("userlist", listForAdmins);
            return model;
        }else{
            List<UserListForAdmin> filteredlist = new ArrayList<>();
            if(cat.equals("1")) {
                log.info("구매총량이 많은 순으로 재정렬합니다.");
                filteredlist = userRepository.getUserListsForAdminByFilter(Sort.by(new Sort.Order(Sort.Direction.DESC, "totalAmount", Sort.NullHandling.NULLS_LAST)));
            }
            if(cat.equals("2")) {
                log.info("구매총액이 많은 순으로 재정렬합니다.");
                filteredlist = userRepository.getUserListsForAdminByFilter(Sort.by(new Sort.Order(Sort.Direction.DESC, "totalPrice", Sort.NullHandling.NULLS_LAST)));
            }
            if(cat.equals("3")) {
                log.info("최근가입한 순으로 재정렬합니다.");
                filteredlist = userRepository.getUserListsForAdminByFilter(Sort.by(Sort.Direction.DESC,"joinDate"));
            }
            model.addAttribute("userlist", filteredlist);
            return model;
        }
    }

    public Model userInfo(Long userIdx, Model model){
        log.info("idx : {} 의 회원정보를 불러옵니다.",userIdx);
        UserEntity user = userRepository.getById(userIdx);
        UserDTO userInfo = user.convertToUserDTO(user);
        model.addAttribute("user", userInfo);
        return model;
    }

    public Model getQuestionList(Pageable pageable, Model model){
        log.info("{}번째 페이지 질문리스트를 가져옵니다.", pageable.getPageNumber()+1);
        Page<QuestionEntity> questionEntityPage = questionRepository.findAll(pageable);
        List<QuestionEntity> questions = questionEntityPage.getContent();
        pagenation = pagenation.pagenationInfo(questionEntityPage, 5);
        model.addAttribute("questionlist", questions)
                .addAttribute("mode", "Qna")
                .addAttribute("pages", pagenation);
        return model;
    }

    public Model getOneToOneList(Pageable pageable, Model model){
        log.info("{}번째 페이지 1:1문의 리스트를 가져옵니다.", pageable.getPageNumber()+1);
        Page<OneToOneEntity> oneToOneEntityPage = oneToOneRepository.findAll(pageable);
        List<OneToOneEntity> oneToOne = oneToOneEntityPage.getContent();
        pagenation = pagenation.pagenationInfo(oneToOneEntityPage, 10);
        model.addAttribute("questionlist", oneToOne)
                .addAttribute("mode", "OneToOne")
                .addAttribute("pages", pagenation);
        return model;
    }

    public void registerQnaReply(QuestionDTO question){
        log.info("goods idx : {} 에 올라온 문의({})에 답글을 등록합니다.",question.getGoodsIdx(),question.getQuestionIdx());
        QuestionEntity target = questionRepository.getById(question.getQuestionIdx());
        target.setQuestionReply(question.getQuestionReply());
        target.setQuestionIsReplied(1);
        target.setQuestionReplyDate(Date.from(Instant.now()));
        questionRepository.save(target);
    }

    public void registerOneToOneReply(OneToOneDTO oneToOne){
        log.info("user idx : {} 가 등록한 문의({}) 에 답글을 등록합니다.",oneToOne.getUserIdx(),oneToOne.getOnetooneIdx());
        OneToOneEntity target = oneToOneRepository.getById(oneToOne.getOnetooneIdx());
        target.setOneToOneReply(oneToOne.getOnetooneReply());
        target.setOntToOneIsReplied(1);
        target.setOneToOneReplyDate(Date.from(Instant.now()));
        oneToOneRepository.save(target);
    }

    public Model getGoodsListForAdmin(Pageable pageable, Model model){
        log.info("admin 용 상품리스트를 불러옵니다.");
        Page<GoodsEntity> allGoodsPages = goodsRepository.findAll(pageable);
        pagenation = pagenation.pagenationInfo(allGoodsPages, 5);
        model.addAttribute("goodslist",allGoodsPages.getContent())
                .addAttribute("page", pagenation);
        return model;
    }

    public Model stockSearchFilter(String searchText, String search_cat, Model model){
        if(search_cat.equals("goods_name")){
            log.info("이름으로 검색합니다.");
            List<GoodsEntity> goods = goodsRepository.getGoodsEntitiesByGoodsNameContaining(searchText);
            List<GoodsDTO> searched = goods.stream().map(g -> g.convertToGoodsDTO(g)).collect(Collectors.toList());
            model.addAttribute("goodslist", searched);
            return model;
        }else{
            List<GoodsEntity> goods;
            List<GoodsDTO> searched;
            switch (search_cat) {
                case "goods_onsale=1":
                    log.info("판매중인 상품을 가져옵니다.");
                    goods = goodsRepository.getGoodsEntitiesByGoodsOnSale(1);
                    break;
                case "goods_onsale=0":
                    log.info("품절 혹은 판매중지된 상품을 가져옵니다.");
                    goods = goodsRepository.getGoodsEntitiesByGoodsOnSale(0);
                    break;
                case "goods_cat":
                    log.info("{} 카테고리를 가진 상품을 가져옵니다.", searchText);
                    goods = goodsRepository.getGoodsEntitiesByGoodsCat(searchText);
                    break;
                case "goods_onevent":
                    log.info("{} 에 해당하는 상품을 가져옵니다.", searchText);
                    goods = goodsRepository.getGoodsEntitiesByGoodsOnEvent(Events.valueOf(searchText));
                    break;
                default:
                    goods = goodsRepository.findAll();
                    break;
            }
            searched = goods.stream().map(g -> g.convertToGoodsDTO(g)).collect(Collectors.toList());
            model.addAttribute("goodslist", searched);
            return model;
        }
    }

    public void makeGoodsSoldOut(HashMap<String, Boolean> list){
        list.forEach((k,v) -> {
            if(v){
                log.info("goods idx : {} 를 품절처리합니다.",k);
                GoodsEntity target = goodsRepository.getById(Long.valueOf(k));
                target.setGoodsOnSale(0);
                goodsRepository.save(target);
            }
        });
    }

    public void deleteGoodsOnDB(HashMap<String, String> list){
        list.forEach((k,v) -> {
            log.info("goods idx : {} 를 db에서 삭제합니다.", k);
            if(v.equals("on")){
                GoodsEntity target = goodsRepository.getById(Long.valueOf(k));
                goodsRepository.delete(target);
            }
        });
    }

    public void orderGoods(HashMap<String, String> list){
        int amount = Integer.parseInt(list.get("amount"));
        list.remove("amount");
        list.forEach((k,v) -> {
            log.info("goods idx : {} 의 수량을 {}만큼 증가시키고 판매중으로 변경합니다.",k,amount);
            if(v.equals("true")){
                GoodsEntity target = goodsRepository.getById(Long.valueOf(k));
                target.setGoodsStock(target.getGoodsStock()+amount);
                target.setGoodsOnSale(1);
                goodsRepository.save(target);
            }
        });
    }

    public String uploadThumbnail(MultipartFile file) throws Exception {
        log.info("thumbnail 이미지를 등록합니다.");
        return fileService.fileUploader(ImgCat.THUMB, file);
    }

    public String uploadDetail(MultipartFile file) throws Exception{
        log.info("detail 이미지를 등록합니다.");
        return fileService.fileUploader(ImgCat.DETAIL,file);
    }

    public String insertGoods(GoodsDTO goods) throws InvocationTargetException, IllegalAccessException {
        log.info("새로운 상품 {} 을 등록합니다.", goods.getGoodsName());
        GoodsEntity newGoods = new GoodsEntity();
        newGoods = (GoodsEntity) updateUtil.entityUpdateUtil(goods, newGoods);
        newGoods = goodsRepository.save(newGoods);
        return String.valueOf(newGoods.getGoodsIdx());
    }

    public void uploadGoodsImgs(List<MultipartFile> imgs, Long goodsIdx){
        imgs.forEach(img -> {
            String path;
            try {
                path = fileService.fileUploader(ImgCat.GOODS,img);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            log.info("상품 이미지를 {}에 저장하였습니다.",path);
            GoodsImgsEntity newImg = new GoodsImgsEntity();
            newImg.setGoodsEntity(goodsRepository.getById(goodsIdx));
            newImg.setGoodsImg(path);
            goodsImgsRepository.save(newImg);
        });
    }

    public Model reviewList(Pageable pageable, Model model){
        Page<ReviewEntity> reviewEntityPage = reviewRepository.findAll(pageable);
        List<ReviewEntity> reviews = reviewEntityPage.getContent();
        pagenation = pagenation.pagenationInfo(reviewEntityPage, 5);
        model.addAttribute("reviewlist",reviews)
                .addAttribute("pages", pagenation);
        return model;
    }

    public void registReviewReply(HashMap<String, String> reply){
        ReviewEntity target = reviewRepository.getById(Long.valueOf(reply.get("review_idx")));
        target.setReviewReply(reply.get("review_reply"));
        target.setReviewReplyDate(Date.from(Instant.now()));
        reviewRepository.save(target);
    }

    public Model transactionFiltered(String statement, Model model){
        List<PurchaseEntity> purchaseList;
        Optional<List<PurchaseEntity>> purchaseEntityOptional = Optional.ofNullable(
                purchaseRepository.getAllByPurchaseStatement(Statement.getStatementByDescription(statement)));
        if(purchaseEntityOptional.isPresent()){
            model.addAttribute("purchaselist", purchaseEntityOptional.get());
            return model;
        }else{
            if(statement.equals("최신순")){
                purchaseList = purchaseRepository.findAll(Sort.by(Sort.Direction.DESC, "purchaseDate"));
            }else if(statement.equals("오래된순")){
                purchaseList = purchaseRepository.findAll(Sort.by(Sort.Direction.ASC, "purchaseDate"));
            }else{
                purchaseList = purchaseRepository.findAll();
            }
            model.addAttribute("purchaselist", purchaseList);
            return model;
        }
    }

    public Model transaction(Pageable pageable, Model model){
        Page<PurchaseEntity> purchaseEntityPage = purchaseRepository.findAll(pageable);
        pagenation = pagenation.pagenationInfo(purchaseEntityPage, 5);
        model.addAttribute("purchaselist", purchaseEntityPage.getContent())
                .addAttribute("pages", pagenation);
        return model;
    }



}
