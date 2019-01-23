package com.bs.mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bs.mall.dao.AllContentDao;
import com.bs.mall.pojo.Activity;
import com.bs.mall.pojo.AllContent;
import com.bs.mall.pojo.Category;
import com.bs.mall.pojo.Collector;
import com.bs.mall.pojo.Comment;
import com.bs.mall.service.AllContentService;
@Service
public class AllContentServiceImpl implements AllContentService {
	@Autowired
	private AllContentDao acd;
//	根据书的id查询所有书和作者，销量的数据
	@Override
	public List<AllContent> getDetailByBookId(Integer bookId) {
		return acd.referDetailByBookId(bookId);
	}
//	根据书的id查询评论
	@Override
	public List<Comment> getAllByBookId(Integer bookId) {
		return acd.referAllfyByBookId(bookId);
	}
//	添加收藏
	@Override
	public Integer getAddBook(Integer userId,Integer bookId) {
		return acd.referAddBook(userId,bookId);
	}
//	判断是否存在
	@Override
	public Collector getColletcorId(Integer userId, Integer bookId) {
		 return acd.referCollectorById(userId, bookId);
	}
//	分类左侧只显示的分类
	@Override
	public List<Category> selectClassifyById() {
		return acd.referClassifyByBookId();
	}
//	点击分类显示书名，金额
	@Override
	public List<AllContent> getAllBookId(String bookCategory) {
		return acd.referAllBookId(bookCategory);
	}
//	显示所有书名和图片
	@Override
	public List<AllContent> getAllBook() {
		return acd.referAllBook();
	}
//	查询书id对应的折扣
	@Override
	public List<Activity> getActivity(Integer bookId) {
		return acd.referActivity(bookId);
	}
//	查询书的所有作者
	@Override
	public List<AllContent> getAllAuthor(Integer bookId) {
		return acd.referAllAuthor(bookId);
	}
//	添加评论
	@Override
	public Integer getAllComment(Integer userId,Integer bookId,String commentContent) {
		return acd.referAllCommentAdd(userId, bookId, commentContent);
	}
//	追加评论
	@Override
	public Integer getAllCommentReply(Integer userId, Integer bookId, String commentReply) {
		return acd.referAllCommentReply(userId, bookId, commentReply);
	}
//	查看库存
	@Override
	public List<AllContent> getStockNum(Integer bookId) {
		return acd.referStockNum(bookId);
	}

}
