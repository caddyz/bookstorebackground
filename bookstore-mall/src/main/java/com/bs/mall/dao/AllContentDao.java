package com.bs.mall.dao;

import java.util.List;

import com.bs.mall.pojo.Activity;
import com.bs.mall.pojo.AllContent;
import com.bs.mall.pojo.Category;
import com.bs.mall.pojo.Collector;
import com.bs.mall.pojo.Comment;

public interface AllContentDao {
//	根据书的id查询所有书和作者，销量的数据
	List<AllContent> referDetailByBookId(Integer bookId);
//	根据书的id查询评论
	List<Comment> referAllfyByBookId(Integer bookId);
//	添加收藏
	Integer referAddBook(Integer userId,Integer bookId);
//	判断是否存在
	Collector referCollectorById(Integer userId,Integer bookId);
//	分类左侧只显示的分类
	List<Category> referClassifyByBookId();
//	点击分类显示书名，金额
	List<AllContent> referAllBookId(String bookCategory);
//	显示所有书名和图片
	List<AllContent> referAllBook();
//	查询书id对应的折扣
	List<Activity> referActivity(Integer bookId);
//	查询书的所有作者
	List<AllContent> referAllAuthor(Integer bookId);
//	添加评论
	Integer referAllCommentAdd(Integer userId,Integer bookId,String commentContent);
//	添加评论
	Integer referAllCommentReply(Integer userId,Integer bookId,String commentReply);
//	查看库存
	List<AllContent> referStockNum(Integer bookId);
}
