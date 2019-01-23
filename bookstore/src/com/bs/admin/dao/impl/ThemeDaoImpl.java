package com.bs.admin.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bs.admin.dao.ThemeDao;
import com.bs.admin.mapper.ThemeMapper;

@Repository
public class ThemeDaoImpl implements ThemeDao {

	@Autowired
	private ThemeMapper tm;

	@Override
	public Integer addThemeBook(Integer bookId, Integer themeId, String recommendWord) {
		return tm.addThemeBook(bookId, themeId, recommendWord);
	}

	@Override
	public Integer updateTheme(Integer themeId, String themeName, String reason) {
		return tm.updateTheme(themeId, themeName, reason);
	}

	@Override
	public Integer deleteBookTheme(Integer themeId) {
		return tm.deleteBookTheme(themeId);
	}
	

}
