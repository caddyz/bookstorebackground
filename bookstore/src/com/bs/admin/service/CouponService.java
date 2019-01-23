package com.bs.admin.service;

import java.util.List;

import com.bs.admin.pojo.Coupon;
import com.bs.admin.pojo.PageData;
import com.bs.admin.pojo.QueryBoxData;
import com.bs.admin.util.JsonData;

public interface CouponService {

	/**
	 * 优惠券发放(添加优惠券)
	 * 
	 * @return Integer
	 */
	JsonData addCoupon(Coupon coupon);

	/**
	 * 优惠券名字页面数据的集合
	 * 
	 * @return 页面数据集合
	 */
	List<QueryBoxData> getAllCouponName();

	/**
	 * 优惠券类型页面数据的集合
	 * 
	 * @return 页面数据集合
	 */
	List<QueryBoxData> getAllCouponStatus();

	/**
	 * 优惠券查询
	 * 
	 * @return 优惠券的集合
	 */
	PageData<Coupon> getAllCoupon(Integer pageNum, Integer pageSize, String couponName, String couponStart, String couponEnd, String couponStatus);
	
	/**
	 * 优惠券修改
	 * 
	 * @return JsonData
	 */
	JsonData updataCoupon(List<Coupon> coupons);
	
	/**
	 * 优惠券删除
	 * 
	 * @return JsonData
	 */
	JsonData delCoupon(List<Coupon> coupons);
}
