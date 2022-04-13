package com.ezenjpa.ezenjpaver.service;

import com.ezenjpa.ezenjpaver.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
class GoodsListServiceTest {

    @Autowired
    GoodsRepository goodsRepository;


}