package com.ezenjpa.ezenjpaver.controller;

import com.ezenjpa.ezenjpaver.service.GoodsListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("goodsList")
public class GoodsListController {

    @Autowired
    GoodsListService goodsListService;

    @RequestMapping("goodsList")
    public String goodsList(Model model){
        model = goodsListService.getAllGoodsList(model);
        return "goodsList/goodsList";
    }

}
