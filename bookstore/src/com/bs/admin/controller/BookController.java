package com.bs.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bs.admin.annotation.SystemControllerLog;
import com.bs.admin.pojo.Author;
import com.bs.admin.pojo.Book;
import com.bs.admin.pojo.CategoryOrStatus;
import com.bs.admin.pojo.Message;
import com.bs.admin.pojo.BookModel;
import com.bs.admin.pojo.PageData;
import com.bs.admin.service.AuthorService;
import com.bs.admin.service.BookService;
import com.bs.admin.util.JsonData;

@Controller
@RequestMapping("bookController")
public class BookController {

	@Autowired
	private BookService bs;
	
	@Autowired
	private AuthorService as;

	/**
	 * 
	 * <p>Title: sendBookPage</p>  
	 * <p>Description: 跳转页面</p>  
	 * @return  
	 * <p> @date 2018年12月11日  </p>
	 */
	@RequestMapping("sendBookPage")
	public String sendBookPage() {
		return "bookPage";
	}
	
	/**
	 * 获取查询出来的所有书 分页操作
	 */
	@GetMapping(value = "getBook/{pageNumber}/{pageSize}", produces = "application/json;charset=utf-8")
	public @ResponseBody JsonData getBook(@RequestParam("topPrice") Double topPrice,
			@RequestParam("bottomPrice") Double bottomPrice, @PathVariable("pageNumber") Integer pageNumber,
			@PathVariable("pageSize") Integer pageSize, @RequestParam("value") String value,
			@RequestParam("publishName") String publishName, @RequestParam("bookCategory") String bookCategory,
			@RequestParam("bookStatus") String bookStatus, @RequestParam("authorName") String authorName) {
		
		PageData<BookModel> bookData = bs.getBookData(topPrice, bottomPrice, bookCategory, publishName, bookStatus, value, pageNumber, pageSize,authorName);
		Boolean flag = bookData != null ? true : false;
		String msg = flag ? "返回成功！" : "返回失败！";
		JsonData jsonData = new JsonData("bookData", bookData, msg, flag);
		return jsonData;
	}

	/**
	 * 获取所有类型
	 */
	@PostMapping(value="getCategpry",produces="text/html;charset=utf-8")
	public @ResponseBody String getCategpry() {
		List<CategoryOrStatus> bookCategory = bs.getBookCategory();
		String jsonString = JSON.toJSONString(bookCategory);
		return jsonString;
	}

	/**
	 * 
	 * <p>Title: addBook</p>  
	 * <p>Description: 添加书</p>  
	 * @param bookId
	 * @param bookName
	 * @param authorId
	 * @param authorName
	 * @param bookCoverImage
	 * @param bookSalesPrice
	 * @param publishName
	 * @param bookCategory
	 * @param bookStatus
	 * @param bookProfile
	 * @param printId
	 * @return  
	 * <p> @date 2018年12月11日  </p>
	 */
	@SystemControllerLog(type=2, description="添加了一本书")
	@RequestMapping(value="addBook", produces="application/json;charset=utf-8")
	public @ResponseBody JsonData addBook(@RequestParam("bookId") Integer bookId,@RequestParam("bookName") String bookName,
			@RequestParam("authorId") Integer authorId,@RequestParam("authorName") String authorName,@RequestParam("bookCoverImage") String bookCoverImage,
			@RequestParam("bookSalesPrice") Double bookSalesPrice,@RequestParam("publishName") String publishName,
			@RequestParam("bookCategory") String bookCategory,@RequestParam("bookStatus") String bookStatus,
			@RequestParam("bookProfile") String bookProfile,@RequestParam("printId") Integer printId){
		
		Book book = new Book(bookId, bookName, bookCategory, bookSalesPrice, bookProfile, bookCoverImage, bookStatus, publishName, printId);
		Author author = new Author(authorId, authorName, null);
		Integer addBook = bs.addBook(book, author);
		Boolean flag = addBook > 0 ? true : false;
		String str = flag ? "新书添加成功！" : "新书添加失败！请刷新后重试...";
		JsonData jsonData = new JsonData("addBook", addBook, str, flag);
		return jsonData;
	}

