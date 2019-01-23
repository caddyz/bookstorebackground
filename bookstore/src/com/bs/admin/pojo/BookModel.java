package com.bs.admin.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
* <p>Title: Book</p>  
* <p>Description: “书”的实体类</p>  
* @author 胡杰  
* <p> @date 2018年11月23日</p>
 */
public class BookModel implements Serializable {
	
	/**
	 serialVersionUID
	*/ 
	private static final long serialVersionUID = -4052814292611307607L;
	/**
	 primaryId  自增主键
	*/ 
	private Integer primaryId;
	/**
	 bookId  书号
	*/ 
	private Integer bookId;
	/**
	 bookName  书名
	*/ 
	private String bookName;
	/**
	 author    作者与书，多对多
	*/ 
	private List<Author> authors = new ArrayList<Author>(); 
	/**
	 bookCategory   书的种类
	*/ 
	private String bookCategory;   
	/**
	 bookSalesPrice  售价
	*/ 
	private Double bookSalesPrice; 
	/**
	 bookProfile   简介
	*/ 
	private String bookProfile;   
	/**
	 bookCoverImage   图片路径
	*/ 
	private String bookCoverImage;  
	/**
	 bookStatus  书的状态
	*/ 
	private String bookStatus;
	/**
	 publisherId  出版社名
	*/ 
	private String publishName;  
	/**
	 printId 印刷商id
	*/ 
	private Integer printId;   
	/**
	 * 库存数量
	 */
	private Integer stockNum;
	/**
	 * 月销量
	 */
	private Integer stockSales;
	/**
	 activity   书与活动  多对多
	*/
	public Integer getPrimaryId() {
		return primaryId;
	}
	public void setPrimaryId(Integer primaryId) {
		this.primaryId = primaryId;
	}
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getAuthors() {
		String tmp = "";
		for(int i = 0 ; i < authors.size() -1 ; i++) {
			tmp+=authors.get(i).getAuthorName()+",";
		}
		if(authors.size() >= 1) tmp+=authors.get(authors.size()-1).getAuthorName()  ;
		return tmp;
	}
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
	public String getBookCategory() {
		return bookCategory;
	}
	public void setBookCategory(String bookCategory) {
		this.bookCategory = bookCategory;
	}
	public Double getBookSalesPrice() {
		return bookSalesPrice;
	}
	public void setBookSalesPrice(Double bookSalesPrice) {
		this.bookSalesPrice = bookSalesPrice;
	}
	public String getBookProfile() {
		return bookProfile;
	}
	public void setBookProfile(String bookProfile) {
		this.bookProfile = bookProfile;
	}
	public String getBookCoverImage() {
		return bookCoverImage;
	}
	public void setBookCoverImage(String bookCoverImage) {
		this.bookCoverImage = bookCoverImage;
	}
	public String getBookStatus() {
		return bookStatus;
	}
	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}
	public String getPublishName() {
		return publishName;
	}
	public void setPublishName(String publishName) {
		this.publishName = publishName;
	}
	public Integer getPrintId() {
		return printId;
	}
	public void setPrintId(Integer printId) {
		this.printId = printId;
	}
	
	public Integer getStockSales() {
		return stockSales;
	}
	public void setStockSales(Integer stockSales) {
		this.stockSales = stockSales;
	}
	
	public Integer getStockNum() {
		return stockNum;
	}
	public void setStockNum(Integer stockNum) {
		this.stockNum = stockNum;
	}
	public BookModel(){}
	public BookModel(Integer primaryId, Integer bookId, String bookName, List<Author> authors, String bookCategory,
			Double bookSalesPrice, String bookProfile, String bookCoverImage, String bookStatus, String publishName,
			Integer printId, Integer stockSales, Integer stockNum) {
		this.primaryId = primaryId;
		this.bookId = bookId;
		this.bookName = bookName;
		this.authors = authors;
		this.bookCategory = bookCategory;
		this.bookSalesPrice = bookSalesPrice;
		this.bookProfile = bookProfile;
		this.bookCoverImage = bookCoverImage;
		this.bookStatus = bookStatus;
		this.publishName = publishName;
		this.printId = printId;
		this.stockSales = stockSales;
		this.stockNum = stockNum;
	}
	/*@Override
	public String toString() {
		return "BookModel [primaryId=" + primaryId + ", bookId=" + bookId + ", bookName=" + bookName + ", authors="
				+ authors + ", bookCategory=" + bookCategory + ", bookSalesPrice=" + bookSalesPrice + ", bookProfile="
				+ bookProfile + ", bookCoverImage=" + bookCoverImage + ", bookStatus=" + bookStatus + ", publishName="
				+ publishName + ", printId=" + printId + ", stockSales=" + stockSales + "]";
	}*/
	@Override
	public String toString() {
		return "BookModel [bookId=" + bookId + ", bookName=" + bookName + "]";
	}
	
}
