package com.bs.mall.service;

import java.util.List;

import com.bs.mall.pojo.Book;
import com.bs.mall.pojo.BookList;
import com.bs.mall.pojo.BookTheme;
import com.bs.mall.pojo.HotBook;
import com.bs.mall.pojo.OrderInculdeBooks;

public interface BookService {
	List<Book> getBookByPage(Integer pageNum);
	List<Book> getBookByKeyword(Integer pageNum,String keyword);
	List<Book> getFavoriteBook(Integer userId);
	List<HotBook> getHotBookByPage(Integer pageNum);
	List<Book> getBookByKind(String bookKind);
	List<Book> getBookByAuthor(String author);
	List<BookList> getBookList(Integer themeId);
	BookTheme getBookTheme(Integer themeId);
	List<OrderInculdeBooks> getOrderInculdeBooks(String orderId);
}