	/**
	 * 
	 * <p>Title: updateBook</p>  
	 * <p>Description: 修改书</p>  
	 * @param bookId
	 * @param bookName
	 * @param authorId
	 * @param authorName
	 * @param bookCoverImage
	 * @param bookSalesPrice
	 * @param publishName
	 * @param bookCategory
	 * @param bookStatus
	 * @param bookProfile
	 * @param printId
	 * @return  
	 * <p> @date 2018年12月11日  </p>
	 */
	@SystemControllerLog(type=2, description="修改了一本书")
	@RequestMapping(value="updateBook",produces="application/json;charset=utf-8")
	public @ResponseBody JsonData updateBook(@RequestParam("bookId") Integer bookId,@RequestParam("bookName") String bookName,
			@RequestParam("authorId") Integer authorId,@RequestParam("authorName") String authorName,@RequestParam("bookCoverImage") String bookCoverImage,
			@RequestParam("bookSalesPrice") Double bookSalesPrice,@RequestParam("publishName") String publishName,
			@RequestParam("bookCategory") String bookCategory,@RequestParam("bookStatus") String bookStatus,
			@RequestParam("bookProfile") String bookProfile,@RequestParam("printId") Integer printId){
		
		Book book = new Book(bookId, bookName, bookCategory, bookSalesPrice, bookProfile, bookCoverImage, bookStatus, publishName, printId);
		Author author = new Author(authorId, authorName, null);
		Integer updateBook = bs.updateBook(book, author);
		Boolean flag = updateBook != null ? true : false;
		String str = flag ? "修改成功！" : "修改失败！请重试...";
		JsonData jsonData = new JsonData("updateBook", updateBook, str, flag);
		return jsonData;
	}
	
	/**
	 * 
	 * <p>Title: removeBook</p>  
	 * <p>Description: 删除书</p>  
	 * @param bookId
	 * @return  
	 * <p> @date 2018年12月11日  </p>
	 */
	@SystemControllerLog(type=2, description="删除了一本书")
	@RequestMapping(value="removeBook", produces="application/json;charset=utf-8")
	public @ResponseBody JsonData removeBook(@RequestParam("bookId") Integer bookId){
		
		Integer deleteBook = bs.deleteBook(bookId);
		Boolean flag = deleteBook > 0 ? true : false; 
		String str = flag ? "删除成功！" : "删除失败！请重试...";
		JsonData jsonData = new JsonData("delectBook", deleteBook, str, flag);
		return jsonData;
	}
	
	/**
	 * 
	 * <p>Title: getAuthor</p>  
	 * <p>Description: 获取作者</p>  
	 * @return  
	 * <p> @date 2018年12月11日  </p>
	 */
	@RequestMapping(value="getAuthor", produces="text/html;charset=utf-8")
	public @ResponseBody String getAuthor(){
		List<Author> allAuthor = as.getAllAuthor(null, null);
		String jsonString = JSON.toJSONString(allAuthor);
		return jsonString;
	}
	
	/**
	 *	获取留言/评论
	 */
	@RequestMapping(value="getMessage", produces="text/html;charset=utf-8")
	public @ResponseBody String getMessage(@RequestParam("bookId") Integer bookId){
		List<Message> msg = bs.getMessageByBookId(bookId);
		String jsonString = JSON.toJSONString(msg);
		return jsonString; 
	}
	
	/**
	 * 获取状态
	 */
	@RequestMapping(value="getBookStatus", produces="text/html;charset=utf-8")
	public @ResponseBody String getBookStatus(){
		List<CategoryOrStatus> bookStataus = bs.getBookStataus();
		String jsonString = JSON.toJSONString(bookStataus);
		return jsonString;
	}
	
	/**
	 * 获取全部印刷商
	 */
	@RequestMapping(value="getPublishName", produces="text/html;charset=utf-8")
	public @ResponseBody String getPublishName(){
		List<CategoryOrStatus> publishName = bs.getPublishName();
		String jsonString = JSON.toJSONString(publishName);
		return jsonString;
	}
	
	
}
