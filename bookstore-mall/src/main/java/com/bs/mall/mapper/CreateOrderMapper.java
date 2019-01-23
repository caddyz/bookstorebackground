package com.bs.mall.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bs.mall.pojo.Cart;
import com.bs.mall.pojo.CreateOrder;
import com.bs.mall.pojo.ReceiveAddress;

public interface CreateOrderMapper {

	Integer insertOrder(@Param("order") CreateOrder order);

	Integer addOrderToBookId(@Param("carts") List<Cart> carts,@Param("orderId") String orderId);

	Integer insertOrderAddress(@Param("address") ReceiveAddress address);

	Integer insertUserScore(@Param("score") Double score,@Param("userId") Integer userId);

	
}
