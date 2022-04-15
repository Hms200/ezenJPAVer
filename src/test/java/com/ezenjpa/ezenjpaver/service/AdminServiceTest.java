package com.ezenjpa.ezenjpaver.service;

import com.ezenjpa.ezenjpaver.DTO.OneToOneDTO;
import com.ezenjpa.ezenjpaver.entity.OneToOneEntity;
import com.ezenjpa.ezenjpaver.repository.OneToOneRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AdminServiceTest {

    @Autowired
    OneToOneRepository oneToOneRepository;

    @Test
    void getOneToOneList() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<OneToOneEntity> oneToOneEntityPage = oneToOneRepository.findAll(pageable);
        List<OneToOneDTO> oneToOneDTOS = oneToOneEntityPage.getContent().stream().map(o -> o.convertToOneToOneDTO(o)).collect(Collectors.toList());
        oneToOneDTOS.forEach(System.out::println);
    }
}