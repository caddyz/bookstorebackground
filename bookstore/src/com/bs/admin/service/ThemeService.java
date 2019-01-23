package com.bs.admin.service;

import org.springframework.stereotype.Service;

@Service
public interface ThemeService {

	/**
	 * 
	 * <p>Title: updateTheme</p>  
	 * <p>Description: 更新主题</p>  
	 * @param themeId
	 * @param themeName
	 * @param reason
	 * @return  
	 * <p> @date 2018年12月14日  </p>
	 */
	Integer updateTheme(Integer themeId, String themeName, String reason);
	
	/**
	 * 
	 * <p>Title: addTheme</p>  
	 * <p>Description: 添加主题书的信息</p>  
	 * @param bookId
	 * @param themeId
	 * @param recommendWord
	 * @return  
	 * <p> @date 2018年12月13日  </p>
	 */
	Integer addThemeBook(Integer bookId, Integer themeId, String recommendWord);
	
}
