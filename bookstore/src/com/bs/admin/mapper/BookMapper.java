package com.bs.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;

import com.bs.admin.pojo.Book;
import com.bs.admin.provider.BookProvider;

/**
 * 
* <p>Title: BookMapper</p>  
* <p>Description: 连接BookMapper.xml的接口</p>  
* @author 胡杰  
* <p> @date 2018年12月14日</p>
 */
public interface BookMapper {
	
	/**
	 * 
	 * <p>Title: addBook</p>  
	 * <p>Description: 添加book</p>  
	 * @param book
	 * @return  
	 * <p> @date 2018年12月13日  </p>
	 */
	// method：指明工厂类对应的方法 type：指明SQL工厂类
	@InsertProvider(method = "addBook", type = BookProvider.class)
	Integer addBook(@Param("book") Book book);

	
	/**
	 * 
	 * <p>Title: dynamicUpdateBook</p>  
	 * <p>Description: 动态修改book</p>  
	 * @param book
	 * @return  
	 * <p> @date 2018年12月13日  </p>
	 */
	@UpdateProvider(method = "dynamicUpdateBook", type = BookProvider.class)
	Integer dynamicUpdateBook(@Param("book") Book book);

	/**
	 * 
	 * <p>Title: getBookByBookInfo</p>  
	 * <p>Description: 通过书id查找书的信息</p>  
	 * @param topPrice
	 * @param bottomPrice
	 * @param bookCategory
	 * @param publishName
	 * @param bookStatus
	 * @param value
	 * @param start
	 * @param count
	 * @param authorName
	 * @return  
	 * <p> @date 2018年12月10日  </p>
	 */
	List<Book> getBookByBookInfo(@Param("topPrice") Double topPrice, @Param("bottomPrice") Double bottomPrice,
			@Param("bookCategory") String bookCategory, @Param("publishName") String publishName,
			@Param("bookStatus") String bookStatus, @Param("value") String value, @Param("start") Integer start,
			@Param("count") Integer count, @Param("authorName") String authorName);

	/**
	 * 
	 * <p>Title: getBookByBookId</p>  
	 * <p>Description: 通过id查询书的信息</p>  
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
	List<Book> getBookCategory();

	/**
	 * 
	 * <p>Title: getBookStatus</p>  
	 * <p>Description: 获取书的全部状态</p>  
	 * @return  
	 * <p> @date 2018年12月12日  </p>
	 */
	List<Book> getBookStatus();
	
	/**
	 * 
	 * <p>Title: getPublishName</p>  
	 * <p>Description: 获取全部印刷商</p>  
	 * @return  
	 * <p> @date 2018年12月14日  </p>
	 */
	List<Book> getPublishName();
	
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
	Integer getTotal(@Param("topPrice") Double topPrice, @Param("bottomPrice") Double bottomPrice,
			@Param("bookCategory") String bookCategory, @Param("publishName") String publishName,
			@Param("bookStatus") String bookStatus, @Param("value") String value, @Param("authorName") String authorName);

	/**
	 * 
	 * <p>Title: getBookName</p>  
	 * <p>Description: 获取书名</p>  
	 * @return  
	 * <p> @date 2018年12月10日  </p>
	 */
	List<Book> getBookName();

	/**
	 * 
	 * <p>Title: getBookIdByBooKName</p>  
	 * <p>Description: 通过书名获取Id</p>  
	 * @param bookName
	 * @return  
	 * <p> @date 2018年12月10日  </p>
	 */
	Integer getBookIdByBooKName(@Param("bookName") String bookName);

	/**
	 * 
	 * <p>Title: deleteBook</p>  
	 * <p>Description: 通过书号删除书</p>  
	 * @param bookId
	 * @return  
	 * <p> @date 2018年12月3日  </p>
	 */
	Integer deleteBook(Integer bookId);
	
	/**
	 * 
	 * <p>Title: deleteBookAuthor</p>  
	 * <p>Description: 删除中间表对应的id</p>  
	 * @param bookId
	 * @return  
	 * <p> @date 2018年12月3日  </p>
	 */
	Integer deleteBookAuthor(Integer bookId);
	
	/**  
	
	 * <p>Title: getAllBook</p>  
	
	 * <p>Description: 分页返回</p>  
	
	 * @param bookName
	 * @param bookCategory
	 * @param bookStatus
	 * @param bottomPrice
	 * @param topPrice
	 * @param start
	 * @param end
	 * @return  
	 * <p> @date 2018年12月13日  </p> 
	 */
	List<Book> getAllBook(
			@Param("bookName") String bookName, 
			@Param("bookCategory") String bookCategory, 
			@Param("bookStatus") String bookStatus, 
			@Param("bottomPrice") Double bottomPrice, 
			@Param("topPrice") Double topPrice, 
			@Param("start") Integer start, 
			@Param("end") Integer end);
	
	/**  
	
	 * <p>Title: getAllTotal</p>  
	
	 * <p>Description: 查询全部的条数</p>  
	
	 * @param bookName
	 * @param bookCategory
	 * @param bookStatus
	 * @param bottomPrice
	 * @param topPrice
	 * @return  
	 * <p> @date 2018年12月13日  </p> 
	 */
	Integer getAllTotal(
			@Param("bookName") String bookName, 
			@Param("bookCategory") String bookCategory, 
			@Param("bookStatus") String bookStatus, 
			@Param("bottomPrice") Double bottomPrice, 
			@Param("topPrice") Double topPrice);
	
	
}
