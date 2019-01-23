package com.bs.admin.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bs.admin.dao.AuthorDao;
import com.bs.admin.mapper.AuthorMapper;
import com.bs.admin.pojo.Author;
import com.bs.admin.pojo.Book;

@Repository
public class AuthorDaoImpl implements AuthorDao {

	@Autowired
	private AuthorMapper am;
	
	@Override
	public List<Author> retrieveAuthorByAuthorInfo(Author author) {
		return am.getAuthorByAuthorInfo(author);
	}

	@Override
	public Integer createAuthor(Integer authorId,String authorName) {
		return am.createAuthor(authorId,authorName);
	}

	@Override
	public Integer updateAuthor(Author author) {
		return am.updateAuthor(author);
	}

	@Override
	public Integer createBookAuthor(Book book, Author author) {
		if (null != book.getBookId() && null != author.getAuthorId())
			return am.createBookAuthor(book.getBookId(), author.getAuthorId());
		return null;
	}

	@Override
	public Integer updateBookAuthor(Integer bookId, Integer authorId) {
		return am.updateBookAuthor(bookId,authorId);
	}

	@Override
	public List<Author> getAllAuthor(Integer authorId, String authorName) {
		return am.getAllAuthor(authorId, authorName);
	}

}
