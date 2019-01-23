package com.bs.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bs.admin.pojo.Book;
import com.bs.admin.pojo.BookModel;
import com.bs.admin.pojo.Message;

@Repository
public interface BookDao {

	/**
	 * 
	 * <p>Title: retrieveBookByBookId</p>  
	 * <p>Description: 通过id查询书的信息</p>  
	 * @param bookId
	 * @return  
	 * <p> @date 2018年11月24日  </p>
	 */
//	List<BookModel> retrieveBookByBookInfo(Double topPrice,Double bottomPrice,String bookCategory,String publishName,String bookStatus,String value,Integer pageNumber,Integer pageSize,String authorName); 
	List<Book> retrieveBookByBookInfo(Double topPrice,Double bottomPrice,String bookCategory,String publishName,String bookStatus,String value,Integer pageNumber,Integer pageSize,String authorName); 
	
	/**
	 * 
	 * <p>Title: getBookByBookId</p>  
	 * <p>Description: 通过书id获取书</p>  
	 * @param bookId
	 * @return  
	 * <p> @date 2018年12月10日  </p>
	 */
	Book getBookByBookId(Integer bookId);
	
	/**
	 * 
	 * <p>Title: getBookCategory</p>  
	 * <p>Description: 获取书的全部类型</p>  
	 * @return  
	 * <p> @date 2018年12月10日  </p>
	 */
	List<String> getBookCategory();
	
	/**
	 * 
	 * <p>Title: getBookCategory</p>  
	 * <p>Description: 获取书的全部状态</p>  
	 * @return  
	 * <p> @date 2018年12月10日  </p>
	 */
	List<String> getBookStatus();
	
	/**
	 * 
	 * <p>Title: getPublishName</p>  
	 * <p>Description: 获取印刷商</p>  
	 * @return  
	 * <p> @date 2018年12月14日  </p>
	 */
	List<Book> getPublishName();
	
	/**
	 * 
	 * <p>Title: getBookName</p>  
	 * <p>Description: 获取所有书名</p>  
	 * @return  
	 * <p> @date 2018年11月28日  </p>
	 */
	List<String> getBookName();
	
	/**
	 * 
	 * <p>Title: createBook</p>  
	 * <p>Description: 添加书</p>  
	 * @param book
	 * @return  
	 * <p> @date 2018年11月24日  </p>
	 */
	Integer createBook(Book book);

	/**
	 * 
	 * <p>Title: updateBook</p>  
	 * <p>Description: 修改书的信息</p>  
	 * @param book
	 * @return  
	 * <p> @date 2018年11月26日  </p>
	 */
	Integer updateBook(Book book);
	
	/**
	 * 
	 * <p>Title: retrieveMessageByBookId</p>  
	 * <p>Description: 通过书id查询该书的所有留言</p>  
	 * @param bookId
	 * @return  
	 * <p> @date 2018年11月26日  </p>
	 */
	List<Message> retrieveMessageByBookId(Integer bookId);
	
	/**
	 * 
	 * <p>Title: getTotal</p>  
	 * <p>Description: 获取总行数</p>  
	 * @param topPrice
	 * @param bottomPrice
	 * @param bookCategory
	 * @param publishName
	 * @param bookStatus
	 * @param value
	 * @return  
	 * <p> @date 2018年12月10日  </p>
	 */
	Integer getTotal(Double topPrice,Double bottomPrice,String bookCategory,String publishName, String bookStatus, String value, String authorName);
	
	/**
	 * 
	 * <p>Title: deleteBook</p>  
	 * <p>Description: 删除书</p>  
	 * @param bookId
	 * @return  
	 * <p> @date 2018年12月10日  </p>
	 */
	Integer deleteBook(Integer bookId);
	
	/**
	 * 
	 * <p>Title: deleteBookAuthor</p>  
	 * <p>Description: 删除书和作者在中间表关联的信息</p>  
	 * @param bookId
	 * @return  
	 * <p> @date 2018年12月10日  </p>
	 */
	Integer deleteBookAuthor(Integer bookId);
	
}
