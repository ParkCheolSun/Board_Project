package com.myspring.pro30.board.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface BoardDAO {
	public List selectAllArtclesList() throws Exception;
	public int insertNewArticle(Map articleMap) throws DataAccessException;
}
