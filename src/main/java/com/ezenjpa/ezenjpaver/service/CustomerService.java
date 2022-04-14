package com.ezenjpa.ezenjpaver.service;

import com.ezenjpa.ezenjpaver.DTO.FaqDTO;
import com.ezenjpa.ezenjpaver.DTO.OneToOneDTO;
import com.ezenjpa.ezenjpaver.entity.FaqEntity;
import com.ezenjpa.ezenjpaver.entity.GoodsEntity;
import com.ezenjpa.ezenjpaver.entity.OneToOneEntity;
import com.ezenjpa.ezenjpaver.entity.QuestionEntity;
import com.ezenjpa.ezenjpaver.repository.FaqRepository;
import com.ezenjpa.ezenjpaver.repository.OneToOneRepository;
import com.ezenjpa.ezenjpaver.repository.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class CustomerService {

    @Autowired
    FaqRepository faqRepository;
    @Autowired
    OneToOneRepository oneToOneRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    EntityUpdateUtil updateUtil;
    @Autowired
    HttpSession session;

    public Model getAllFaq(Model model){
        log.info("등록된 모든 faq를 가져옵니다.");
        List<FaqEntity> faqEntityList = faqRepository.findAll();
        model.addAttribute("faq", faqEntityList);
        return model;
    }

    public Model getFaqByCat(String cat, Model model){
        log.info("선택된 카테고리 : {} 에 해당하는 faq를 가져옵니다.", cat);
        List<FaqEntity> filtterd = faqRepository.getFaqEntitiesByFaqCat(cat);
        model.addAttribute("faq", filtterd)
                .addAttribute("faq_cat",cat);
        return model;
    }

    public void insertNewFaq(FaqDTO faq) throws InvocationTargetException, IllegalAccessException {
        log.info("다음 내용으로 새로운 faq를 등록합니다. {}", faq.toString());
        FaqEntity newFaqEntity = new FaqEntity();
        newFaqEntity = (FaqEntity) updateUtil.entityUpdateUtil(faq, newFaqEntity);
        faqRepository.save(newFaqEntity);
    }

    public void deleteFaq(Long faqIdx){
        log.info("faq idx : {} 를 삭제합니다.", faqIdx);
        faqRepository.deleteById(faqIdx);
    }

    public void insertNewAsk(OneToOneDTO oneToOne) throws InvocationTargetException, IllegalAccessException {
        log.info("새로운 1:1 문의를 등록합니다");
        OneToOneEntity newAsk = new OneToOneEntity();
        newAsk = (OneToOneEntity) updateUtil.entityUpdateUtil(oneToOne, newAsk);
        newAsk.setOneToOneDate(Date.from(Instant.now()));
        oneToOneRepository.save(newAsk);
    }

    public Model myAsk(Model model){
        Long userIdx = Long.valueOf((String) session.getAttribute("userIdx"));
        log.info("user idx : {} 로 작성된 모든 1:1문의와 상품상세문의를 가져옵니다.", userIdx);
        List<OneToOneEntity> myOneToOne = oneToOneRepository.getAllByUserEntityUserIdx(userIdx);
        List<QuestionEntity> myQuestion = questionRepository.getAllByUserEntityUserIdx(userIdx);
        List<GoodsEntity> goods = new ArrayList<>();
        myQuestion.forEach(q -> {
            GoodsEntity g = q.getGoodsEntity();
            goods.add(g);
        });
        model.addAttribute("oneToOne", myOneToOne)
                .addAttribute("question", myQuestion)
                .addAttribute("goods", goods);
        return model;
    }

    public Model getAskByCat(String cat, Model model){
        Long userIdx = Long.valueOf((String)session.getAttribute("userIdx"));
        log.info("user idx : {} 로 작성된 {} 카테고리 문의들을 가져옵니다.",userIdx,cat);
        List<OneToOneEntity> filtteredOneToOne = oneToOneRepository.getAllByUserEntityUserIdxAndOneToOneCat(userIdx,cat);
        List<QuestionEntity> myQuestion = questionRepository.getAllByUserEntityUserIdx(userIdx);
        model.addAttribute("oneToOne", filtteredOneToOne)
                .addAttribute("question", myQuestion)
                .addAttribute("onetoone_cat",cat);
        return model;
    }



}
