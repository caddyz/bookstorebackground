package com.bs.mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bs.mall.pojo.Activity;
import com.bs.mall.pojo.AllContent;
import com.bs.mall.pojo.Category;
import com.bs.mall.pojo.Collector;
import com.bs.mall.pojo.Comment;
import com.bs.mall.service.AllContentService;

@RestController
public class AllContentController {
	@Autowired
	private AllContentService acs;
//	根据书的id查询所有书和作者，销量的数据
	@GetMapping("{bookId}/allContext")
	public List<AllContent> findClassifyByBookId(@PathVariable("bookId") Integer bookId){
		return acs.getDetailByBookId(bookId);
	}
//	根据书的id查询评论
	@GetMapping("{bookId}/allContext/kindAll")
	public List<Comment> findAllByBookId(@PathVariable("bookId") Integer bookId){
		return acs.getAllByBookId(bookId);
	}
//	添加收藏
	@GetMapping("{userId}/{bookId}/kindAdd")
	public Integer findAddByBookUserId(@PathVariable("userId") Integer userId,@PathVariable("bookId") Integer bookId){
		return acs.getAddBook(userId, bookId);
	}
	
//	判断收藏是否存在
	@GetMapping("{userId}/{bookId}/isExit")
	public Boolean findIsExitBookId(@PathVariable("userId") Integer userId,@PathVariable("bookId") Integer bookId){
		  Collector coll = acs.getColletcorId(userId, bookId);
		  System.out.println(coll);
		  if(coll!=null){
			  if(coll.getUserId()==userId&&coll.getBookId()==bookId){
				  return false;
			  }else{
			
				  return true;
			  }
		  }
		  return false;
	}
	
//	分类左侧只显示的分类
	@GetMapping("selectBook")
	public List<Category> findSelectBookCategory(){
		return acs.selectClassifyById();
	}
	
//	点击分类显示书名，金额
	@GetMapping("{bookCategory}/allBook")
	public List<AllContent> findAllBookId(@PathVariable("bookCategory") String bookCategory){
		return acs.getAllBookId(bookCategory);
	}
	
//	显示所有书名和图片
	@GetMapping("selectAllBook")
	public List<AllContent> findSelectBook(){
		return acs.getAllBook();
	}
	
//	查询书id对应的折扣
	@GetMapping("selectActivity/{bookId}")
	public List<Activity> findSelectActivity(@PathVariable("bookId") Integer bookId){
		return acs.getActivity(bookId);
	}
	
//	查询书的所有作者
	@GetMapping("selectAllAuthor/{bookId}")
	public List<AllContent> findSelectAllAuthor(@PathVariable("bookId") Integer bookId){
		return acs.getAllAuthor(bookId);
	}
	
//	添加评论
	@GetMapping("{userId}/{bookId}/{commentContent}/commentAdd")
	public Integer findInsertAllComment(@PathVariable("userId") Integer userId,@PathVariable("bookId") Integer bookId,@PathVariable("commentContent") String commentContent){
		return acs.getAllComment(userId, bookId, commentContent);
	}
	
//	追加评论
	@GetMapping("{userId}/{bookId}/{commentReply}/commentAll")
	public Integer findUpdateAllComment(@PathVariable("userId") Integer userId,@PathVariable("bookId") Integer bookId,@PathVariable("commentReply") String commentReply){
		return acs.getAllCommentReply(userId, bookId, commentReply);
	}
	
//	查看库存
	@GetMapping("{bookId}/selectNum")
	public List<AllContent> findStockNum(@PathVariable("bookId") Integer bookId){
		return acs.getStockNum(bookId);
	}
	
}
