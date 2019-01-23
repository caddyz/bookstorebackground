package com.bs.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bs.admin.dao.BookDao;
import com.bs.admin.pojo.Author;
import com.bs.admin.pojo.Book;
import com.bs.admin.pojo.CategoryOrStatus;
import com.bs.admin.pojo.BookModel;
import com.bs.admin.pojo.Message;
import com.bs.admin.pojo.PageData;
import com.bs.admin.service.AuthorService;
import com.bs.admin.service.BookService;

/**
 * 
* <p>Title: BookServiceImpl</p>  
* <p>Description: 书的service层</p>  
* @author 胡杰  
* <p> @date 2018年12月10日</p>
 */
@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookDao bd;
	@Autowired
	private AuthorService as;

	@Override
	public List<BookModel> getBookByBookInfo(Double topPrice, Double bottomPrice, String bookCategory, String publishName,
			String bookStatus, String value, Integer pageNumber, Integer pageSize, String authorName) {
		List<Book> bookList = bd.retrieveBookByBookInfo(topPrice, bottomPrice, bookCategory, publishName, bookStatus,
				value, pageNumber, pageSize,authorName);
		List<BookModel> books = new ArrayList<>();
		for(Book book : bookList){
			books.add(new BookModel(book.getPrimaryId(), book.getBookId(), book.getBookName(), book.getAuthors(), book.getBookCategory(), book.getBookSalesPrice(), book.getBookProfile(), book.getBookCoverImage(), book.getBookStatus(), book.getPublishName(), book.getPrintId(), book.getStockSales(),book.getStockNum()));
		}
		return books;
	}

	// 添加书
	@Override
	public Integer addBook(Book book,Author author) {
		Integer createBook = bd.createBook(book);
		Integer addAuthor = as.addAuthor(author.getAuthorId(),author.getAuthorName());
		Integer createBookAuthor = as.createBookAuthor(book, author);
		Integer temp = createBook + addAuthor + createBookAuthor;
		return temp;
	}

	// 修改书
	@Override
	public Integer updateBook(Book book, Author author) {
		Integer updateBook = bd.updateBook(book);
		Integer updateAuthor = as.updateAuthor(author);
		Integer updateBookAuthor = as.updateBookAuthor(book.getBookId(), author.getAuthorId());
		Integer update = updateBookAuthor + updateAuthor + updateBook;  
		return update;
	}

	// 查询留言
	@Override
	public List<Message> getMessageByBookId(Integer bookId) {
		List<Message> message = bd.retrieveMessageByBookId(bookId);
		return message;
	}

	// 获取书名
	@Override
	public List<String> getBookName() {
		return bd.getBookName();
	}

	@Override
	public Book getBookByBookId(Integer bookId) {
		return bd.getBookByBookId(bookId);
	}

	@Override
	public List<CategoryOrStatus> getBookCategory() {
		List<CategoryOrStatus> bookCategory = new ArrayList<CategoryOrStatus>();
		List<String> categoryList = bd.getBookCategory();
		for(int i = 0;i < categoryList.size();i++){
			bookCategory.add(new CategoryOrStatus(i+2, categoryList.get(i), false));
		}
		return bookCategory;
	}

	/**
	 * 查询书
	 */
	@Override
	public PageData<BookModel> getBookData(Double topPrice, Double bottomPrice, String category, String publish,
			String status, String value, Integer pageNumber, Integer pageSize, String authorName) {
		String value1 = value == "" ? null : value;
		String bookStatus = status == "" ? null : status;
		String bookCategory = category == "" ? null : category;
		String publishName = publish == "" ? null : publish;
		String author = authorName == "" ? null : authorName;
		// 获取总条数
		Integer total = bd.getTotal(topPrice, bottomPrice, bookCategory, publishName, bookStatus, value1, author); 
		
		// 计算开始的行数
		Integer start = (pageNumber - 1) * pageSize;
		
		// 一页显示的数据
		List<BookModel> books = getBookByBookInfo(topPrice, bottomPrice, bookCategory, publishName, bookStatus, value1, start, pageSize, author); 
		
		PageData<BookModel> data = new PageData<BookModel>(books, total);
		return data;
	}


	@Override
	public Integer deleteBook(Integer bookId) {
		Integer deleteBook = bd.deleteBook(bookId);
		Integer deleteBookAuthor = bd.deleteBookAuthor(bookId);
		Integer delete = deleteBookAuthor + deleteBook;
		return delete;
	}

	/**
	 * 获取状态
	 */
	@Override
	public List<CategoryOrStatus> getBookStataus() {
		List<String> bookStatus = bd.getBookStatus();
		List<CategoryOrStatus> status = new ArrayList<>();
		for (int i = 0;i < bookStatus.size(); i++) {
			status.add(new CategoryOrStatus(i+2, bookStatus.get(i), false));
		}
		return status;
	}

	@Override
	public List<CategoryOrStatus> getPublishName() {
		List<CategoryOrStatus> publish = new ArrayList<>();
		List<Book> book = bd.getPublishName();
		for (int i = 0;i < book.size(); i++) {
			publish.add(new CategoryOrStatus(i+2, book.get(i).getPublishName(), false));
		}
		return publish;
	}
	
}
