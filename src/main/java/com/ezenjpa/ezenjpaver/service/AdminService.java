package com.ezenjpa.ezenjpaver.service;

import com.ezenjpa.ezenjpaver.DTO.OneToOneDTO;
import com.ezenjpa.ezenjpaver.DTO.QuestionDTO;
import com.ezenjpa.ezenjpaver.DTO.UserDTO;
import com.ezenjpa.ezenjpaver.VO.UserListForAdmin;
import com.ezenjpa.ezenjpaver.entity.OneToOneEntity;
import com.ezenjpa.ezenjpaver.entity.QuestionEntity;
import com.ezenjpa.ezenjpaver.entity.UserEntity;
import com.ezenjpa.ezenjpaver.repository.OneToOneRepository;
import com.ezenjpa.ezenjpaver.repository.QuestionRepository;
import com.ezenjpa.ezenjpaver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    PagenationService pagenation;

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
        pagenation = pagenation.pagenationInfo(questionEntityPage, 10);
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
        QuestionEntity target = questionRepository.getById(question.getQuestionIdx());
        target.setQuestionReply(question.getQuestionReply());
        target.setQuestionIsReplied(1);
        target.setQuestionReplyDate(Date.from(Instant.now()));
        questionRepository.save(target);
    }

    public void registerOneToOneReply(OneToOneDTO oneToOne){
        OneToOneEntity target = oneToOneRepository.getById(oneToOne.getOnetooneIdx());
        target.setOneToOneReply(oneToOne.getOnetooneReply());
        target.setOntToOneIsReplied(1);
        target.setOneToOneReplyDate(Date.from(Instant.now()));
        oneToOneRepository.save(target);
    }


}
