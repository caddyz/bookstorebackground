package com.bs.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bs.admin.pojo.Order;

/**
 * 
* <p>Title: OrderMapper</p>  
* <p>Description: 订单mapper</p>  
* @author 胡杰  
* <p> @date 2018年12月7日</p>
 */
public interface OrderMapper {

	/**
	 * 
	 * <p>Title: getOrderByOrderInfo</p>  
	 * <p>Description: 查询订单信息</p>  
	 * @param orderId              订单id
	 * @param orderStatus		 订单状态
	 * @param topPrice			 价格区间（第二个数）
	 * @param bottomPrice		 价格区间（第一个数）
	 * @param start					 开始行数（分页需要）
	 * @param count					 结束行数
	 * @return  
	 * <p> @date 2018年12月7日  </p>
	 */
	List<Order> getOrderByOrderInfo(@Param("orderId") String orderId, @Param("orderStatus") String orderStatus,
			@Param("topPrice") Double topPrice, @Param("bottomPrice") Double bottomPrice,
			@Param("start") Integer start, @Param("count") Integer count);

	/**
	 * 
	 * <p>Title: getAchieveOrder</p>  
	 * <p>Description: 获取已完成并且未入账的订单</p>  
	 * @return  
	 * <p> @date 2018年12月20日  </p>
	 */
	List<Order> getAchieveOrder(@Param("start")Integer start, @Param("end")Integer end);
	
	/**
	 * 
	 * <p>Title: getTotal</p>  
	 * <p>Description: 获取查询出来的总行数</p>  
	 * @param orderId
	 * @param orderStatus
	 * @param topPrice
	 * @param bottomPrice
	 * @param start
	 * @param count
	 * @return  
	 * <p> @date 2018年12月20日  </p>
	 */
	Integer getTotal(@Param("orderId") String orderId, @Param("orderStatus") String orderStatus,
			@Param("topPrice") Double topPrice, @Param("bottomPrice") Double bottomPrice);
	
	/**
	 * 
	 * <p>Title: getAchieveTotal</p>  
	 * <p>Description: 获取已完成并且未入账的订单条数</p>  
	 * @return  
	 * <p> @date 2018年12月20日  </p>
	 */
	Integer getAchieveTotal();
	
	/**
	 * 
	 * <p>Title: deleteOrder</p>  
	 * <p>Description: 删除订单</p>  
	 * @param orderId
	 * @return  
	 * <p> @date 2018年12月6日  </p>
	 */
	Integer deleteOrder(@Param("orderId") String orderId);
	
	/**
	 * 
	 * <p>Title: deleteBookOrder</p>  
	 * <p>Description: 删除订单与书的中间表对应的信息</p>  
	 * @param orderId
	 * @return  
	 * <p> @date 2018年12月17日  </p>
	 */
	Integer deleteBookOrder(@Param("orderId") String orderId);
	
	/**
	 * 
	 * <p>Title: updateOrder</p>  
	 * <p>Description: 修改订单</p>  
	 * @param orderId
	 * @param orderStatus
	 * @param totalPrice
	 * @return  
	 * <p> @date 2018年12月6日  </p>
	 */
	Integer updateOrder(@Param("orderId") String orderId, @Param("orderStatus") String orderStatus,
			@Param("totalPrice") Double totalPrice, @Param("consignee") String consignee,
			@Param("mobile") String mobile, @Param("province") String province, @Param("city") String city,
			@Param("county") String county, @Param("detail") String detail);
	
	/**
	 * 
	 * <p>Title: updateStatus</p>  
	 * <p>Description: 修改退货的订单状态</p>  
	 * @param afterStatus  修改后的状态
	 * @param orderStatus 修改前的状态
	 * @return  
	 * <p> @date 2018年12月18日  </p>
	 */
	Integer updateStatus(@Param("afterStatus") String afterStatus, @Param("orderStatus") String orderStatus);
	
	/**
	 * 
	 * <p>Title: updateAchieve</p>  
	 * <p>Description: 修改已完成订单入账信息</p>  
	 * @param achieve
	 * @param orderId
	 * @return  
	 * <p> @date 2018年12月20日  </p>
	 */
	Integer updateAchieve(@Param("achieve") Boolean achieve, @Param("orderId") String orderId);
	
	/**
	 * 
	 * <p>Title: getOrderStatus</p>  
	 * <p>Description: 获取订单全部状态</p>  
	 * @return  
	 * <p> @date 2018年12月10日  </p>
	 */
	List<Order> getOrderStatus();
	
	/**
	 * 
	 * <p>Title: getOrderWinInfo</p>  
	 * <p>Description: 获取窗口显示的信息</p>  
	 * @param orderId
	 * @return  
	 * <p> @date 2018年12月11日  </p>
	 */
	List<Order> getOrderWinInfo(@Param("orderId") String orderId);
}
