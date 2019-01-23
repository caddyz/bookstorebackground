package com.bs.admin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bs.admin.dao.BookDao;
import com.bs.admin.mapper.BookMapper;
import com.bs.admin.mapper.MessageMapper;
import com.bs.admin.pojo.Book;
import com.bs.admin.pojo.BookModel;
import com.bs.admin.pojo.Message;

@Repository
public class BookDaoImpl implements BookDao {

	@Autowired
	private BookMapper bm;
	@Autowired
	private MessageMapper mm;

	@Override
	public List<Book> retrieveBookByBookInfo(Double topPrice, Double bottomPrice, String bookCategory,
			String publishName, String bookStatus, String value, Integer pageNumber, Integer pageSize,String authorName) {
		List<Book> book = bm.getBookByBookInfo(topPrice, bottomPrice, bookCategory, publishName, bookStatus, value,
				pageNumber, pageSize, authorName);
		return book;
	}

	@Override
	public Integer createBook(Book book) {
		Integer rows = bm.addBook(book);
		return rows;
	}

	@Override
	public Integer updateBook(Book book) {
		Integer rows = bm.dynamicUpdateBook(book);
		return rows;
	}

	@Override
	public List<Message> retrieveMessageByBookId(Integer bookId) {
		List<Message> message = mm.getMessageByBookId(bookId);
		return message;
	}

	@Override
	public List<String> getBookName() {
		List<String> bn = new ArrayList<String>();
		List<Book> book = bm.getBookName();
		for (Book b : book) {
			bn.add(b.getBookName());
		}
		return bn;
	}

	@Override
	public Book getBookByBookId(Integer bookId) {
		return bm.getBookByBookId(bookId);
	}

	@Override
	public List<String> getBookCategory() {
		List<String> category = new ArrayList<String>();
		List<Book> book = bm.getBookCategory();
		for (Book b : book) {
			category.add(b.getBookCategory());
		}
		return category;
	}

	@Override
	public Integer getTotal(Double topPrice, Double bottomPrice, String bookCategory,
			String publishName, String bookStatus, String value, String authorName) {
		return bm.getTotal(topPrice, bottomPrice, bookCategory, publishName, bookStatus, value, authorName);
	}

	@Override
	public Integer deleteBook(Integer bookId) {
		return bm.deleteBook(bookId);
	}

	@Override
	public Integer deleteBookAuthor(Integer bookId) {
		return bm.deleteBookAuthor(bookId);
	}

	@Override
	public List<String> getBookStatus() {
		List<Book> book = bm.getBookStatus();
		List<String> status = new ArrayList<>();
		for (Book b : book) {
			status.add(b.getBookStatus());
		}
		return status;
	}

	@Override
	public List<Book> getPublishName() {
		return bm.getPublishName();
	}

}
