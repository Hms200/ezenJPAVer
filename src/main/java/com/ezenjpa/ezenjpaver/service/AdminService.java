package com.ezenjpa.ezenjpaver.service;

import com.ezenjpa.ezenjpaver.DTO.UserDTO;
import com.ezenjpa.ezenjpaver.VO.UserListForAdmin;
import com.ezenjpa.ezenjpaver.entity.UserEntity;
import com.ezenjpa.ezenjpaver.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class AdminService {

    @Autowired
    UserRepository userRepository;

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
        UserEntity user = userRepository.getById(userIdx);
        UserDTO userInfo = user.convertToUserDTO(user);
        model.addAttribute("user", userInfo);
        return model;
    }


}
