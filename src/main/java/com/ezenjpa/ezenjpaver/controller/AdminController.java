package com.ezenjpa.ezenjpaver.controller;

import com.ezenjpa.ezenjpaver.DTO.GoodsDTO;
import com.ezenjpa.ezenjpaver.DTO.OneToOneDTO;
import com.ezenjpa.ezenjpaver.DTO.QuestionDTO;
import com.ezenjpa.ezenjpaver.enums.Statement;
import com.ezenjpa.ezenjpaver.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("admin")
@Slf4j
public class AdminController {

    @Autowired
    AdminService adminService;

    @RequestMapping("")
    public String adminRoot(){
        return "admin/main";
    }

    @RequestMapping("main")
    public String main(){
        return "admin/main";
    }

    @RequestMapping("memberList")
    public String memberList(Model model){
        model = adminService.memberListForAdmin(model);
        return "admin/memberList";
    }
    // memberList 상단 필터
    @GetMapping("userSearchAction")
    public String memberListFilter(@RequestParam(name = "cat")String cat,
                                   @RequestParam(name = "searchText",required = false)String searchText,
                                   Model model) {
        model = adminService.memberListForAdminByFilter(cat, searchText, model);
        return "admin/memberList";
    }
    // 상세조회
    @GetMapping("memberListpopup")
    public String memberListPopup(@RequestParam("user_idx")Long userIdx, Model model){
        model = adminService.userInfo(userIdx, model);
        return "admin/memberListpopup";
    }

    @GetMapping("qnaList")
    public String qnaList(@RequestParam("Qna")String cat,Pageable pageable, Model model){
        if(cat.equals("Qna")){
            model = adminService.getQuestionList(pageable, model);
        }else{
            model = adminService.getOneToOneList(pageable, model);
        }
        return "admin/qnaList";
    }

    // qna 답글달기
    @PostMapping("registerQuestionReplyAction")
    @ResponseBody
    public String registerQnaReply(@RequestBody QuestionDTO question){
        adminService.registerQnaReply(question);
        return "등록되었습니다.";
    }
    // 1:1질문 답글달기
    @PostMapping("registerOneToOneReplyAction")
    @ResponseBody
    public String registerOneToOneReply(@RequestBody OneToOneDTO oneToOne){
        adminService.registerOneToOneReply(oneToOne);
        return "등록되었습니다.";
    }

    @RequestMapping("stock")
    public String stock(Pageable pageable, Model model) {
        if(!model.containsAttribute("goodslist")) {
            model = adminService.getGoodsListForAdmin(pageable, model);
        }
        model.addAttribute("entireItemCardMode", 2);
        return "admin/stock";
    }
    // stock search & onEvent search
    @GetMapping("adminStockSearchAction")
    public String stockSearch(@RequestParam(required = false) String searchText,
                              @RequestParam String search_cat,
                              @RequestParam String page,
                              Model model) {
        model = adminService.stockSearchFilter(searchText, search_cat, model);
        if(page.equals("stock")) {
            return stock(Pageable.unpaged(), model);
        }else {
            return eventConfig(Pageable.unpaged(), model);
        }
    }
    // stock 품절처리
    @PostMapping("inventorySoldOutAction")
    @ResponseBody
    public void soldOutGoods(@RequestBody HashMap<String, Boolean> param) {
        adminService.makeGoodsSoldOut(param);
    }
    // stock 상품 삭제
    @PostMapping("inventoryDeleteAction")
    @ResponseBody
    public String deleteGoods(@RequestParam HashMap<String, String> param) {
        adminService.deleteGoodsOnDB(param);
        return "<script>alert('삭제되었습니다.');location.href='stock';</script>";
    }
    // stock 발주
    @PostMapping("inventoryOrderAction")
    @ResponseBody
    public void orderGoods(@RequestBody HashMap<String, String> param) {
        log.info("{}",param.toString());
        adminService.orderGoods(param);
    }

    @RequestMapping("goods")
    public String goods() {
        return "admin/goods";
    }
    // 섬네일 등록
    @PostMapping("uploadGoodsThumbAction")
    @ResponseBody
    public String uploadeThumb(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        return adminService.uploadThumbnail(multipartFile);
    }
    // 상세이미지 등록
    @PostMapping("uploadGoodsDetailAction")
    @ResponseBody
    public String uploadDetail(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        return adminService.uploadDetail(multipartFile);
    }
    // 상품등록
    @PostMapping("productRegisterAction")
    @ResponseBody
    public String uploadGoods(@RequestBody GoodsDTO goods) throws InvocationTargetException, IllegalAccessException {
        return adminService.insertGoods(goods);
    }
    // 상품이미지등록
    @PostMapping("uploadGoodsIMGSAction")
    @ResponseBody
    public String uploadGoodsImgs(@RequestParam("img1")MultipartFile fileOne,
                                  @RequestParam("img2")MultipartFile fileTow,
                                  @RequestParam("img3")MultipartFile fileThree,
                                  @RequestParam("goods_idx")String idx) {
        Long goodsIdx = Long.valueOf(idx);
        List<MultipartFile> files = new ArrayList<>();
        files.add(fileOne);
        files.add(fileTow);
        files.add(fileThree);
        adminService.uploadGoodsImgs(files, goodsIdx);
        return "<script>alert('등록되었습니다.'); location.href='goods';</script>";
    }

    @RequestMapping("review")
    public String review(Pageable pageable, Model model) {
        model = adminService.reviewList(pageable, model);
        return "admin/review";
    }
    // 리뷰 답글 등록
    @PostMapping("registReviewReplyAction")
    @ResponseBody
    public String registReviewReply(@RequestBody HashMap<String, String> param) {
        adminService.registReviewReply(param);
        return "등록되었습니다.";
    }

    @GetMapping("transaction")
    public String transaction(@RequestParam(required = false) String statement,
                              Pageable pageable, Model model) {
        try {
            model = adminService.transactionFiltered(statement, model);
        } catch (Exception e) {
            log.error("{}",e);
            model = adminService.transaction(pageable, model);
        }
        log.info("{}",model.toString());
        return "admin/transaction";
    }

    @GetMapping("transactionpop")
    public String transactionpop(@RequestParam String purchase_idx, Model model) {
        int purchaseIdx = Integer.parseInt(purchase_idx);
        model = adminService.transactionDetail(purchaseIdx, model);
        return "admin/transactionpop";
    }
    // 주문상태 변경
    @PostMapping("changeStatementAction")
    @ResponseBody
    public String changeStatement(@RequestBody HashMap<String, String> param) {
        int purchase_idx = Integer.parseInt(param.get("purchase_idx"));
        String purchase_statement = param.get("purchase_statement");
        String returnString = adminService.changeStatement(purchase_idx, purchase_statement);
        return returnString;
    }


}
