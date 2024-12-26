package com.chinesecz.controller;



import com.chinesecz.domain.res.PayOrderRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("api/v1/shopcart/")
public class ShopCartController {

    @GetMapping(value =  "product")
    public PayOrderRes createOrder(@RequestParam("productId") String productId) {
        try {

        } catch (Exception e) {

        }
        return null;
    }

}
