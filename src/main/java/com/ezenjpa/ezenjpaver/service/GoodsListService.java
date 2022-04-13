package com.ezenjpa.ezenjpaver.service;

import com.ezenjpa.ezenjpaver.entity.GoodsEntity;
import com.ezenjpa.ezenjpaver.repository.GoodsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
public class GoodsListService {

    @Autowired
    GoodsRepository goodsRepository;

    public Model getAllGoodsList(Model model){
        List<GoodsEntity> allGoods = goodsRepository.getGoodsEntitiesByGoodsOnSale(1);
        model.addAttribute("list", allGoods);
        return model;
    }

}
