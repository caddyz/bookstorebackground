package com.bs.admin.pojo;


/**  

* <p>Title: ActivityPageData</p>  

* <p>Description: </p>  页面数据

* @author 赵宝林  

* <p> @date 2018年11月28日</p>   

*/ 
public class ActivityPageData {
	
	private Integer BookId;
	private Integer activityId;
	private String bookName;
	private String activityName;
	private Integer activityDiscount;
	private String activityStart;
	private String activityEnd;
	
	public Integer getBookId() {
		return BookId;
	}

	public void setBookId(Integer bookId) {
		BookId = bookId;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Integer getActivityDiscount() {
		return activityDiscount;
	}

	public void setActivityDiscount(Integer activityDiscount) {
		this.activityDiscount = activityDiscount;
	}

	public String getActivityStart() {
		return activityStart;
	}

	public void setActivityStart(String activityStart) {
		this.activityStart = activityStart;
	}

	public String getActivityEnd() {
		return activityEnd;
	}

	public void setActivityEnd(String activityEnd) {
		this.activityEnd = activityEnd;
	}

	@Override
	public String toString() {
		return "ActivityPageData [BookId=" + BookId + ", activityId=" + activityId + ", bookName=" + bookName
				+ ", activityName=" + activityName + ", activityDiscount=" + activityDiscount + ", activityStart="
				+ activityStart + ", activityEnd=" + activityEnd + "]";
	}

	public ActivityPageData(String bookName, String activityName, Integer activityDiscount, String activityStart,
			String activityEnd) {
		this.bookName = bookName;
		this.activityName = activityName;
		this.activityDiscount = activityDiscount;
		this.activityStart = activityStart;
		this.activityEnd = activityEnd;
	}

	public ActivityPageData(Integer activityId, String bookName, String activityName, Integer activityDiscount,
			String activityStart, String activityEnd) {
		this.activityId = activityId;
		this.bookName = bookName;
		this.activityName = activityName;
		this.activityDiscount = activityDiscount;
		this.activityStart = activityStart;
		this.activityEnd = activityEnd;
	}

	public ActivityPageData() {
	}

}
