package com.ezenjpa.ezenjpaver.service;

import com.ezenjpa.ezenjpaver.DTO.FaqDTO;
import com.ezenjpa.ezenjpaver.entity.FaqEntity;
import com.ezenjpa.ezenjpaver.repository.FaqRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
@Transactional
@Slf4j
public class CustomerService {

    @Autowired
    FaqRepository faqRepository;
    @Autowired
    EntityUpdateUtil updateUtil;

    public Model getAllFaq(Model model){
        log.info("등록된 모든 faq를 가져옵니다.");
        List<FaqEntity> faqEntityList = faqRepository.getAll();
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
        faqRepository.deleteById(faqIdx);
    }
}
