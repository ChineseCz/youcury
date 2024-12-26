package com.chinesecz.service.rpc;

import com.chinesecz.domain.vo.ProductVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 模拟 用户调用 购买查询商品
 */
@Service
public class ProductRPC {

    public ProductVO queryProductByProductId(String productId){
        ProductVO productVO = new ProductVO();
        productVO.setProductId(productId);
        productVO.setProductName("测试商品");
        productVO.setProductDesc("这是一个测试商品");
        productVO.setPrice(new BigDecimal("1.68"));
        return productVO;

    }



}
