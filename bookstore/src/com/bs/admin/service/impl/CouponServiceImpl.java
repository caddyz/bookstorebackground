package com.bs.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.bs.admin.dao.CouponDao;
import com.bs.admin.pojo.Coupon;
import com.bs.admin.pojo.PageData;
import com.bs.admin.pojo.QueryBoxData;
import com.bs.admin.service.CouponService;
import com.bs.admin.util.DateUtil;
import com.bs.admin.util.JsonData;

@Repository
public class CouponServiceImpl implements CouponService {

	@Autowired
	private CouponDao cd;
	
	@Override
	public List<QueryBoxData> getAllCouponName() {
		// 获取对象
		List<String> names = cd.getAllCouponName();
		// 创建页面数据的集合
		List<QueryBoxData> nameData = new ArrayList<QueryBoxData>();
		for (int i = 0; i < names.size(); i++) {
			nameData.add(new QueryBoxData(i, names.get(i), false));
		}
		return nameData;
	}

	@Override
	public List<QueryBoxData> getAllCouponStatus() {	
		List<String> status = cd.getAllCouponStatus();	
		List<QueryBoxData> statusData = new ArrayList<QueryBoxData>();
		for (int i = 0; i < status.size(); i++) {
			statusData.add(new QueryBoxData(i, status.get(i), false));
		}
		return statusData;	
	}

	@Override
	public JsonData addCoupon(Coupon coupon) {
		// 查询是否存在对象
		Coupon couponId = cd.getCouponIdByCoupon(coupon);
		if(couponId==null) {
			Integer addCoupon = cd.addCoupon(coupon);
			if(addCoupon>0) {
				return new JsonData("addCoupon", coupon, "添加成功", true);
			}else {
				return new JsonData("addCoupon", addCoupon, "添加失败", false);
			}
		}else {
			return new JsonData("addCoupon", couponId, "添加失败,已存在此优惠券信息", false);
		}
		
	}

	@Override
	public PageData<Coupon> getAllCoupon(Integer pageNum, Integer pageSize,String couponName,String couponStart ,String couponEnd, String couponStatus) {
		// 获取总条数
		Integer total = cd.getCouponTotal(couponName, couponStart, couponEnd, couponStatus);
		// 获取开始位置
		Integer start = (pageNum - 1) * pageSize;
		// 获取结束位置
		Integer end = (pageNum <= (total / pageSize)) ? (start + pageSize) : total;
		return new PageData<Coupon>(cd.getAllCoupon(start, end,couponName, couponStart, couponEnd, couponStatus), total);
	}

	@Override
	public JsonData updataCoupon(List<Coupon> coupons) {
		Integer index = 0;
		for (Coupon coupon : coupons) {
			coupon.setCouponStart(DateUtil.changeDateString(coupon.getCouponStart()));
			coupon.setCouponEnd(DateUtil.changeDateString(coupon.getCouponEnd()));
			if(cd.updateCouponByCoupon(coupon)>0) {
				index++;
			}
		}
		if(index==coupons.size()) {
			return new JsonData("updata", null, "修改成功", true);
		}else {
			return new JsonData("updata", null, "修改失败", false);
		}
	}

	@Override
	public JsonData delCoupon(List<Coupon> coupons) {
		Integer index = 0;
		for (Coupon coupon : coupons) {
			if(cd.delCoupon(coupon.getCouponId())>0) {
				index++;
			}
		}
		if(index==coupons.size()) {
			return new JsonData("delete", null, "删除成功", true);
		}else {
			return new JsonData("delete", null, "删除失败", false);
		}
	}

}
